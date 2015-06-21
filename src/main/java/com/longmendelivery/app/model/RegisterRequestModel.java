package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by desmond on 04/06/15.
 */
public class RegisterRequestModel {

    @JsonProperty
    private String phone;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;

    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }

    public RegisterRequestModel(String phone, String email, String password) {

        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public RegisterRequestModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
