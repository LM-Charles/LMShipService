package com.longmendelivery.service.model.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by rabiddesire on 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum CourierServiceType {
    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11", "ECONOMY_COURIER", "/public/image/ups.png"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02", "MEDIUM_COURIER", "/public/image/ups.png"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13", "ECONOMY_COURIER", "/public/image/ups.png"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01", "FASTEST_COURIER", "/public/image/ups.png"),
    UPS_EXPRESS_EARLY_AM(CourierType.UPS, "UPS Express Early AM", "14", "FASTEST_COURIER", "/public/image/ups.png"),
    UPS_INTERNATIONAL_FIRST(CourierType.UPS, "UPS International First", "07", "FASTEST_COURIER", "/public/image/ups.png"),
    UPS_INTERNATIONAL_PRIORITY(CourierType.UPS, "UPS International Priority", "08", "MEDIUM_COURIER", "/public/image/ups.png"),
    UPS_INTERNATIONAL_SAVER(CourierType.UPS, "UPS World Wide Saver", "65", "ECONOMY_COURIER", "/public/image/ups.png"),

    FIRST_OVERNIGHT(CourierType.FEDEX, "First Overnight", "FIRST_OVERNIGHT", "FASTEST_COURIER", "/public/image/fedex.jpg"),
    PRIORITY_OVERNIGHT(CourierType.FEDEX, "Priority Overnight", "PRIORITY_OVERNIGHT", "FASTEST_COURIER", "/public/image/fedex.jpg"),
    STANDARD_OVERNIGHT(CourierType.FEDEX, "Standard Overnight", "STANDARD_OVERNIGHT", "FASTEST_COURIER", "/public/image/fedex.jpg"),
    FEDEX_2_DAY(CourierType.FEDEX, "Fedex 2 Day", "FEDEX_2_DAY", "MEDIUM_COURIER", "/public/image/fedex.jpg"),
    FEDEX_EXPRESS_SAVER(CourierType.FEDEX, "FedEx Express Saver", "FEDEX_EXPRESS_SAVER", "ECONOMY_COURIER", "/public/image/fedex.jpg"),
    FEDEX_GROUND(CourierType.FEDEX, "FedEx Ground", "FEDEX_GROUND", "ECONOMY_COURIER", "/public/image/fedex.jpg"),

    CANADA_EXPEDITED(CourierType.CANADA_POST, "Expedited Parcel", "DOM.EP", "FASTEST_COURIER", "/public/image/canadapost.jpg"),
    CANADA_PRIORITY(CourierType.CANADA_POST, "Priority", "DOM.PC", "MEDIUM_COURIER", "/public/image/canadapost.jpg"),
    CANADA_REGULAR(CourierType.CANADA_POST, "Regular Parcel", "DOM.RP", "ECONOMY_COURIER", "/public/image/canadapost.jpg"),
    CANADA_EXPRESS(CourierType.CANADA_POST, "Xpresspost", "DOM.XP", "FASTEST_COURIER", "/public/image/canadapost.jpg"),

    CANADA_INTERNATIONAL_SMALL_AIR(CourierType.CANADA_POST, "Small Packet International Air", "INT.SP.AIR", "MEDIUM_COURIER", "/public/image/canadapost.jpg"),
    CANADA_INTERNATIONAL_SMALL_SURFACE(CourierType.CANADA_POST, "Small Packet International Surface", "INT.SP.SURF", "ECONOMY_COURIER", "/public/image/canadapost.jpg"),

    CANADA_INTERNATIONAL_SURFACE(CourierType.CANADA_POST, "International Parcel Surface", "INT.IP.SURF", "ECONOMY_COURIER", "/public/image/canadapost.jpg"),
    CANADA_INTERNATIONAL_TRACKED_PARCEL(CourierType.CANADA_POST, "International Tracked Parcel", "INT.TP", "ECONOMY_COURIER", "/public/image/canadapost.jpg"),

    CANADA_INTERNATIONAL_PAK(CourierType.CANADA_POST, "Priority Worldwide Pak", "INT.PW.PAK", "MEDIUM_COURIER", "/public/image/canadapost.jpg"),
    CANADA_INTERNATIONAL_PARCEL(CourierType.CANADA_POST, "Priority Worldwide Parcel", "INT.PW.PAK", "MEDIUM_COURIER", "/public/image/canadapost.jpg"),

    CANADA_INTERNATIONAL_EXPRESS(CourierType.CANADA_POST, "Xpresspost International", "INT.XP", "FASTEST_COURIER", "/public/image/canadapost.jpg"),

    LONGMEN_STANDARD(CourierType.LONGMEN, "Long Men Standard", "LONGMEN_STANDARD", "MEDIUM_COURIER", "/public/image/longmen.png"),

    LONGMEN_HANDLING(CourierType.LONGMEN, "Long Men Handling Fee", "LONGMEN_HANDLING", "HANDLING", "/public/image/longmen.png"),
    LONGMEN_INSURANCE(CourierType.LONGMEN, "Long Men Insurance", "LONGMEN_INSURANCE", "INSURANCE", "/public/image/longmen.png");

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
