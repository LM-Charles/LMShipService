<?php
require 'autoload.php'; // This autoloads RocketShipIt classes

$rate = new \RocketShipIt\Rate('UPS');

$rate->setParameter('shipCode','V4C0A9');
$rate->setParameter('shipCountry','CA');
$rate->setParameter('toCode','V7C2X4');
$rate->setParameter('toCountry','CA');
$rate->setParameter('weight','5');
$rate->setParameter('residentialAddressIndicator','0');
$rate->setParameter('packagingType','02');

$response = $rate->getSimpleRates();
print_r($response);
