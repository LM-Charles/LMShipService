package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.OrderStorage;
import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.*;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.*;
import com.longmendelivery.service.model.shipment.*;
import com.longmendelivery.service.security.NotAuthorizedException;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

@Path("/order")
@Produces("application/json")
@Component
@Transactional(readOnly = true)
public class OrderResource {
    @Autowired
    private OrderStorage orderStorage;
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private ShipmentStorage shipmentStorage;
    private RSIShipmentClient shipmentClient;
    @Autowired
    private CourierResource courierResource;

    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    public OrderResource() throws DependentServiceException {
        shipmentClient = new RSIShipmentClient();
    }

    @GET
    public Response listOrdersForUser(@QueryParam("userId") Integer userId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, userId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            AppUserEntity user = userStorage.get(userId);
            Set<ShipOrderEntity> ordersEntity = user.getOrders();
            Set<ShipOrderModel> ordersModel = mapSetEntityToModel(ordersEntity);
            return Response.status(Response.Status.OK).entity(ordersModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("user is not found");
        }
    }

    private Set<ShipOrderModel> mapSetEntityToModel(Set<ShipOrderEntity> ordersEntity) {
        Set<ShipOrderModel> ordersModel = new HashSet<>();
        for (ShipOrderEntity shipOrderEntity : ordersEntity) {
            ordersModel.add(mapper.map(shipOrderEntity, ShipOrderModel.class));
        }
        return ordersModel;
    }

