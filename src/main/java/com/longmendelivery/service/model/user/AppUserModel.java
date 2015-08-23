package com.longmendelivery.service.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.persistence.entity.ShipOrderEntity;
import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.AddressModel;
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
public class AppUserModel implements DTOModel {

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
    private String verificationCode;

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private AddressModel address;

    @JsonProperty
    private Set<ShipOrderEntity> orders;
}
