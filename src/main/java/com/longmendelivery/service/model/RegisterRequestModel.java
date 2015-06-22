package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class RegisterRequestModel implements Model {

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
