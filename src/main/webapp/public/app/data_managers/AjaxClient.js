/**
 * Created by desmondz on 7/31/2015.
 */

function AjaxClient() {
    this.ajaxLoadOrder = function (orderNumber) {
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderNumber + "?token=userToken",
            "datatype": "json",
            Accept: "application/json",
            contentType: "application/json",
            "method": "GET",
            "headers": {}
        };

        var result = $.ajax(settings);
        return JSON.parse(result.responseText);
    };

    this.ajaxSaveTracking = function (orderId, shipmentId, trackingNumber) {
        var tracking = {
            "trackingNumber": trackingNumber
        };
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderId + "/tracking/" + shipmentId + "?token=userToken",
            "data": JSON.stringify(tracking),
            "datatype": "json",
            Accept: "application/json",
            contentType: "application/json",
            "method": "POST",
            "headers": {}
        };

        var result = $.ajax(settings);
        return JSON.parse(result.responseText);
    };

    this.ajaxUpdateDimension = function (orderId, shipmentId, length, width, height, packaging, weight) {
        var tracking = {
            "length": length,
            "width": width,
            "height": height,
            "weight": weight
        };
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderId + "/dimension/" + shipmentId + "?packageType=" + packaging + "&token=userToken",
            "data": JSON.stringify(tracking),
            "datatype": "json",
            Accept: "application/json",
            contentType: "application/json",
            "method": "POST",
            "headers": {}
        };

        var result = $.ajax(settings);
        return JSON.parse(result.responseText);
    }
}