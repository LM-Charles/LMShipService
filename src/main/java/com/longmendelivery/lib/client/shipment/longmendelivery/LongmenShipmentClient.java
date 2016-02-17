package com.longmendelivery.lib.client.shipment.longmendelivery;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.ShipmentClient;
import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.AddressModel;
import com.longmendelivery.service.model.order.GoodCategoryType;
import com.longmendelivery.service.model.shipment.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

/**
 * Created by rabiddesire on 15/08/15.
 */
@Component
public class LongmenShipmentClient implements ShipmentClient {

    @Autowired
    private ShipmentStorage shipmentStorage;

    public LongmenShipmentClient() {
    }

    @Override
    public Map<CourierServiceType, BigDecimal> getAllRates(AddressModel source, AddressModel destination, ShipmentModel shipmentModel) throws DependentServiceException {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal weightInLb = BigDecimal.valueOf(shipmentModel.getWeight()).multiply(new BigDecimal("2.20462"));
        Map<CourierServiceType, BigDecimal> map;
        if (shipmentModel.getGoodCategoryType().equals(GoodCategoryType.COSMETICS)) {
            map = calculateCosmetics(total, weightInLb);
        } else if (shipmentModel.getShipmentPackageType().equals(ShipmentPackageType.LETTER) && shipmentModel.getGoodCategoryType().equals(GoodCategoryType.DOCUMENT)) {
            map = Collections.singletonMap(CourierServiceType.LONGMEN_STANDARD, new BigDecimal("45"));
        } else {
            map = calculateRegular(total, weightInLb);
        }

        return map;
    }

    private Map<CourierServiceType, BigDecimal> calculateCosmetics(BigDecimal total, BigDecimal weightInLb) {
        // Weight Cost
        total = total.add(weightInLb.multiply(new BigDecimal("6")));

        // Box Surcharge
        BigDecimal boxCount = weightInLb.divide(new BigDecimal("6"), 0, RoundingMode.CEILING);
        total = total.add(boxCount);

        // Box Weight Surcharge
        total = total.add(boxCount.multiply(new BigDecimal("6")));

        return Collections.singletonMap(CourierServiceType.LONGMEN_STANDARD, total);
    }

    private Map<CourierServiceType, BigDecimal> calculateRegular(BigDecimal total, BigDecimal weightInLb) {
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
    public ShipmentTrackingModel getTracking(CourierType type, String shipmentId) throws DependentServiceException, ResourceNotFoundException {
        ShipmentEntity shipment = shipmentStorage.get(Integer.valueOf(shipmentId));
        DateTime pickUpDate = shipment.getOrder().getOrderDate();
        DateTime trackDate = OrderStatusHistoryEntity.getMostRecentOrderStatusHistoryEntity(shipment.getOrder().getOrderStatuses()).getStatusDate();
        String trackingLocation = "N/A";
        String trackingStatus = OrderStatusHistoryEntity.getMostRecentOrderStatusHistoryEntity(shipment.getOrder().getOrderStatuses()).getStatusDescription();
        return new ShipmentTrackingModel(pickUpDate, trackDate, trackingLocation, trackingLocation, trackingStatus, "");
    }
}
