package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.RSIShipmentClient;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.entity.OrderEntity;
import com.longmendelivery.persistence.entity.OrderStatusHistoryEntity;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import com.longmendelivery.persistence.util.HibernateUtil;
import com.longmendelivery.service.model.CourierType;
import com.longmendelivery.service.model.OrderModel;
import com.longmendelivery.service.model.ShipmentModel;
import com.longmendelivery.service.model.request.OrderCreationRequestModel;
import com.longmendelivery.service.model.request.OrderStatusRequestModel;
import com.longmendelivery.service.model.response.OrderStatusResponseModel;
import com.longmendelivery.service.model.response.ShipmentTrackingResponseModel;
import com.longmendelivery.service.security.NotAuthorizedException;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    private RSIShipmentClient shipmentClient;

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

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, userId);
            Set<OrderEntity> ordersEntity = user.getOrders();
            Set<OrderModel> ordersModel = new HashSet<>();
            for (OrderEntity orderEntity : ordersEntity) {
                ordersModel.add(DozerBeanMapperSingletonWrapper.getInstance().map(orderEntity, OrderModel.class));
            }

            return Response.status(Response.Status.OK).entity(ordersModel).build();
        } finally {
            tx.rollback();
            writeSession.close();
        }
    }

    @POST
    public Response createOrder(OrderCreationRequestModel orderCreationRequestModel, @QueryParam("token") String token) {
        try {
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_WRITE, orderCreationRequestModel.getUserId());
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        }

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            AppUserEntity user = (AppUserEntity) writeSession.get(AppUserEntity.class, orderCreationRequestModel.getUserId());

            BigDecimal estimatedCost = BigDecimal.ONE;
            BigDecimal finalCost = null;
            String handler = null;
            OrderEntity orderEntity = new OrderEntity(null,
                    user,
                    orderCreationRequestModel.getOrderDate(),
                    null,
                    estimatedCost, finalCost,
                    orderCreationRequestModel.getFromAddress().getAddress(),
                    orderCreationRequestModel.getFromAddress().getCity(),
                    orderCreationRequestModel.getFromAddress().getProvince(),
                    orderCreationRequestModel.getFromAddress().getCountry(),
                    orderCreationRequestModel.getFromAddress().getPostal(),
                    orderCreationRequestModel.getToAddress().getAddress(),
                    orderCreationRequestModel.getToAddress().getCity(),
                    orderCreationRequestModel.getToAddress().getProvince(),
                    orderCreationRequestModel.getToAddress().getCountry(),
                    orderCreationRequestModel.getToAddress().getPostal(),
                    orderCreationRequestModel.getCourierServiceType(), handler,
                    null, orderCreationRequestModel.getGoodCategory(), orderCreationRequestModel.getDeclareValue(), orderCreationRequestModel.getInsuranceValue());

            writeSession.save(orderEntity);

            Set<ShipmentEntity> shipmentEntities = new HashSet<>();
            for (ShipmentModel shipmentModel : orderCreationRequestModel.getShipments()) {
                String trackingNumber = null;

                ShipmentEntity shipmentEntity = new ShipmentEntity(null, orderEntity,
                        shipmentModel.getHeight(),
                        shipmentModel.getWidth(),
                        shipmentModel.getLength(),
                        shipmentModel.getWeight(),
                        trackingNumber,
                        shipmentModel.getNickName()
                );

                writeSession.save(shipmentEntity);
                shipmentEntities.add(shipmentEntity);
            }

            orderEntity.setShipments(shipmentEntities);
            writeSession.save(orderEntity);

            OrderModel orderModel = DozerBeanMapperSingletonWrapper.getInstance().map(orderEntity, OrderModel.class);
            tx.commit();
            return Response.status(Response.Status.OK).entity(orderModel).build();
        } finally {
            writeSession.close();
        }
    }


    @GET
    @Path("/{orderId}")
    public Response getOrderDetails(@PathParam("orderId") Integer orderId, @QueryParam("token") String token) {
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            OrderEntity order = (OrderEntity) writeSession.get(OrderEntity.class, orderId);
            if (order == null) {
                return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
            }
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());
            OrderModel orderModel = DozerBeanMapperSingletonWrapper.getInstance().map(order, OrderModel.class);
            return Response.status(Response.Status.OK).entity(orderModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } finally {
            tx.rollback();
            writeSession.close();
        }
    }

    @GET
    @Path("/{orderId}/status")
    public Response getOrderStatus(@PathParam("orderId") Integer orderId, @QueryParam("token") String token) throws DependentServiceException {
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            OrderEntity order = (OrderEntity) writeSession.get(OrderEntity.class, orderId);
            if (order == null) {
                return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
            }
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());


            OrderStatusResponseModel orderStatusResponseModel = new OrderStatusResponseModel();
            OrderStatusHistoryEntity orderStatusHistoryEntity = order.getOrderStatus().get(0);
            orderStatusResponseModel.setStatus(orderStatusHistoryEntity.getStatus());
            orderStatusResponseModel.setStatusDescription(orderStatusHistoryEntity.getStatusDescription());

            Map<ShipmentModel, ShipmentTrackingResponseModel> shipmentTracking = new HashMap<>();
            for (ShipmentEntity shipmentEntity : order.getShipments()) {
                ShipmentModel shipmentModel = DozerBeanMapperSingletonWrapper.getInstance().map(shipmentEntity, ShipmentModel.class);
                CourierType courierType = order.getCourierServiceType().getCourier();
                ShipmentTrackingResponseModel shipmentTrackingResponseModel = shipmentClient.getTracking(courierType, shipmentEntity.getTrackingNumber());
                shipmentTracking.put(shipmentModel, shipmentTrackingResponseModel);
            }

            return Response.status(Response.Status.OK).entity(orderStatusResponseModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } finally {
            tx.rollback();
            writeSession.close();
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

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            OrderEntity order = (OrderEntity) writeSession.get(OrderEntity.class, orderId);
            if (order == null) {
                return ResourceResponseUtil.generateNotFoundMessage("order " + orderId + " does not exist");
            }
            OrderStatusHistoryEntity orderStatusHistoryEntity = new OrderStatusHistoryEntity(order, status.getStatus(), status.getStatusDescription(), backendUser);
            writeSession.save(orderStatusHistoryEntity);
            tx.commit();

            return ResourceResponseUtil.generateOKMessage("order updated");
        } finally {
            writeSession.close();
        }
    }

    @POST
    @Path("/{orderId}/tracking/{shipmentId}")
    public Response addTrackingNumber(@PathParam("orderId") Integer orderId, @PathParam("shipmentId") Integer shipmentId, @FormParam(("trackingNumber")) String trackingNumber, @FormParam("trackingDocument") String trackingDocumentType, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            ShipmentEntity shipmentEntity = (ShipmentEntity) writeSession.get(ShipmentEntity.class, shipmentId);
            String currentTrackingNumber = shipmentEntity.getTrackingNumber();

            if (currentTrackingNumber != null) {
                System.out.println("WARNING: Already set tracking number or document type");
            }

            shipmentEntity.setTrackingNumber(trackingNumber);

            writeSession.save(shipmentEntity);

            ShipmentModel shipmentModel = new ShipmentModel();
            DozerBeanMapperSingletonWrapper.getInstance().map(shipmentEntity, shipmentModel);
            return Response.status(Response.Status.OK).entity(shipmentModel).build();
        } finally {
            tx.rollback();
            writeSession.close();
        }
    }
}