package com.longmendelivery.lib.client.shipment.model;

/**
 * Created by desmond on 04/06/15.
 */
public class ShippingAddress {
    private final String country;
    private final String province;
    private final String address;

    public String getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    private final String postalCode;

    public ShippingAddress(String country, String province, String address, String postalCode){
        this.country = country;
        this.province = province;
        this.address = address;
        this.postalCode = postalCode;
    }
}
