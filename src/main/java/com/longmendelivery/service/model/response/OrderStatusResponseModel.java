package com.longmendelivery.service.model.response;

import com.longmendelivery.service.model.ShipmentModel;
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
public class OrderStatusResponseModel {
    private String status;
    private String statusDescription;
    private Map<ShipmentModel, ShipmentTrackingResponseModel> shipmentTracking;
}
