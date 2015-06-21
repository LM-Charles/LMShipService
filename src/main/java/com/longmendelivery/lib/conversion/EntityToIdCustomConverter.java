package com.longmendelivery.lib.conversion;

import org.dozer.CustomConverter;

/**
 * Created by desmond on 21/06/15.
 */
public class EntityToIdCustomConverter implements CustomConverter {
    @Override
    public Object convert(Object dest, Object src, Class<?> destClass, Class<?> srcClass) {
        return ((DAOEntity) src).getId();
    }
}