    @POST
    @Transactional(readOnly = false)
    public Response createOrder(OrderCreationRequest orderCreationRequest, @QueryParam("token") String token) {
        Integer userId = orderCreationRequest.getClient();
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            AppUserEntity client = userStorage.get(userId);
            DateTime orderDate = orderCreationRequest.getOrderDate();
            if (orderDate == null) {
                orderDate = DateTime.now();
            }
            CourierServiceType courierServiceType = orderCreationRequest.getCourierServiceType();
            AddressEntity fromAddress = mapper.map(orderCreationRequest.getFromAddress(), AddressEntity.class);
            AddressEntity toAddress = mapper.map(orderCreationRequest.getToAddress(), AddressEntity.class);

            String handler = orderCreationRequest.getHandler();
            Set<OrderStatusHistoryEntity> orderStatus = new HashSet<>();
            List<ShipmentEntity> shipments = new ArrayList<>();
            BigDecimal calculatedEstimateTotal = null;
            try {
                RateResponseModel rateResponseModel = courierResource.calculateRate(orderCreationRequest);
                if (rateResponseModel == null) {
                    return ResourceResponseUtil.generateNotFoundMessage("No courier service are available for this order, please verify with get quote");
                }
                calculatedEstimateTotal = rateResponseModel.calculateTotalWithService(orderCreationRequest.getCourierServiceType());
            } catch (NoSuchElementException e) {
                return ResourceResponseUtil.generateNotFoundMessage("The specified courier service " + orderCreationRequest.getCourierServiceType() + " is not available for this order, please verify with get quote");
            }
            BigDecimal estimatedCost = calculatedEstimateTotal;
            BigDecimal finalCost = calculatedEstimateTotal;
            BigDecimal declaredValue = orderCreationRequest.getDeclareValue();
            BigDecimal insuranceValue = orderCreationRequest.getInsuranceValue();
            DateTime appointmentDate = orderCreationRequest.getAppointmentDate();
            AppointmentSlotType appointmentSlotType = orderCreationRequest.getAppointmentSlotType();
            String nickname = orderCreationRequest.getNickname();
            ShipOrderEntity shipOrderEntity = new ShipOrderEntity(null, client, orderDate, courierServiceType, shipments, estimatedCost, finalCost, fromAddress, toAddress, handler, orderStatus, declaredValue, insuranceValue, appointmentDate, appointmentSlotType, nickname);

            List<ShipmentEntity> shipmentEntities = new ArrayList<>();
            for (ShipmentModel shipmentModel : orderCreationRequest.getShipments()) {
                String trackingNumber = null;
                ShipmentEntity shipmentEntity = new ShipmentEntity(null, shipOrderEntity, shipmentModel.getHeight(), shipmentModel.getWidth(), shipmentModel.getLength(), shipmentModel.getWeight(), trackingNumber, shipmentModel.getGoodCategoryType(), shipmentModel.getShipmentPackageType(), shipmentModel.getDisplayLengthPreference(), shipmentModel.getDisplayWeightPreference());
                shipmentEntities.add(shipmentEntity);
            }
            shipOrderEntity.setShipments(shipmentEntities);

            OrderStatusHistoryEntity initialOrderStatus = new OrderStatusHistoryEntity(null, OrderStatusType.ORDER_PLACED, shipOrderEntity, "order placed by system", handler, DateTime.now());
            Set<OrderStatusHistoryEntity> initialOrderSet = new HashSet<>();
            initialOrderSet.add(initialOrderStatus);
            shipOrderEntity.setOrderStatuses(initialOrderSet);

            orderStorage.recursiveCreate(shipOrderEntity);

            ShipOrderModel shipOrderModel = mapper.map(shipOrderEntity, ShipOrderModel.class);
            return Response.status(Response.Status.OK).entity(shipOrderModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateBadRequestMessage("order client user not found");
        } catch (DependentServiceException e) {
            return ResourceResponseUtil.generateServiceMessage("order rating system is not temporarily not available for courier");
        }
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderDetails(@PathParam("orderId") Integer orderId, @QueryParam("token") String token) {
        try {
            ShipOrderEntity order = orderStorage.get(orderId);
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());
            ShipOrderModel shipOrderModel = mapper.map(order, ShipOrderModel.class);
            return Response.status(Response.Status.OK).entity(shipOrderModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
        }
    }

    @GET
    @Path("/{orderId}/status")
    public Response getOrderStatus(@PathParam("orderId") Integer orderId, @QueryParam("token") String token) throws DependentServiceException {
        try {
            ShipOrderEntity order = orderStorage.get(orderId);
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());

            ShipOrderWithStatusModel orderWithStatusModel = mapper.map(order, ShipOrderWithStatusModel.class);
            OrderStatusHistoryEntity orderStatusHistoryEntity = OrderStatusHistoryEntity.getMostRecentOrderStatusHistoryEntity(order.getOrderStatuses());

            // Get most recent history
            OrderStatusModel orderStatus = mapper.map(orderStatusHistoryEntity, OrderStatusModel.class);
            orderWithStatusModel.setOrderStatusModel(orderStatus);
            orderWithStatusModel.setShipments(new ArrayList<ShipmentWithTrackingModel>());

            CourierType courierType = order.getCourierServiceType().getCourier();
            for (ShipmentEntity shipmentEntity : order.getShipments()) {
                ShipmentWithTrackingModel shipmentWithTrackingModel = mapper.map(shipmentEntity, ShipmentWithTrackingModel.class);
                if (shipmentEntity.getTrackingNumber() != null) {
                    ShipmentTrackingModel shipmentTrackingModel = shipmentClient.getTracking(courierType, shipmentEntity.getTrackingNumber());
                    shipmentWithTrackingModel.setTracking(shipmentTrackingModel);
                }
                orderWithStatusModel.getShipments().add(shipmentWithTrackingModel);
            }
            return Response.status(Response.Status.OK).entity(orderWithStatusModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
        }
    }

    @POST
    @Path("/{orderId}/status")
    @Transactional(readOnly = false)
    public Response updateOrderStatus(@PathParam("orderId") Integer orderId, OrderStatusUpdateRequest status, @QueryParam("backendUserId") Integer backendUserId, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE, backendUserId);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            AppUserEntity backendUser = userStorage.get(backendUserId);
            ShipOrderEntity order = orderStorage.get(orderId);
            OrderStatusHistoryEntity orderStatusHistoryEntity = new OrderStatusHistoryEntity(null, status.getStatus(), order, status.getStatusDescription(), backendUser.getEmail(), DateTime.now());
            orderStorage.createHistory(orderStatusHistoryEntity);
            orderStorage.update(order);
            return ResourceResponseUtil.generateOKMessage("order updated");
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
        } finally {

        }
    }

    @POST
    @Path("/{orderId}/tracking/{shipmentId}")
    @Transactional(readOnly = false)
    public Response addTrackingNumber(@PathParam("orderId") Integer orderId, @PathParam("shipmentId") Integer shipmentId, ShipmentAddTrackingRequest request, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        try {
            ShipmentEntity shipmentEntity = shipmentStorage.get(shipmentId);
            shipmentEntity.setTrackingNumber(request.getTrackingNumber());
            shipmentStorage.update(shipmentEntity);
            ShipmentModel shipmentModel = mapper.map(shipmentEntity, ShipmentModel.class);
            return Response.status(Response.Status.OK).entity(shipmentModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("shipment " + shipmentId + " does not exist");
        }
    }
}