<?php

namespace RocketShipIt;

class Pickup extends \RocketShipIt\Service\Base
{

	/**
     * Class constructor
     * 
     * @param $carrier
     */
    function __construct($carrier, $options=array())
    {
        $classParts = explode('\\', __CLASS__);
        $service = end($classParts);
        parent::__construct($carrier, $service, $options);
    }

    function getPickupRate()
    {
        switch ($this->carrier) {
            case "UPS":
                return (array) $this->inherited->upsRateRequest();
            case "FEDEX":
                return array();
            default:
                return $this->invalidCarrierResponse();
        }
    }

    function createPickupRequest()
    {
        $method = __FUNCTION__;
        if (!method_exists($this->inherited, $method)) {
            return $this->invalidCarrierResponse();
        }
        return (array) $this->inherited->$method();
    }
    
    function requestPendingStatus()
    {
        $method = __FUNCTION__;
        if (!method_exists($this->inherited, $method)) {
            return $this->invalidCarrierResponse();
        }
        return (array) $this->inherited->$method();
    }
    
    function cancelPickupRequest()
    {
        $method = __FUNCTION__;
        if (!method_exists($this->inherited, $method)) {
            return $this->invalidCarrierResponse();
        }
        return (array) $this->inherited->$method();
    }

}
