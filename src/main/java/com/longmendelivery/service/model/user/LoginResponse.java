package com.longmendelivery.service.model.user;

import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by rabiddesire on 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements DTOModel {
    @NonNull
    private Integer id;
    @NonNull
    private String apiToken;
    private AppUserStatusType status;
}
