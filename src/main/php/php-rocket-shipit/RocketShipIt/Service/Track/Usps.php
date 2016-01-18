<?php

namespace RocketShipIt\Service\Track;

use \RocketShipIt\Helper\XmlParser;
use \RocketShipIt\Helper\XmlBuilder;

/**
* Main class for tracking shipments and packages
*
* This class is a wrapper for use with all carriers to track packages
* Valid carriers are: UPS, USPS, and FedEx.
*/
class Usps extends \RocketShipIt\Service\Common
{
    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    function trackUSPS($trackingNumber)
    {
        $xml = $this->core->xmlObject;

        $xml->push('TrackFieldRequest',array('USERID' => $this->userid));
            $xml->element('Revision', '1');
            $xml->element('ClientIp', '127.0.0.1');
            $xml->element('SourceId', 'RocketShipIt');
            $xml->push('TrackID',array('ID' => $trackingNumber));
            $xml->pop(); // end TrackID
        $xml->pop(); // end TrackRequest

        $xmlString = $xml->getXml();

        $postData = $this->buildPostData(array(
            'api' => 'TrackV2',
            'xml' => $xmlString,
        ));

        $this->core->request('ShippingAPI.dll', $postData);

        // Convert the xmlString to an array
        return $this->arrayFromXml($this->core->xmlResponse);
    }

    public function notify()
    {
        $postData = $this->buildPostData(array(
            'api' => 'PTSEmail',
            'xml' => $this->buildNotificationXml(),
        ));

        $this->core->request('ShippingAPI.dll', $postData);

        return $this->arrayFromXml($this->core->xmlResponse);
    }

    function buildPostData($params)
    {
        return sprintf('API=%s&XML=%s', $params['api'], $params['xml']);
    }

    public function buildNotificationXml()
    {
        $xml = $this->core->xmlObject;
        $xml->push('PTSEmailRequest', array('USERID' => $this->userid));
            $xml->element('TrackId', $this->trackingNumber);
            $xml->element('MpSuffix', $this->mpSuffix);
            $xml->element('MpDate', $this->mpDate);
            if ($this->requestType != '') {
                $xml->element('RequestType', $this->requestType);
            } else {
                $xml->element('RequestType', 'EB');
            }
            $xml->element('Email1', $this->email);
            $xml->element('Email2', $this->email2);
            $xml->element('Email3', $this->email3);
        $xml->pop(); // end PTSEmailRequest 
        $xmlString = $xml->getXml();

        return $xmlString; 
    }
}
