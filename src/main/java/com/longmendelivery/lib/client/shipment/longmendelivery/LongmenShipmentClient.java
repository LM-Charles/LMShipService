package com.longmendelivery.lib.client.shipment.longmendelivery;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.courier.CourierServiceType;
import com.longmendelivery.service.model.courier.CourierType;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.PackageDimensionModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

/**
 * Created by  rabiddesireon 15/08/15.
 */
public class LongmenShipmentClient implements ShipmentClient {

    private ShipmentStorage shipmentStorage;

    LongmenShipmentClient(ShipmentStorage shipmentStorage) {
        this.shipmentStorage = shipmentStorage;
    }
    @Override
    public Map<CourierServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, PackageDimensionModel dimension) throws DependentServiceException {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal weightInLb = BigDecimal.valueOf(dimension.getWeight()).multiply(new BigDecimal("2.20462"));

        // Weight Cost
        total = total.add(weightInLb.multiply(new BigDecimal("5")));

        // Initial Weight Surcharge
        if (weightInLb.compareTo(new BigDecimal("10")) < 0) {
            total = total.add(new BigDecimal("10"));
        }

        // Box Surcharge
        BigDecimal boxCount = weightInLb.divide(new BigDecimal("6"), 0, RoundingMode.CEILING);
        total = total.add(boxCount);

        // Box Weight Surcharge
        total = total.add(boxCount.multiply(new BigDecimal("5")));

        return Collections.singletonMap(CourierServiceType.LONGMEN_STANDARD, total);
    }

    @Override
    public ShipmentTrackingResponseModel getTracking(CourierType type, String shipmentId) throws DependentServiceException, ResourceNotFoundException {
        ShipmentEntity shipment = shipmentStorage.get(Integer.valueOf(shipmentId));
        DateTime pickUpDate = shipment.getOrder().getOrderDate();
        DateTime trackDate = shipment.getOrder().getOrderStatus().get(0).getStatusDate();
        String trackingLocation = "N/A";
        String trackingStatus = shipment.getOrder().getOrderStatus().get(0).getStatusDescription();
        return new ShipmentTrackingResponseModel(pickUpDate, trackDate, trackingLocation, trackingStatus);
    }
}
