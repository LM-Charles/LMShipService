package com.longmendelivery.service.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by  rabiddesireon 20/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseModel implements DTOModel {
    @JsonProperty
    @NonNull
    private String message;

}
