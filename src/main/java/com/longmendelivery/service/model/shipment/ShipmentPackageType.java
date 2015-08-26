package com.longmendelivery.service.model.shipment;

import java.util.EnumSet;

/**
 * Created by desmond on 23/08/15.
 */
public enum ShipmentPackageType {
    CUSTOM, LETTER, SMALL, MEDIUM, LARGE;
    public static final EnumSet<ShipmentPackageType> BOXES = EnumSet.range(SMALL, LARGE);
}
