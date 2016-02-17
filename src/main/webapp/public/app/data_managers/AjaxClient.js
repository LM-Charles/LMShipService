/**
 * Created by rabiddesirez on 7/31/2015.
 */

function AjaxClient() {
    this.ajaxLogin = function (username, password) {
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/login?email=" + username + "&password=" + password,
            "datatype": "json",
            Accept: "application/json",
            contentType: "application/json",
            "method": "POST",
            "headers": {}
        };

        var result = $.ajax(settings);
        return JSON.parse(result.responseText);
    };

    this.ajaxLoadOrder = function (username, password, orderNumber) {
        var loginResponse = this.ajaxLogin(username, password);

        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderNumber + "/status?token=" + loginResponse.token + "&authId=" + loginResponse.id,
            "datatype": "json",
            Accept: "application/json",
            contentType: "application/json",
            "method": "GET",
            "headers": {}
        };

        var result = $.ajax(settings);
        return JSON.parse(result.responseText);
    };

    this.ajaxSaveTracking = function (username, password, orderId, shipmentId, trackingNumber) {
        var loginResponse = this.ajaxLogin(username, password);


        var tracking = {
            "trackingNumber": trackingNumber
        };
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderId + "/tracking/" + shipmentId + "?token=" + loginResponse.token + "&authId=" + loginResponse.id,
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

    this.ajaxUpdateDimension = function (username, password, orderId, shipmentId, length, width, height, packaging, weight) {
        var loginResponse = this.ajaxLogin(username, password);

        var tracking = {
            "length": length,
            "width": width,
            "height": height,
            "weight": weight
        };
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderId + "/dimension/" + shipmentId + "?packageType=" + packaging + "&token=" + loginResponse.token + "&authId=" + loginResponse.id,
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

    this.ajaxUpdateStatus = function (username, password, orderId, newStatus, newStatusDescription) {
        var loginResponse = this.ajaxLogin(username, password);

        var tracking = {
            "status": newStatus,
            "statusDescription": newStatusDescription,
        };
        var settings = {
            "async": false,
            "crossDomain": true,
            "url": "/rest/order/" + orderId + "/status?token=" + loginResponse.token + "&authId=" + loginResponse.id,
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