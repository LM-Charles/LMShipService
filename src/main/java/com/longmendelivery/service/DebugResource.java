package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.exceptions.DependentServiceRequestException;
import com.longmendelivery.lib.client.shipment.rocketshipit.engine.RSIScriptEngine;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.service.security.SecurityPower;
import com.longmendelivery.service.security.TokenSecurity;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Properties;

@Path("/debug")
@Produces("application/json")
@Component
public class DebugResource {
    @Autowired
    private SessionFactory sessionFactory;

    @PUT
    @Path("testSMS")
    public Response testSMS(@QueryParam("to") String to, @QueryParam("body") String body, @QueryParam("token") String token, @QueryParam("authId") Integer authId) throws DependentServiceException {
        try {
            TokenSecurity.getInstance().authorize(SecurityPower.ADMIN, authId, token, authId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e.getLocalizedMessage());
        }

        String output = "Testing SMS to : " + to + " with body: " + body;
        try {
            new TwilioSMSClient().sendSMS(to, body);
        } catch (DependentServiceRequestException e) {
            ResourceResponseUtil.generateBadRequestMessage("invalid phone number: " + to);
        }
        return ResourceResponseUtil.generateOKMessage(output);
    }

    @POST
    @Path("testPHP")
    @Consumes("text/plain")
    public Response testPHP(String script, @QueryParam("token") String token, @QueryParam("authId") Integer authId) throws DependentServiceException, ScriptException {
        try {
            TokenSecurity.getInstance().authorize(SecurityPower.ADMIN, authId, token, authId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e.getLocalizedMessage());
        }

        RSIScriptEngine engine = new RSIScriptEngine();
        try {
            String message = engine.executeScriptToString(script);
            return ResourceResponseUtil.generateOKMessage(message);
        } finally {
            engine.release();
        }
    }

    @GET
    @Path("testEnvironment")
    @Consumes("text/plain")
    public Response testEnvironment(@QueryParam("token") String token, @QueryParam("authId") Integer authId) throws DependentServiceException, ScriptException {
        try {
            TokenSecurity.getInstance().authorize(SecurityPower.ADMIN, authId, token, authId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e.getLocalizedMessage());
        }

        Map<String, String> envVar = System.getenv();
        return Response.status(Response.Status.OK).entity(envVar).build();
    }

    @GET
    @Path("testSystem")
    @Consumes("text/plain")
    public Response testSystem(@QueryParam("token") String token, @QueryParam("authId") Integer authId) throws DependentServiceException, ScriptException {
        try {
            TokenSecurity.getInstance().authorize(SecurityPower.ADMIN, authId, token, authId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e.getLocalizedMessage());
        }

        Properties properties = System.getProperties();
        return Response.status(Response.Status.OK).entity(properties.entrySet()).build();
    }

    @GET
    @Path("testDB")
    @Transactional(readOnly = true)
    @Produces("text/plain")
    public Response testDB(@QueryParam("token") String token, @QueryParam("authId") Integer authId) throws DependentServiceException {
        try {
            TokenSecurity.getInstance().authorize(SecurityPower.ADMIN, authId, token, authId);
        } catch (com.longmendelivery.service.security.NotAuthorizedException e) {
            return ResourceResponseUtil.generateForbiddenMessage(e.getLocalizedMessage());
        }

        Map<String, ClassMetadata> map = sessionFactory.getAllClassMetadata();
        StringBuilder builder = new StringBuilder();
        builder.append("Testing DB Hibernate entity mapping...");
        builder.append("\n");
        sessionFactory.getCurrentSession();
        for (String entityName : map.keySet()) {
            SessionFactoryImpl sfImpl = (SessionFactoryImpl) sessionFactory;
            String tableName = ((AbstractEntityPersister) sfImpl.getEntityPersister(entityName)).getTableName();
            builder.append("Entity: ").append(entityName).append(" mapped to table: ").append(tableName);
            builder.append("\n");
        }
        return Response.status(Response.Status.OK).entity(builder.toString()).build();
    }
}