package com.longmendelivery.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.shipment.rocketshipit.WrappedScriptEngine;
import com.longmendelivery.lib.client.shipment.rocketshipit.model.UPSRateResponseEntry;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.persistence.util.HibernateUtil;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/debug")
@Produces("application/json")
public class DebugResource {
    @PUT
    @Path("testSMS")
    public Response testSMS(@QueryParam("to") String to, @QueryParam("body") String body) throws DependentServiceException {
        String output = "Testing SMS to : " + to + " with body: " + body;
        new TwilioSMSClient().sendSMS(to, body);
        return ResourceResponseUtil.generateOKMessage(output);
    }

    @POST
    @Path("testPHP")
    @Consumes("text/plain")
    public Response testPHP(String script) throws DependentServiceException, ScriptException {
        List<UPSRateResponseEntry> message = new WrappedScriptEngine().executeScript(script, new TypeReference<List<UPSRateResponseEntry>>() {
        });

        return Response.status(Response.Status.OK).entity(message).build();
    }

    @GET
    @Path("testDB")
    public Response testDB() throws DependentServiceException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Map<String, ClassMetadata> map = sessionFactory.getAllClassMetadata();
        StringBuilder builder = new StringBuilder();
        builder.append("Testing DB Hibernate entity mapping...");
        builder.append("\n");

        for (String entityName : map.keySet()) {
            SessionFactoryImpl sfImpl = (SessionFactoryImpl) sessionFactory;
            String tableName = ((AbstractEntityPersister) sfImpl.getEntityPersister(entityName)).getTableName();
            builder.append("Entity: ").append(entityName).append(" mapped to table: ").append(tableName);
            builder.append("\n");
        }
        return Response.status(200).entity(builder.toString()).build();
    }
}