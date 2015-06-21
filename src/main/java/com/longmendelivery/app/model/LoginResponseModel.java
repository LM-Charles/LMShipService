package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by  rabiddesireon 20/06/15.
 */
public class LoginResponseModel {
    @JsonProperty
    private final String apiToken;

    public String getApiToken() {
        return apiToken;
    }

    public Integer getId() {
        return id;
    }

    @JsonProperty
    private final Integer id;

    public LoginResponseModel(Integer id, String apiToken) {
        this.id = id;
        this.apiToken = apiToken;
    }
}
