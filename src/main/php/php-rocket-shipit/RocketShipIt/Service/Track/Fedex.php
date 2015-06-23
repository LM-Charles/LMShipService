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
class Fedex extends \RocketShipIt\Service\Common
{

    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    function trackFEDEX($trackingNumber)
    {
        $xml = $this->core->xmlObject;
        $xml->push('ns:TrackRequest', array('xmlns:ns' => 'http://fedex.com/ws/track/v4', 'xmlns:xsi' => 'http://www.w3.org/2001/XMLSchema-instance', 'xsi:schemaLocation' => 'http://fedex.com/ws/track/v4 TrackService v4.xsd'));
        $this->core->xmlObject = $xml;
        $this->core->access();
        $xml = $this->core->xmlObject;

        $xml->push('ns:Version');
            $xml->element('ns:ServiceId','trck');
            $xml->element('ns:Major','4');
            $xml->element('ns:Intermediate','0');
            $xml->element('ns:Minor','0');
        $xml->pop(); // end Version
        $xml->push('ns:PackageIdentifier');
            $xml->element('ns:Value', $trackingNumber);
            $xml->element('ns:Type', $this->trackingIdType);
        $xml->pop(); // end PackageIdentifier
        if ($this->trackingIdType != 'TRACKING_NUMBER_OR_DOORTAG') {
            $xml->element('ns:ShipmentAccountNumber', $this->accountNumber);
        }
		$xml->element('ns:IncludeDetailedScans', 'true');

        $xml->pop(); // end TrackRequest

        $xmlString = $xml->getXml();

        $this->core->request($xmlString);

        // Convert the xmlString to an array
        return $this->arrayFromXml($this->core->xmlResponse);
    }

    function buildVersionXml()
    {
        $xml = new XmlBuilder(true);
        $xml->push('ns:Version');
            $xml->element('ns:ServiceId','trck');
            $xml->element('ns:Major','6');
            $xml->element('ns:Intermediate','0');
            $xml->element('ns:Minor','0');
        $xml->pop(); // end Version
        return $xml->getXml();
    }

    function buildQualifiedTrackingNumberXml()
    {
        $xml = new XmlBuilder(true);
        $xml->push('ns:QualifiedTrackingNumber');
            $xml->element('ns:TrackingNumber', $this->trackingNumber);
            $xml->element('ns:ShipDate', $this->shipDate);
            $xml->element('ns:AccountNumber', $this->accountNumber);
            //$xml->element('ns:Carrier', 'FDXG'); // {'FDXC'|'FDXE'|'FDXG'|'FXCC'|'FXFR'|'FXSP'}
        $xml->pop(); // end QualifiedTrackingNumber
        return $xml->getXml();
    }

    function buildContactXml()
    {
        $xml = new XmlBuilder(true);
            $xml->push('ns:Contact');
                $xml->element('ns:PersonName', $this->toName);
                $xml->element('ns:Title', 'Mr');
                $xml->element('ns:CompanyName', $this->toCompany);
                $xml->element('ns:PhoneNumber', $this->toPhone);
                $xml->element('ns:EMailAddress', $this->toEmail);
            $xml->pop();
        return $xml->getXml();
    }

    function buildAddressXml()
    {
        $xml = new XmlBuilder(true);
        $xml->push('ns:Address');
            $xml->element('ns:StreetLines', $this->toAddr1);
            $xml->element('ns:City', $this->toCity);
            $xml->element('ns:StateOrProvinceCode', $this->toState);
            $xml->element('ns:PostalCode', $this->toCode);
            //$xml->element('UrbanizationCode', ''); // Only for PR
            $xml->element('ns:CountryCode', $this->toCountry);
            $xml->element('ns:Residential', $this->residential);
        $xml->pop();
        return $xml->getXml();
    }

    function buildProofOfDeliveryXml()
    {
        $xml = $this->core->xmlObject;
        $xml->push('ns:SignatureProofOfDeliveryLetterRequest',array('xmlns:ns' => 'http://fedex.com/ws/track/v6', 'xmlns:xsi' => 'http://www.w3.org/2001/XMLSchema-instance', 'xsi:schemaLocation' => 'http://fedex.com/ws/track/v6 TrackService_v6.xsd'));
        $this->core->xmlObject = $xml;
        $this->core->access();
        $xml = $this->core->xmlObject;
            $xml->push('ns:TransactionDetail');
                $xml->element('ns:CustomerTransactionId', 'RocketShipIt'); // Customer String echoed back
                $xml->push('ns:Localization');
                    $xml->element('ns:LanguageCode', 'EN');
                    $xml->element('ns:LocaleCode', 'us');
                $xml->pop();
            $xml->pop(); // end TransactionDetail
            $xml->append($this->buildVersionXml());
            $xml->append($this->buildQualifiedTrackingNumberXml());
            $xml->element('ns:AdditionalComments', $this->additionalComments);
            $xml->element('ns:LetterFormat', 'PDF'); // {'PDF'}, pdf is only option right now
            $xml->push('ns:Consignee');
                $xml->append($this->buildContactXml());
                $xml->append($this->buildAddressXml());
            $xml->pop();
        $xml->pop(); // end TrackRequest
        return $xml->getXml();
    }

    function getProofOfDelivery()
    {
        $this->core->request($this->buildProofOfDeliveryXml());

        // Convert the xmlString to an array
        return $this->arrayFromXml($this->core->xmlResponse);
    }
}
