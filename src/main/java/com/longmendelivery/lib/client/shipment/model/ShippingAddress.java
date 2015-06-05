package com.longmendelivery.lib.client.shipment.model;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class ShippingAddress {
    protected String country;
    protected String province;
    protected String address;

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

    protected String postalCode;

    public ShippingAddress(String country, String province, String address, String postalCode){
        this.country = country;
        this.province = province;
        this.address = address;
        this.postalCode = postalCode;
    }
}
