package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum CourierServiceType {
    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01"),
    FEDEX_GROUND(CourierType.FEDEX, "FedEx Ground", "FEDEX_GROUND"),
    LONGMEN_STANDARD(CourierType.LONGMEN, "Long Men Standard", "LONGMEN_STANDARD");

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
}
