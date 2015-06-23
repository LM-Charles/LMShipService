<?php

namespace RocketShipIt\Service\Track;

use \RocketShipIt\Helper\XmlParser;
use \RocketShipIt\Helper\XmlBuilder;

/**
* Main class for tracking shipments and packages
*
* This class is a wrapper for use with all carriers to track packages
* Valid carriers are: UPS, USPS, STAMPS and FedEx.
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

        if (isset($xmlArray['TrackResponse']['Response']['ResponseStatusCode'])) {
            if ($xmlArray['TrackResponse']['Response']['ResponseStatusCode'] == 1) {
                $this->status = 'success';
            }

            if ($xmlArray['TrackResponse']['Response']['ResponseStatusCode'] == 0) {
                $this->status = 'failure';
            }
        }
    }

    public function buildUPSTrackingXml($trackingNumber)
    {
        $this->core->access();
        $xml = $this->core->xmlObject;
        
        $xml->push('TrackRequest',array('xml:lang' => 'en-US'));
            $xml->element('TrackingOption', $this->trackingOption);
            $xml->push('Request');
                $xml->push('TransactionReference');
                    $xml->element('CustomerContext', 'RocketShipIt');
                $xml->pop(); // close TransactionReference
                $xml->element('RequestAction', 'Track');
                /*
                'none' – " " or '0'= Last Activity
                'activity' or '1' - all activity
                2 = POD, Receiver Address and Last Activity
                3 = POD, Receiver Address, All Activity
                4 = POD, COD, Last Activity
                5 = POD, COD, All Activity
                6 = POD, COD, Receiver Address, Last Activity
                7 = POD, COD, Receiver Address, All Activity
                */
                // activity doesn't work for Mail Innovations use '1'
                $xml->element('RequestOption', '1');
            $xml->pop(); // close Request
            if (!isset($this->referenceNumber)) {
                $helper = new \RocketShipIt\Helper\General();
                if ($helper->startsWith($trackingNumber, '1Z')) {
                    $xml->element('ShipmentIdentificationNumber', $trackingNumber);
                } else {
                    $xml->element('TrackingNumber', $trackingNumber);
                }
            } else {
                $xml->element('ShipperNumber', $this->accountNumber);
                $xml->push('ReferenceNumber');
                    $xml->element('Value', $this->referenceNumber);
                $xml->pop(); // close ReferenceNumber
            }
        $xml->pop();

        // Convert xml object to a string
        return $xml->getXml();
    }

    // Builds xml for tracking and sends the xml string to the ups->request method
    // receives a response from UPS and outputs an array.
    function trackUPS($trackingNumber)
    {
        $xmlString = $this->buildUPSTrackingXml($trackingNumber);

        // Send the xmlString to UPS and store the resonse in a class variable, xmlResponse.
        $this->core->request('Track', $xmlString);

        $this->processResponse();

        return $this->responseArray; 
    }
}
