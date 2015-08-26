package com.longmendelivery.service.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by desmond on 15/08/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusModel {
    private OrderStatusType status;
    private String statusDescription;
    private String handler;
    private DateTime statusDate;
}
