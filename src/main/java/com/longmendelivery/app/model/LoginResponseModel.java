package com.longmendelivery.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;

/**
 * Created by desmond on 20/06/15.
 */
public class LoginResponseModel implements Model {
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
