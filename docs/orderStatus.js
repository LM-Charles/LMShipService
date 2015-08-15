var orderStatusResponse = {
    "status": "##represent the status of the order for LM##",
    "serviceInfo": {
        "serviceId": "##the service id assigned by LM to manage the service##",
        "serviceCategory": "##the service category assigned by LM to manage the service##",
        "courier": "## the underlying courier name: eg UPS ##",
        "courierServiceCode": "## the code used by courier to represent the service, eg. 01 ##",
        "courierServiceDescription": "## the descriptor used by courier to display the service 3 Day Air ##"
    },
    "tracking": {
        "trackingNumber": "## string for searching this tracking ##",
        "trackingURL": "## the URL for accessing the tracking page for this shipment ##",
        "startDate": "## timestamp for the shipment confirmed to be in courier system ##",
        "packages": [
            {
                "trackingNumber": "## tracking number for the individual package under this shipment",
                "activityStatus": "## most recent status, courier provided human readable status, eg. DELIVERED ##",
                "activityDate": "## timestamp of most recent status update",
                "activityLocation": "## address string for the location of this activity ##"
            }
            // more packages here...
        ]
    }
};


// orderStatus represent the status of the order for LM
// ORDER_PLACED - Customer confirmed the order, driver appointment is set to pick up
// PROCESSING - Picked up and with LM, before sent to courier and obtaining tracking number
// IN_TRANSIT - After entering the tracking number and handed over to courier
// COMPLETE - Confirmed to be complete

// shipmentTracking is only present if the order is IN_TRANSIT, and contains courier information
