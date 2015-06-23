<?php

namespace RocketShipIt\Service\Track;
use \RocketShipIt\Helper\XmlParser;

/**
* Main class for tracking shipments and packages
*
* This class is a wrapper for use with all carriers to track packages
* Valid carriers are: UPS, USPS, and FedEx.
*/
class Canada extends \RocketShipIt\Service\Common
{
    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    function trackCANADA($trackingNumber)
    {
        $this->core->request('/vis/track/pin/'. $trackingNumber. '/detail', '');

        // Convert the xmlString to an array
        return $this->arrayFromXml($this->core->xmlResponse);
    }
}
