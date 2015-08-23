package com.longmendelivery.lib.conversion;

import com.longmendelivery.persistence.DAOEntity;
import org.dozer.CustomConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by  rabiddesireon 21/06/15.
 */
//TODO Make more generic to collections
public class EntitySetToIdSetCustomConverter implements CustomConverter {
    @Override
    public Object convert(Object dests, Object srcs, Class<?> destClass, Class<?> srcClass) {
        HashSet<Integer> newDests = new HashSet<>();
        for (DAOEntity src : (Set<DAOEntity>) srcs) {
            newDests.add(src.getId());
        }
        dests = newDests;
        return dests;
    }
}
