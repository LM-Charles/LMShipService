package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by desmond on 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum CourierServiceType {
    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11", "ECONOMY_COURIER"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02", " MEDIUM_COURIER"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13", "ECONOMY_COURIER"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01", "FASTEST_COURIER"),
    UPS_EXPRESS_EARLY_AM(CourierType.UPS, "UPS Express Early AM", "14", "FASTEST_COURIER"),

    FEDEX_GROUND(CourierType.FEDEX, "FedEx Ground", "FEDEX_GROUND", "ECONOMY_COURIER"),
    LONGMEN_STANDARD(CourierType.LONGMEN, "Long Men Standard", "LONGMEN_STANDARD", "MEDIUM_COURIER");

    public static CourierServiceType getFromServiceCode(CourierType courier, String serviceCode) {
        for (CourierServiceType service : CourierServiceType.values()) {
            if (service.getCourier().equals(courier) && service.getServiceCode().equals(serviceCode)) {
                return service;
            }
        }
        return null;
    }

    private CourierType courier;
    private String description;
    private String serviceCode;
    public String category;
}
