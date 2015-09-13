package com.longmendelivery.service.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by  rabiddesireon 05/07/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateEntryModel {
    private String service_icon_url;
    private String category;
    private BigDecimal estimate;
    private BigDecimal taxEstimate;
    private String courierName;
    private String serviceName;

    private DateTime estimatedDelivery;
}
