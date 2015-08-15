package com.longmendelivery.lib.client.shipment.rocketshipit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by desmond on 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum ServiceType {
    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01"),
    FEDEX_GROUND(CourierType.FEDEX, "FedEx Ground", "FEDEX_GROUND");

    public static ServiceType getFromServiceCode(CourierType courier, String serviceCode) {
        for (ServiceType service : ServiceType.values()) {
            if (service.getCourier().equals(courier) && service.getServiceCode().equals(serviceCode)) {
                return service;
            }
        }
        return null;
    }

    private CourierType courier;
    private String description;
    private String serviceCode;
}
