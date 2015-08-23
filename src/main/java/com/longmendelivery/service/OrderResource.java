package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.OrderStorage;
import com.longmendelivery.persistence.ShipmentStorage;
import com.longmendelivery.persistence.UserStorage;
import com.longmendelivery.persistence.engine.DatabaseOrderStorage;
import com.longmendelivery.persistence.engine.DatabaseShipmentStorage;
import com.longmendelivery.persistence.engine.DatabaseUserStorage;
import com.longmendelivery.persistence.entity.*;
import com.longmendelivery.persistence.exception.ResourceNotFoundException;
import com.longmendelivery.service.model.courier.CourierType;
import com.longmendelivery.service.model.order.ShipOrderModel;
import com.longmendelivery.service.model.order.ShipmentModel;
import com.longmendelivery.service.model.request.OrderCreationRequestModel;
import com.longmendelivery.service.model.request.OrderStatusRequestModel;
import com.longmendelivery.service.model.response.OrderStatusResponseModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import com.longmendelivery.service.security.NotAuthorizedException;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Path("/order")
@Produces("application/json")
public class OrderResource {
    private OrderStorage orderStorage = DatabaseOrderStorage.getInstance();
    private UserStorage userStorage = DatabaseUserStorage.getInstance();
    private RSIShipmentClient shipmentClient;
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    private ShipmentStorage shipmentStorage = DatabaseShipmentStorage.getInstance();

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
    public Response createOrder(OrderCreationRequestModel orderCreationRequestModel, @QueryParam("token") String token) {
        Integer userId = orderCreationRequestModel.getUserId();
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, userId);
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            AppUserEntity user = userStorage.get(userId);
            ShipOrderEntity shipOrderEntity = buildOrderEntity(orderCreationRequestModel, user);
            Set<ShipmentEntity> shipmentEntities = buildShipmentEntities(orderCreationRequestModel, shipOrderEntity);
            shipOrderEntity.setShipments(shipmentEntities);
            orderStorage.create(shipOrderEntity);
            ShipOrderModel shipOrderModel = mapper.map(shipOrderEntity, ShipOrderModel.class);
            return Response.status(Response.Status.OK).entity(shipOrderModel).build();
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateBadRequestMessage("Order client user not found");
        }
    }

    private ShipOrderEntity buildOrderEntity(OrderCreationRequestModel orderCreationRequestModel, AppUserEntity user) {
        BigDecimal estimatedCost = BigDecimal.ONE;
        //XXX wire up with estimation
        BigDecimal finalCost = null;
        String handler = null;
        AddressEntity from = mapper.map(orderCreationRequestModel.getFromAddress(), AddressEntity.class);
        AddressEntity to = mapper.map(orderCreationRequestModel.getToAddress(), AddressEntity.class);

        return new ShipOrderEntity(null, user, orderCreationRequestModel.getOrderDate(),
                null, estimatedCost, finalCost, from, to, orderCreationRequestModel.getCourierServiceType(),
                handler, null, orderCreationRequestModel.getGoodCategoryType(),
                orderCreationRequestModel.getDeclareValue(), orderCreationRequestModel.getInsuranceValue());
    }

    private Set<ShipmentEntity> buildShipmentEntities(OrderCreationRequestModel orderCreationRequestModel, ShipOrderEntity shipOrderEntity) {
        Set<ShipmentEntity> shipmentEntities = new HashSet<>();
        for (ShipmentModel shipmentModel : orderCreationRequestModel.getShipments()) {
            String trackingNumber = null;

            ShipmentEntity shipmentEntity = new ShipmentEntity(null, shipOrderEntity,
                    shipmentModel.getHeight(),
                    shipmentModel.getWidth(),
                    shipmentModel.getLength(),
                    shipmentModel.getWeight(),
                    trackingNumber,
                    shipmentModel.getNickName()
            );

            shipmentEntities.add(shipmentEntity);
        }
        return shipmentEntities;
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
        } finally {


        }
    }

    @GET
    @Path("/{orderId}/status")
    public Response getOrderStatus(@PathParam("orderId") Integer orderId, @QueryParam("token") String token) throws DependentServiceException {


        try {
            ShipOrderEntity order = orderStorage.get(orderId);

            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());
            OrderStatusResponseModel orderStatusResponseModel = new OrderStatusResponseModel();
            OrderStatusHistoryEntity orderStatusHistoryEntity = order.getOrderStatus().get(0);
            orderStatusResponseModel.setStatus(orderStatusHistoryEntity.getStatus());
            orderStatusResponseModel.setStatusDescription(orderStatusHistoryEntity.getStatusDescription());

            Map<ShipmentModel, ShipmentTrackingResponseModel> shipmentTracking = new HashMap<>();
            for (ShipmentEntity shipmentEntity : order.getShipments()) {
                ShipmentModel shipmentModel = mapper.map(shipmentEntity, ShipmentModel.class);
                CourierType courierType = order.getCourierServiceType().getCourier();
                ShipmentTrackingResponseModel shipmentTrackingResponseModel = shipmentClient.getTracking(courierType, shipmentEntity.getTrackingNumber());
                shipmentTracking.put(shipmentModel, shipmentTrackingResponseModel);
            }
            return Response.status(Response.Status.OK).entity(orderStatusResponseModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } catch (ResourceNotFoundException e) {
            return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
        }
    }

    @POST
    @Path("/{orderId}/status")
    public Response updateOrderStatus(@PathParam("orderId") Integer orderId, OrderStatusRequestModel status, @QueryParam("backendUser") Integer backendUser, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE, backendUser);
        } catch (NotAuthorizedException e) {
            ResourceResponseUtil.generateForbiddenMessage(e);
        }

        try {
            ShipOrderEntity order = orderStorage.get(orderId);
            OrderStatusHistoryEntity orderStatusHistoryEntity = new OrderStatusHistoryEntity(null, status.getStatus(), order, status.getStatusDescription(), backendUser, DateTime.now());
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