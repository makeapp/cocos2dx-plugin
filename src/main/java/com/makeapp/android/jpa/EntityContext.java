/****************************************************************************************
 * Copyright(c) Shanghai YiJun Network Info Technologies Inc. All right reserved.     *
 ****************************************************************************************/

package com.makeapp.android.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-6
 * Time: 下午5:07
 */
public class EntityContext
{
    static Map<String, EntityManagerFactory> units = new HashMap();

    public static void setEntityManagerFactory(String unitName, EntityManagerFactory entityManagerFactory)
    {
        units.put(unitName, entityManagerFactory);
    }

    public static EntityManagerFactory removeEntityManagerFactory(String unitName)
    {
        return units.remove(unitName);
    }

    public static EntityManagerFactory getEntityManagerFactory()
    {
        return getEntityManagerFactory("default");
    }

    public static EntityManagerFactory getEntityManagerFactory(String unitName)
    {
        EntityManagerFactory emf = units.get(unitName);
        if (emf == null) {
            synchronized (units) {
                emf = units.get(unitName);
            }
        }
        return emf;
    }

    static ThreadLocal tl = new ThreadLocal();

    public static void setEntityManager(EntityManager bean)
    {
        tl.set(bean);
    }

    public static EntityManager getEntityManager()
    {
        return (EntityManager) tl.get();
    }
}
