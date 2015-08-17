package com.longmendelivery.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.lib.conversion.Model;
import com.longmendelivery.persistence.entity.AppUserGroupType;
import com.longmendelivery.persistence.entity.AppUserStatusType;
import com.longmendelivery.persistence.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

/**
 * Created by desmond on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserModel implements Model {

    @JsonProperty
    @NonNull
    private Integer id;
    @JsonProperty
    @NonNull
    private String phone;
    @JsonProperty
    @NonNull
    private String email;
    @JsonProperty
    @NonNull
    private String password_md5;
    @JsonProperty
    @NonNull
    private AppUserGroupType userGroup;
    @JsonProperty
    @NonNull
    private AppUserStatusType userStatus;
    @JsonProperty
    @NonNull
    private String apiToken;
    @JsonProperty
    @NonNull
    private VerificationCodeModel verificationCode;

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private AddressModel address;

    @JsonProperty
    private Set<OrderEntity> orders;
}
