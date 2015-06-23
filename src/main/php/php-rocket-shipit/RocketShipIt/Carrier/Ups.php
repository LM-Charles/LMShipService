<?php

namespace RocketShipIt\Carrier;

use \RocketShipIt\Request;

/**
* Core UPS Class
*
* Used internally to send data, set debug information, change
* urls, and build xml
*/
class Ups extends \RocketShipIt\Carrier\Base
{

    public $paramSynonyms = array(
        'customsDescription' => 'invoiceLineDescription',
        'customsQuantity' => 'invoiceLineNumber',
        'customsValue' => 'invoiceLineValue',
        'customsPartNumber' => 'invoiceLinePartNumber',
        'customsOriginCountry' => 'invoiceLineOriginCountryCode',
    );

    function __construct()
    {
        parent::__construct();

        $this->debugMode = $this->config->getDefault('generic', 'debugMode');
        $this->testingUrl = 'https://wwwcie.ups.com';
        $this->productionUrl = 'https://onlinetools.ups.com';
        $this->setTestingMode($this->debugMode);
        
        // Create a new xmlObject to be used by access and other classes
        // This object will be used all the way through, until the final xmlObject
        // is converted to a string just before sending to UPS
        $this->xmlObject = new \RocketShipIt\Helper\XmlBuilder(false);
        //$this->access();

        $this->request = new Request;
    }

    // Build the access XML to be used in EVERY request to UPS
    public function access()
    {
        $xml = $this->xmlObject;
        $xml->push('AccessRequest', array('xml:lang' => 'en-US'));
            $xml->element('AccessLicenseNumber', $this->license);
            $xml->element('UserId', $this->username);
            $xml->element('Password', $this->password);
        $xml->pop();

        $this->xmlObject = $xml;

        return $this->xmlObject->getXml();
    }

    // This function is the only function that actually transmits and receives data
    // from UPS. All classes use this to send XML to UPS servers.
    function request($type, $xml)
    {
        if ($this->mockXmlResponse != '') {
            if (is_array($this->mockXmlResponse)) {
                $mockXml = array_shift($this->mockXmlResponse);
            } else {
                $mockXml = $this->mockXmlResponse;
            }
            $this->xmlResponse = $mockXml;

            return $mockXml;
        }

        $this->xmlSent = $xml;
        $request = $this->request;
        $request->url = $this->url. '/ups.app/xml/'. $type;
        $request->requestTimeout = $this->requestTimeout;
        $request->payload = $xml;
        $request->post();

        $curlReturned = $request->getResponse();
        if ($request->getError()) {
            $error = $request->getError();
            $xml = "<error>$error</error>";
            $this->xmlResponse = $xml;
            return array($xml);
        }
        $this->curlReturned = $curlReturned;
        // exit ($curlReturned);

        // Find out if the UPS service is down
        if ($request->getStatusCode() != 100 && $request->getStatusCode() != 200) {
            return array('error' => 'The UPS service seems to be down with HTTP/1.1 '. $request->getStatusCode());
        } else {
            $response = strstr($curlReturned, '<?'); // Separate the html header and the actual XML because we turned CURLOPT_HEADER to 1
            $this->xmlResponse = $response;
            return $response;
        }
    }

    function getServiceDescriptionFromCode($code, $country='US')
    {
        if (strtoupper($country) == 'CA') {
            switch($code) {
                case '01':
                    return 'UPS Express';
                case '02':
                    return 'UPS Worldwide Expedited';
                case '07':
                    return 'UPS Express';
                case '11':
                    return 'UPS Standard';
                case '12':
                    return 'UPS 3 Day Select';
                case '13':
                    return 'UPS Saver';
                case '14':
                    return 'UPS Express Early AM';
                default:
                    return 'Unknown service code';
            }
        } else {
            switch($code) {
                case '01':
                    return 'UPS Next Day Air';
                case '02':
                    return 'UPS 2nd Day Air';
                case '03':
                    return 'UPS Ground';
                case '07':
                    return 'UPS Worldwide Express';
                case '08':
                    return 'UPS Worldwide Expedited';
                case '11':
                    return 'UPS Standard';
                case '12':
                    return 'UPS 3 Day Select';
                case '13':
                    return 'UPS Next Day Air Saver';
                case '14':
                    return 'UPS Next Day Air Early AM';
                case '54':
                    return 'UPS Worldwide Express Plus';
                case '59':
                    return 'UPS Second Day Air AM';
                case '65':
                    return 'UPS Worldwide Saver';
                case '82':
                    return 'UPS Today Standard';
                case '83':
                    return 'UPS Today Dedicated';
                case '84':
                    return 'UPS Today Intercity';
                case '85':
                    return 'UPS Today Express';
                case '86':
                    return 'UPS Today Express Saver';
                default:
                    return 'Unknown service code';
            }
        }
    }

    public function mapMailInnovationServiceCodes($oldCode)
    {
        $codeMap = array();
        $codeMap['70'] = 'M2';
        $codeMap['71'] = 'M3';
        $codeMap['72'] = 'M4';
        $codeMap['73'] = 'M5';
        $codeMap['74'] = 'M6';
        if (!isset($codeMap[$oldCode])) {
            return $oldCode;
        }

        return $codeMap[$oldCode];
    }

    public $paramValueSynonyms = array(
        'LB' => 'LBS',
        'KG' => 'KGS',
    );

    function getShipmentDCISType($signatureCode)
    {
        $signatureTypes = array(
            'INDIRECT' => '',
            'DIRECT' => '1',
            'ADULT' => '2',
            'NO_SIGNATURE_REQUIRED' => '',
        );

        if (array_key_exists($signatureCode, $signatureTypes)) {
            return $signatureTypes[$signatureCode];
        }

        return $signatureCode;
    }

    function getPackageDCISType($type)
    {
        $signatureTypes = array(
            'INDIRECT' => '1',
            'DIRECT' => '2',
            'ADULT' => '3',
            'USPS' => '4',
            'NO_SIGNATURE_REQUIRED' => '',
        );

        if (array_key_exists($type, $signatureTypes)) {
            return $signatureTypes[$type];
        }

        return $type;
    }

}
