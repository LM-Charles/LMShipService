package com.longmendelivery.service.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.longmendelivery.service.model.shipment.ShipmentTrackingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by  rabiddesireon 15/08/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusResponse {
    @JsonProperty
    private ShipOrderModel order;

    private OrderStatusType status;
    private String statusDescription;
    private Map<ShipmentModel, ShipmentTrackingResponse> shipmentTracking;
}
