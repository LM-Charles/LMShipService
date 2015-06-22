<?php

namespace RocketShipIt\Service\AddressValidate;

use \RocketShipIt\Helper\XmlParser;
use \RocketShipIt\Helper\XmlBuilder;

/**
* Main Address Validation class for carrier.
*
* Valid carriers are: UPS, USPS, STAMPS, and FedEx.
*/
class Ups extends \RocketShipIt\Service\Common
{
    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    public function processResponse()
    {
        $xmlArray = $this->arrayFromXml($this->core->xmlResponse);
        $this->responseArray = $xmlArray;

        if (isset($xmlArray['AddressValidationResponse']['Response']['ResponseStatusCode'])) {
            if ($xmlArray['AddressValidationResponse']['Response']['ResponseStatusCode'] == 1) {
                $this->status = 'success';
            }
            if ($xmlArray['AddressValidationResponse']['Response']['ResponseStatusCode'] == 0) {
                $this->status = 'failure';
            }
        }
    }

    // Builds xml for a rate request converts xml to a string, sends the xml to ups,
    // stores the xmlSent and xmlResponse in the ups class incase you want to see it.
    // Finally, this class returns the xml response from UPS as an array.
    function getUPSValidate()
    {
        // Grab the auth portion of the xml from the ups class
        $this->core->access();
        $accessXml = $this->core->xmlObject;

        // Start a new xml object
        $xml = new xmlBuilder();

        $xml->push('AddressValidationRequest',array('xml:lang' => 'en-US'));
            $xml->push('Request');
                $xml->push('TransactionReference'); // Not required
                    $xml->element('CustomerContext', 'RocketShipIt'); // Not required
                    //$xml->element('XpciVersion', '1.0'); // Not required
                $xml->pop(); // close TransactionReference, not required
                $xml->element('RequestAction', 'AV');
            $xml->pop(); // Close Request
            $xml->push('Address');
                if ($this->toCity != '') {
                    $xml->element('City', $this->toCity);
                }
                if ($this->toState != '') {
                    $xml->element('StateProvinceCode', $this->toState);
                }
                if ($this->toCode != '') {
                    $xml->element('PostalCode', $this->toCode);
                }
            $xml->pop(); // Close Address
        $xml->pop(); // Close AddressValidationRequest

        // Convert xml object to a string appending the auth xml
        $xmlString = $accessXml->getXml(). $xml->getXml();

        // Submit the cURL call
        $this->core->request('AV', $xmlString);

        $this->processResponse(); 
        return $this->responseArray;
    }

    public function getUSPSValidate()
    {
        return array();
    }

    public function getUPSValidateStreetLevel()
    {
        $this->core->request('XAV', $this->buildUPSValidateStreetLevelXml()); 
        $this->processResponse(); 
        return $this->responseArray;
    }

    function buildUPSValidateStreetLevelXml()
    {
        $this->core->access();
        $accessXml = $this->core->xmlObject;

        $xml = new xmlBuilder();

        $xml->push('AddressValidationRequest',array('xml:lang' => 'en-US'));
            $xml->push('Request');
                $xml->push('TransactionReference'); // Not required
                    $xml->element('CustomerContext', 'RocketShipIt'); // Not required
                    //$xml->emptyelement('ToolVersion');
                $xml->pop(); // close TransactionReference, not required
                $xml->element('RequestAction', 'XAV');
                $xml->element('RequestOption', '3');
            $xml->pop(); // close Request
            $xml->push('AddressKeyFormat');
                $xml->element('ConsigneeName', $this->toName);
                $xml->element('AttentionName', $this->toName);
                $xml->element('PoliticalDivision1', $this->toState);
                $xml->element('PoliticalDivision2', $this->toCity);
                $xml->element('AddressLine', $this->toAddr1);
                $xml->element('BuildingName', $this->toAddr2);
                $xml->element('PostcodePrimaryLow', $this->toCode);
                $xml->element('PostcodeExtendedLow', $this->toExtendedCode);
                $xml->element('CountryCode', $this->toCountry);
            $xml->pop(); // close AddressKeyFormat
        $xml->pop(); // close AddressValidationRequest

        $xmlString = $accessXml->getXml(). $xml->getXml();
        return $xmlString;
    }
}
