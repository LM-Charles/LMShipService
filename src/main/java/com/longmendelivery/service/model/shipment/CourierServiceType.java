package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by desmond on 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum CourierServiceType {

    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11", "ECONOMY_COURIER", "/public/image/ups.png"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02", "MEDIUM_COURIER", "/public/image/ups.png"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13", "ECONOMY_COURIER", "/public/image/ups.png"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01", "FASTEST_COURIER", "/public/image/ups.png"),
    UPS_EXPRESS_EARLY_AM(CourierType.UPS, "UPS Express Early AM", "14", "FASTEST_COURIER", "/public/image/ups.png"),

    FEDEX_GROUND(CourierType.FEDEX, "FedEx Ground", "FEDEX_GROUND", "ECONOMY_COURIER", "/public/image/fedex.jpg"),

    CANADA_EXPEDITED(CourierType.CANADA_POST, "Canada Expedited Post", "DOM.EP", "FASTEST_COURIER", "/public/image/canadapost.jpg"),
    CANADA_PRIORITY(CourierType.CANADA_POST, "Canada Priority Post", "DOM.PC", "MEDIUM_COURIER", "/public/image/canadapost.jpg"),
    CANADA_REGULAR(CourierType.CANADA_POST, "Canada Regular Post", "DOM.RP", "ECONOMY_COURIER", "/public/image/canadapost.jpg"),
    CANADA_EXPRESS(CourierType.CANADA_POST, "Canada Express Post", "DOM.XP", "FASTEST_COURIER", "/public/image/canadapost.jpg"),


    LONGMEN_STANDARD(CourierType.LONGMEN, "Long Men Standard", "LONGMEN_STANDARD", "MEDIUM_COURIER", "/public/image/longmen.jpg"),

    LONGMEN_HANDLING(CourierType.LONGMEN, "Long Men Handling Fee", "LONGMEN_HANDLING", "HANDLING", "/public/image/longmen.jpg"),
    LONGMEN_INSURANCE(CourierType.LONGMEN, "Long Men Insurance", "LONGMEN_INSURANCE", "INSURANCE", "/public/image/longmen.jpg");

    public static CourierServiceType getFromServiceCode(CourierType courier, String serviceCode) {
        for (CourierServiceType service : CourierServiceType.values()) {
            if (service.getCourier().equals(courier) && service.getServiceCode().equals(serviceCode)) {
                return service;
            }
        }
        System.out.println("[WARN] new service discovered: " + courier + " service: " + serviceCode);
        return null;
    }

    private CourierType courier;
    private String description;
    private String serviceCode;
    public String category;
    private String iconURL;
}
