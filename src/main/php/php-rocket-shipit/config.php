<?php

// Copyright RocketShipIt LLC All Rights Reserved
// For Support email: support@rocketship.it

// Feel free to modify the following defaults:
return array(
    /**
    * This is used to set generic defaults.  I.e. They are 
    * not carrier-specific.
    *
    * These defaults will be used across all carriers.  They can be 
    * overwritten on the 
    * shipment/package level.
    */
    'generic' => array(
        // 1 for Debug mode, 0 for normal operations
        // This also changes from testing to production mode
        'debugMode' => 1,

        // Default timezone
        // You can find out which timezones are available here: 
        // http://php.net/manual/en/timezones.php
        'timezone' => 'America/Vancouver',

        // Your company name
        'shipper' => 'Long Men',

        // Key shipping contact individual at your company
        'shipContact' => 'Charles',

        'shipAddr1' => '9188 Hemlock Drive',
        'shipAddr2' => '',
        'shipCity' => 'Richmond',
        'shipState' => 'BC',
        'shipCode' => 'V4C0A9',
        'shipCountry' => 'CA',

        // Phone number in this format: 1234567890
        'shipPhone' => '1231231234',
        'toCountry' => 'CA',

        // General currency for things like COD
        'currency' => 'CAD',
    ),
    //}}

    /**
    * This is used to set FedEx specfic defaults.
    *
    * These defaults will be used for FedEx calls only.  They can be 
    * overwritten on the 
    * shipment/package level using the setParameter() function.
    */

    'fedex' => array(
        'key' => 'zsbcq5Rq25vlCGLd',
        'password' => '8Swwmx7v0JDNX1d4nGfaPpoIH',
        'accountNumber' => '510088000',
        'meterNumber' => '118685333',

        'packagingType' => 'YOUR_PACKAGING',
        'weightUnit' => 'KG',
        'lengthUnit' => 'CM',

        // EUROPE_FIRST_INTERNATIONAL_PRIORITY
        // FEDEX_1_DAY_FREIGHT
        // FEDEX_2_DAY
        // FEDEX_2_DAY_FREIGHT
        // FEDEX_3_DAY_FREIGHT
        // FEDEX_EXPRESS_SAVER
        // FEDEX_GROUND
        // FIRST_OVERNIGHT
        // GROUND_HOME_DELIVERY
        // INTERNATIONAL_ECONOMY
        // INTERNATIONAL_ECONOMY_FREIGHT
        // INTERNATIONAL_FIRST
        // INTERNATIONAL_PRIORITY
        // INTERNATIONAL_PRIORITY_FREIGHT
        // PRIORITY_OVERNIGHT
        // SMART_POST
        // STANDARD_OVERNIGHT
        // FEDEX_FREIGHT
        // FEDEX_NATIONAL_FREIGHT
        'service' => '',

        // REGULAR_PICKUP
        // REQUEST_COURIER
        // DROP_BOX
        // BUSINESS_SERVICE_CENTER
        // STATION
        'dropoffType' => 'REGULAR_PICKUP',

        // COLLECT
        // RECIPIENT
        // SENDER
        // THIRD_PARTY
        'paymentType' => 'SENDER',

        'labelFormatType' => '',
        'imageType' => '',
        'labelStockType' => '',
        'trackingIdType' => 'TRACKING_NUMBER_OR_DOORTAG',

        'insuredCurrency' => 'CAD',
        
        // COD (Collect On Delivery) - YES or NO
        'collectOnDelivery' => 'NO',
        
        // Hold at Location - YES or NO
        'holdAtLocation' => 'NO',
        
        // Saturday Delivery - YES or NO
        'saturdayDelivery' => 'NO',
        
        'codCollectionType' => 'ANY',
    ),
    //}}

    /**
    * This is used to set UPS specfic defaults.
    *
     * These defaults will be used for UPS calls only.  They can be
     * overwritten on the
    * shipment/package level using the setParameter() function.
    */
    'ups' => array(

        'license' => '0CF224653F2240B5',
        'username' => 'desmondzhou',
        'password' => 'Qwerty!1',

        // Make sure addresses are valid before label creation
        // validate, nonvalidate
        'verifyAddress' => 'nonvalidate',

        'labelPrintMethodCode' => '',
        'httpUserAgent' => '',
        'labelHeight' => '',
        'labelWidth' => '',
        'labelImageFormat' => '',

        // The following variables are for your UPS account
        // They typically don't change from shipment to shipment, although,
        // you may set any of them directly.
        // Your UPS Account number
        'accountNumber' => '0X0Y74',

        // Options
        // 01 - Daily Pickup
        // 03 - Customer Counter
        // 06 - One Time Pickup
        // 07 - On Call Air
        // 11 - Suggested Retail Rates
        // 19 - Letter Center
        // 20 - Air Service Center
        'PickupType' => '03',

        'weightUnit' => 'KGS',
        'lengthUnit' => 'CM',
        'insuredCurrency' => 'CAD',
        'toCountryCode' => 'CA',

        // The following variables set the defaults for individual shipments
        // you may set them here to save time, or you may set them explicitly
        // each time you use the classes.
        'shipmentDescription' => '',

        // Options
        // 01 - UPS Next Day Air
        // 02 - UPS Second Day Air
        // 03 - UPS Ground
        // 07 - UPS Worldwide Express
        // 08 - UPS Worldwide Expedited
        // 11 - UPS Standard
        // 12 - UPS Three-Day Select
        // 13 - Next Day Air Saver 
        // 14 - UPS Next Day Air Early AM
        // 54 - UPS Worldwide Express Plus
        // 59 - UPS Second Day Air AM
        // 65 - UPS Saver
        'service' => '',

        // Options
        // 01 - UPS Letter
        // 02 - Your Packaging
        // 03 - Tube
        // 04 - PAK
        // 21 - Express Box
        // 24 - 25KG Box
        // 25 - 10KG Box
        // 30 - Pallet
        // 2a - Small Express Box
        // 2b - Medium Express Box
        // 2c - Large Express Box
        'packagingType' => '02',
        'packageDescription' => '',

        // Set '0' for commercial '1' for residential
        'residentialAddressIndicator' => '0',

        // Set '0' for retail rates '1' for negotiated
        // You must turn this on with your UPS account rep
        'negotiatedRates' => '0',

        // Options
        // AJ Accounts Receivable Customer Account
        // AT Appropriation Number
        // BM Bill of Lading Number
        // 9V Collect on Delivery (COD) Number
        // ON Dealer Order Number
        // DP Department Number
        // 3Q Food and Drug Administration (FDA) Product Code
        // IK Invoice Number
        // MK Manifest Key Number
        // MJ DTOModel Number
        // PM Part Number
        // PC Production Code
        // PO Purchase Order Number
        // RQ Purchase Request Number
        // RZ Return Authorization Number
        // SA Salesperson Number
        // SE Serial Number
        // ST Store Number
        // TN Transaction Reference Number
        'referenceCode' => '',

        // Options
        // 2 - UPS Print and Mail (PNM)
        // 3 - UPS Return Service 1-Attempt (RS1)
        // 5 - UPS Return Service 3-Attempt (RS3)
        // 8 - UPS Electronic Return Label (ERL)
        // 9 - UPS Print Return Label (PRL) 
        'returnCode' => '',

        // Options
        // 00 - Rates Associated with Shipper Number
        // 01 - Daily Rates
        // 04 - Retail Rates
        // 53 - Standard List Rates
        'customerClassification' => '',
    ),

    'usps' => array(
        'userid' => '',
        'service' => '',
        'size' => '',
        'firstClassMailType' => '',
        'imageType' => 'PDF',
    ),

    'stamps' => array(
        'username' => '',
        'password' => '',
        'imageType' => '',
        'packagingType' => 'Package',
    ),

    'dhl' => array(

        'siteId' => '',
        'password' => '',
        'accountNumber' => '',
        'lengthUnit' => 'CM',
        'weightUnit' => 'KG',
        'trackingIdType' => 'AWBNumber',
        'labelPrintMethodCode' => 'PDF',
    ),

    'canada' => array(
        'username' => '4c21fdbc66bb1373',
        'password' => 'cf93d834da9cb506c31f4f',
        'accountNumber' => '0008379000',
        'service' => 'DOM.EP',

    ),

    'purolator' => array(

        'username' => '',
        'password' => '',
        'accountNumber' => '9999999999',
        'weightUnit' => 'kg',
        'pickupType' => 'PreScheduled',
        'service' => 'PurolatorExpress',
    ),
);
