package com.longmendelivery.app;

import com.longmendelivery.app.behavior.OrderCalculatorProvider;
import com.longmendelivery.app.model.OrderCreationRequestModel;
import com.longmendelivery.app.model.OrderModel;
import com.longmendelivery.app.model.ShipmentModel;
import com.longmendelivery.app.util.ResourceResponseUtil;
import com.longmendelivery.lib.security.NotAuthorizedException;
import com.longmendelivery.lib.security.SecurityPower;
import com.longmendelivery.lib.security.TokenSecurity;
import com.longmendelivery.persistence.HibernateUtil;
import com.longmendelivery.persistence.entity.AppUserEntity;
import com.longmendelivery.persistence.entity.CourierServiceEntity;
import com.longmendelivery.persistence.entity.OrderEntity;
import com.longmendelivery.persistence.entity.ShipmentEntity;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Path("/order")
public class OrderResource {
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
            DozerBeanMapperSingletonWrapper.getInstance().map(ordersEntity, ordersModel);
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
            CourierServiceEntity courierService = (CourierServiceEntity) writeSession.get(CourierServiceEntity.class, orderCreationRequestModel.getCourierServiceId());

            BigDecimal estimatedCost = OrderCalculatorProvider.provide(orderCreationRequestModel.getCourierServiceId()).estimate(orderCreationRequestModel);
            BigDecimal finalCost = null;
            String handler = null;
            OrderEntity orderEntity = new OrderEntity(
                    user,
                    orderCreationRequestModel.getOrderDate(),
                    null,
                    estimatedCost, finalCost,
                    orderCreationRequestModel.getFromAddress(),
                    orderCreationRequestModel.getFromCity(),
                    orderCreationRequestModel.getFromProvince(),
                    orderCreationRequestModel.getFromCode(),
                    orderCreationRequestModel.getFromCountry(),
                    orderCreationRequestModel.getToAddress(),
                    orderCreationRequestModel.getToCity(),
                    orderCreationRequestModel.getToProvince(),
                    orderCreationRequestModel.getToCountry(),
                    orderCreationRequestModel.getToCode(),
                    courierService, handler
            );

            orderEntity = (OrderEntity) writeSession.save(orderEntity);

            Set<ShipmentEntity> shipmentEntities = new HashSet<>();
            for (ShipmentModel shipmentModel : orderCreationRequestModel.getShipments()) {


                String trackingNumber = null;
                String trackingDocumentType = null;

                ShipmentEntity shipmentEntity = new ShipmentEntity(orderEntity,
                        shipmentModel.getHeight(),
                        shipmentModel.getWidth(),
                        shipmentModel.getLength(),
                        shipmentModel.getWeight(),
                        trackingNumber,
                        trackingDocumentType
                );

                writeSession.save(shipmentEntity);
                shipmentEntities.add(shipmentEntity);
            }

            orderEntity.setShipments(shipmentEntities);
            orderEntity = (OrderEntity) writeSession.save(orderEntity);

            OrderModel orderModel = new OrderModel();
            DozerBeanMapperSingletonWrapper.getInstance().map(orderEntity, orderModel);

            return Response.status(Response.Status.OK).entity(orderModel).build();
        } finally {
            tx.rollback();
            writeSession.close();
        }
    }


    @GET
    @Path("/{orderId}")
    public Response getOrderDetails(@PathParam("orderId") String orderId, @QueryParam("token") String token) {
        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            OrderEntity order = (OrderEntity) writeSession.get(OrderEntity.class, orderId);
            TokenSecurity.getInstance().authorize(token, SecurityPower.PRIVATE_READ, order.getClient().getId());
            OrderModel orderModel = new OrderModel();
            DozerBeanMapperSingletonWrapper.getInstance().map(order, orderModel);
            return Response.status(Response.Status.OK).entity(orderModel).build();
        } catch (NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e);
        } finally {
            tx.rollback();
            writeSession.close();
        }
    }

    @POST
    @Path("/{orderId}/status")
    public Response updateOrderStatus(@PathParam("orderId") String orderId, String status, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        return Response.status(200).build();

    }

    @POST
    @Path("/{orderId}/tracking/{shipmentId}")
    public Response addTrackingNumber(@PathParam("orderId") String orderId, @PathParam("shipmentId") String shipmentId, @FormParam(("trackingNumber")) String trackingNumber, @FormParam("trackingDocument") String trackingDocumentType, @QueryParam("token") String token) {
        TokenSecurity.getInstance().authorize(token, SecurityPower.BACKEND_WRITE);

        Session writeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = writeSession.beginTransaction();
        try {
            ShipmentEntity shipmentEntity = (ShipmentEntity) writeSession.get(ShipmentEntity.class, shipmentId);
            String currentTrackingNumber = shipmentEntity.getTrackingNumber();
            String currentTrackingDocumentType = shipmentEntity.getTrackingDocumentType();

            if (currentTrackingNumber != null || currentTrackingDocumentType != null) {
                //WARN
                System.out.println("WARNING: Already set tracking number or document type");
            }

            shipmentEntity.setTrackingDocumentType(trackingDocumentType);
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