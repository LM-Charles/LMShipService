package com.longmendelivery.service;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.persistence.util.HibernateUtil;
import com.longmendelivery.service.util.ResourceResponseUtil;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/debug")
public class DebugResource {
    @PUT
    @Path("testSMS")
    @Produces("text/plain")
    public Response getMessage(@QueryParam("to") String to, @QueryParam("body") String body) throws DependentServiceException {
        String output = "Testing SMS to : " + to + " with body: " + body;
        new TwilioSMSClient().sendSMS(to, body);
        return ResourceResponseUtil.generateOKMessage(output);
    }

    @GET
    @Path("testDB")
    @Produces("text/plain")
    public Response getMessage() throws DependentServiceException {
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