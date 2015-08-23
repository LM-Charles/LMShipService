package com.longmendelivery.service.model.user;

import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by desmond on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserModel implements DTOModel {
    @NonNull
    private Integer id;
    @NonNull
    private String phone;
    @NonNull
    private String email;
    @NonNull
    private byte[] password_md5;
    @NonNull
    private AppUserGroupType userGroup;
    @NonNull
    private AppUserStatusType userStatus;
    @NonNull
    private String apiToken;
    @NonNull
    private String verificationCode;
    private String firstName;
    private String lastName;
    private AddressModel address;
}
