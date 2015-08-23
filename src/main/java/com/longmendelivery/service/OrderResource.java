package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.OrderStorage;
import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.entity.*;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.order.*;
import com.longmendelivery.service.model.shipment.CourierServiceType;
import com.longmendelivery.service.model.shipment.CourierType;
import com.longmendelivery.service.model.shipment.ShipmentTrackingResponse;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            CourierServiceType courierServiceType = orderCreationRequest.getCourierServiceType();
            AddressEntity fromAddress = mapper.map(orderCreationRequest.getFromAddress(), AddressEntity.class);
            AddressEntity toAddress = mapper.map(orderCreationRequest.getToAddress(), AddressEntity.class);

            String handler = orderCreationRequest.getHandler();
            GoodCategoryType goodCategoryType = orderCreationRequest.getGoodCategoryType();
            Set<OrderStatusHistoryEntity> orderStatus = new HashSet<>();
            Set<ShipmentEntity> shipments = new HashSet<>();
            BigDecimal estimatedCost = BigDecimal.ZERO;
            BigDecimal finalCost = BigDecimal.ZERO;
            BigDecimal declaredValue = orderCreationRequest.getDeclareValue();
            BigDecimal insuranceValue = orderCreationRequest.getInsuranceValue();
            ShipOrderEntity shipOrderEntity = new ShipOrderEntity(null, client, orderDate, courierServiceType, shipments, estimatedCost, finalCost, fromAddress, toAddress, handler, goodCategoryType, orderStatus, declaredValue, insuranceValue);

            Set<ShipmentEntity> shipmentEntities = new HashSet<>();
            for (ShipmentModel shipmentModel : orderCreationRequest.getShipments()) {
                String trackingNumber = null;
                ShipmentEntity shipmentEntity = new ShipmentEntity(null, shipOrderEntity, shipmentModel.getHeight(), shipmentModel.getWidth(), shipmentModel.getLength(), shipmentModel.getWeight(), trackingNumber, shipmentModel.getNickName());
                shipmentEntities.add(shipmentEntity);
            }
            shipOrderEntity.setShipments(shipmentEntities);

            OrderStatusHistoryEntity initialOrderStatus = new OrderStatusHistoryEntity(null, OrderStatusType.ORDER_PLACED, shipOrderEntity, "order placed by system", handler, DateTime.now());
            shipOrderEntity.getOrderStatus().add(initialOrderStatus);

            orderStorage.recursiveCreate(shipOrderEntity);

            ShipOrderModel shipOrderModel = mapper.map(shipOrderEntity, ShipOrderModel.class);
            return Response.status(Response.Status.OK).entity(shipOrderModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateBadRequestMessage("order client user not found");
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
            OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
            OrderStatusHistoryEntity orderStatusHistoryEntity = order.getOrderStatus().toArray(new OrderStatusHistoryEntity[order.getOrderStatus().size()])[0];
            orderStatusResponse.setStatus(orderStatusHistoryEntity.getStatus());
            orderStatusResponse.setStatusDescription(orderStatusHistoryEntity.getStatusDescription());

            Map<ShipmentModel, ShipmentTrackingResponse> shipmentTracking = new HashMap<>();
            for (ShipmentEntity shipmentEntity : order.getShipments()) {
                ShipmentModel shipmentModel = mapper.map(shipmentEntity, ShipmentModel.class);
                CourierType courierType = order.getCourierServiceType().getCourier();
                ShipmentTrackingResponse shipmentTrackingResponse = shipmentClient.getTracking(courierType, shipmentEntity.getTrackingNumber());
                shipmentTracking.put(shipmentModel, shipmentTrackingResponse);
            }
            return Response.status(Response.Status.OK).entity(orderStatusResponse).build();
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
    public Response addTrackingNumber(@PathParam("orderId") Integer orderId, @PathParam("shipmentId") Integer shipmentId, @FormParam(("trackingNumber")) String trackingNumber, @FormParam("trackingDocument") String trackingDocumentType, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);


        try {
            ShipmentEntity shipmentEntity = shipmentStorage.get(shipmentId);
            String currentTrackingNumber = shipmentEntity.getTrackingNumber();

            if (currentTrackingNumber != null) {
                System.out.println("pWARN] already set tracking number or document type " + orderId + " - " + shipmentId);
            }

            shipmentEntity.setTrackingNumber(trackingNumber);

            shipmentStorage.update(shipmentEntity);
            ShipmentModel shipmentModel = mapper.map(shipmentEntity, ShipmentModel.class);
            return Response.status(Response.Status.OK).entity(shipmentModel).build();
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } finally {
            return ResourceResponseUtil.generateNotFoundMessage("shipment " + shipmentId + " does not exist");
        }
    }
}