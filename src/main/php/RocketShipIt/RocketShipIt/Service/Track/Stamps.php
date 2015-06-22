<?php

namespace RocketShipIt\Service\Track;

use \RocketShipIt\Helper\XmlParser;
use \RocketShipIt\Helper\XmlBuilder;

/**
* Main class for tracking shipments and packages
*
* This class is a wrapper for use with all carriers to track packages
*/
class Stamps extends \RocketShipIt\Service\Common
{
    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    public function trackSTAMPS($trackingNumber)
    {
        $creds = $this->core->getCredentials();
        $request['Credentials'] = $creds;
        // You can also track by StampsTxID
        //$request['StampsTxID'] = $trackingNumber;
        $request['TrackingNumber'] = $trackingNumber;
        $response = $this->core->request('TrackShipment', $request);
        return $response;
    }
}
