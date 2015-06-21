package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class ChangeUserDetailRequestModel {

    @JsonProperty
    private String phone;
    @JsonProperty
    private String email;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String address;
    @JsonProperty
    private String city;
    @JsonProperty
    private String province;
    @JsonProperty
    private String country;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ChangeUserDetailRequestModel(String phone, String email, String firstName, String lastName, String address, String city, String province, String country) {

        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public ChangeUserDetailRequestModel() {

    }
}
