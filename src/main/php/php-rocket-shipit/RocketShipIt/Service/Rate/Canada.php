<?php

namespace RocketShipIt\Service\Rate;

use RocketShipIt\Helper\XmlBuilder;

/**
* Main Rate class for producing rates for various packages/shipments
*
* This class is a wrapper for use with all carriers to produce rates 
* Valid carriers are: UPS, USPS, Stamps.com, DHL, and FedEx.
*/
class Canada extends \RocketShipIt\Service\Common implements \RocketShipIt\RateInterface
{
    var $packageCount;
    
    function __construct()
    {
        $classParts = explode('\\', __CLASS__);
        $carrier = end($classParts);
        parent::__construct($carrier);
    }

    function getAllRates()
    {
        $header = array('Content-Type: application/vnd.cpc.ship.rate+xml',
                        'Accept: application/vnd.cpc.ship.rate+xml');
        $response = $this->core->request('/rs/ship/price', $this->buildCANADARateXml(), $header);

        if (is_array($response)) {
            // Error
            return $response;
        }

        return $this->arrayFromXml($this->core->xmlResponse);
    }

    function buildCANADARateXml()
    {
        $xml = new xmlBuilder();
        $xml->push('mailing-scenario', array('xmlns' => "http://www.canadapost.ca/ws/ship/rate"));
            if (!empty($this->accountNumber)) {
                $xml->element('customer-number', $this->accountNumber);
            } else {
                $xml->element('quote-type', 'counter');
            }
            $xml->push('parcel-characteristics');
                $xml->element('weight', $this->weight);
                if ($this->length != '' && $this->width != '' && $this->height != '') {
                    $xml->push('dimensions');
                        $xml->element('length', $this->length);
                        $xml->element('width', $this->width);
                        $xml->element('height', $this->height);
                    $xml->pop(); // end dimensions
                }
            $xml->pop(); // end parcel-characteristics

            if (@$this->insuredValue != '') {
                $xml->push('options');
                    $xml->push('option');
                        $xml->element('option-code', 'COV');
                        $xml->element('option-amount', $this->insuredValue);
                    $xml->pop(); // end option
                $xml->pop(); // end options
            }

        $xml->element('origin-postal-code', strtoupper(preg_replace('/[^0-9a-z]/i', '', $this->shipCode)));
            $xml->push('destination');
                if ($this->toCountry == 'CA') {
                    $xml->push('domestic');
                    $xml->element('postal-code', strtoupper(preg_replace('/[^0-9a-zA-Z]/i', '', $this->toCode)));
                    $xml->pop(); // end domestic
                } else if ($this->toCountry == 'US') {
                    $xml->push('united-states');
                    $xml->element('zip-code', strtoupper(preg_replace('/[^0-9-]/i', '', $this->toCode)));
                    $xml->pop(); // end domestic
                }
                else {
                    $xml->push('international');
                        $xml->element('country-code', $this->toCountry);
                    $xml->pop(); // end domestic
                }
            $xml->pop(); // end destination
        $xml->pop(); // end mailing-scenario
        return $xml->getXml();
    }

    public function getServiceDescriptionFromName($name)
    {
        return "Canada Post $name";
    }

    function getSimpleRates($user_func=null)
    {
        $canada = $this->getAllRates();
        print("CANADA: $canada");
        if (isset($canada['error'])) {
            // Error
            return $canada;
        }
        $rates = array();
        foreach ($canada['price-quotes']['price-quote'] as $s) {
            //$price = $s['price-details']['base']; // without taxes
            $price = $s['price-details']['due'];
            $simpleRate = array('desc' => $this->getServiceDescriptionFromName($s['service-name']), 'rate' => $price, 'service_code' => $s['service-code']);
            if (!empty($user_func)) {
                $simpleRate = call_user_func($user_func, end(explode('\\', __CLASS__)), $s, $simpleRate);
            }
            $rates[] = $simpleRate;
         }
         return $rates;
    }

    // Checks the country to see if the request is International
    function isInternational($country) {
        if ($country == '' ||
            $country == 'US' ||
            $country == $this->inherited->core->getCountryName('US') ||
            $country == '' ||
            $country == 'CA' ||
            $country == $this->inherited->core->getCountryName('CA')) {
            return false;
        }
        return true;
    }

    public function getRate()
    {
        return 'Method not available';
    }

    public function addPackageToShipment($packageObj)
    {
        return 'method not available';
    }

    public function getSimpleRate()
    {
        return 'method not available';
    }
}
