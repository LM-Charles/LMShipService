package com.longmendelivery.service.model.order;

import com.longmendelivery.service.model.DTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by desmond on 21/06/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusUpdateRequest implements DTOModel {
    @NonNull
    private OrderStatusType status;
    private String statusDescription;
}
