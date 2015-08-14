package com.longmendelivery.lib.client.shipment;

import com.longmendelivery.lib.client.shipment.rocketshipit.model.CourierType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by  rabiddesireon 04/06/15.
 */
@AllArgsConstructor
@Getter
public enum ShippingService {
    UPS_STANDARD(CourierType.UPS, "UPS Standard", "11"),
    UPS_WORLDWIDE_EXPEDITED(CourierType.UPS, "UPS Worldwide Expedited", "02"),
    UPS_SAVER(CourierType.UPS, "UPS Saver", "13"),
    UPS_EXPRESS(CourierType.UPS, "UPS Express", "01");

    public static ShippingService getFromServiceCode(CourierType courier, String serviceCode) {
        for (ShippingService service : ShippingService.values()) {
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
