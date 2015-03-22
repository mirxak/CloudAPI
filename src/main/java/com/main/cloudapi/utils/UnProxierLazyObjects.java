package com.main.cloudapi.utils;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mirxak on 21.03.15.
 */
public class UnProxierLazyObjects {

    public static <T> T unproxy(T entity){
        if (entity == null){
            return null;
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy){
            entity = (T)((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }

        return entity;
    }

    private static <T> boolean isHasProxy(Collection<T> list){
        boolean hasProxy = false;
        for (T entity : list){
            if (entity instanceof HibernateProxy){
                hasProxy = true;
                break;
            }
        }
        return hasProxy;
    }

    public static <T> Collection<T> unproxy(Collection<T> entities){
        if (isHasProxy(entities)){
            List<T> unproxied = new ArrayList<>();
            for (T entity : entities){
                if (entity instanceof HibernateProxy){
                    unproxied.add(UnProxierLazyObjects.unproxy(entity));
                }
                else{
                    unproxied.add(entity);
                }
            }

            entities.clear();
            entities.addAll(unproxied);
        }

        return entities;
    }
}