package com.longmendelivery.service.model.user;

import com.longmendelivery.service.model.DTOModel;
import com.longmendelivery.service.model.order.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by rabiddesire on 04/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest implements DTOModel {
    @NonNull
    private String phone;
    @NonNull
    private String email;

    private String firstName;
    private String lastName;
    private AddressModel address;

    @NonNull
    private String password;

}
