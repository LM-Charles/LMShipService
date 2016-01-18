package com.longmendelivery.service.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapperContextResolver() {
        System.out.println("MapperContextListener registering JodaModule...");
        mapper.registerModule(new JodaModule());
        System.out.println("MapperContextListener registered JodaModule.");
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}