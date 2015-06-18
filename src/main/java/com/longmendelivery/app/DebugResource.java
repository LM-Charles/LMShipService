package com.longmendelivery.app;

import com.longmendelivery.lib.client.exceptions.DependentServiceException;
import com.longmendelivery.lib.client.sms.twilio.TwilioSMSClient;
import com.longmendelivery.persistence.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/debug")
public class DebugResource {
    @PUT
    @Path("testSMS/{to}/{body}")
    public Response getMessage(@PathParam("to") String to, @PathParam("body") String body) throws DependentServiceException {
        String output = "Testing SMS to : " + to + " with body: " + body;
        new TwilioSMSClient().sendSMS(to, body);
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("testDB")
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