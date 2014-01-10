/****************************************************************************************
 * Copyright(c) Shanghai YiJun Network Info Technologies Inc. All right reserved.     *
 ****************************************************************************************/

package com.makeapp.android.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.makeapp.javase.lang.FieldUtil;
import com.makeapp.javase.util.DataUtil;


/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-18
 * Time: 下午1:09
 */
public class EntityUtil
{
    public static Integer nativeExecute(String sql)
    {
        return getEntityBean().nativeExecute(sql, new HashMap());
    }

    public static Integer nativeExecute(String sql, Object... params)
    {
        return getEntityBean().nativeExecute(sql, params);
    }

    public static Integer nativeExecute(String sql, Map<String, Object> params)
    {
        return getEntityBean().nativeExecute(sql, params);
    }

    public static Integer nativeQueryCount(String sql, Map<String, Object> params)
    {
        return getEntityBean().nativeQueryCount(sql, params);
    }

    public static List nativeQuery(String sql, int first, int pageSize, Map<String, Object> params)
    {
        return getEntityBean().nativeQuery(sql, first, pageSize, params);
    }

    public static List nativeQuery(String sql, Map<String, Object> params)
    {
        return getEntityBean().nativeQuery(sql, 0, -1, params);
    }

    public static List nativeQuery(String sql)
    {
        return getEntityBean().nativeQuery(sql, 0, -1);
    }

    public static Object nativeQuerySingle(String sql, int first, int pageSize, Map<String, Object> params)
    {
        return getEntityBean().nativeQuerySingle(sql, first, pageSize, params);
    }

    public static Object nativeQuerySingle(String sql, Map<String, Object> params)
    {
        return getEntityBean().nativeQuerySingle(sql, params);
    }

    public static Object nativeQuerySingle(String sql)
    {
        return getEntityBean().nativeQuerySingle(sql, (Map) null);
    }

    public static Object nativeQuerySingle(String sql, int first, int pageSize, Object... params)
    {
        return getEntityBean().nativeQuerySingle(sql, first, pageSize, params);
    }

    public static Object nativeQuerySingle(String sql, Object... params)
    {
        return getEntityBean().nativeQuerySingle(sql, params);
    }

    public static List nativeQuery(String sql, int first, int pageSize, Object... params)
    {
        return getEntityBean().nativeQuery(sql, first, pageSize, params);
    }

    public static List nativeQuery(String sql, Object... params)
    {
        return nativeQuery(sql, 0, -1, params);
    }

    public static <T> List<T> nativeQuery(Class<T> clazz, String sql, int first, int pageSize, Object... params)
    {
        return getEntityBean().nativeQuery(clazz, sql, first, pageSize, params);
    }

    public static <T> List<T> nativeQuery(Class<T> clazz, String sql, Object... params)
    {
        return nativeQuery(clazz, sql, 0, -1, params);
    }

    public static Integer nativeQueryCount(String sql, Object... params)
    {
        return getEntityBean().nativeQueryCount(sql, params);
    }


    public static void persist(Object... obj)
    {
        getEntityBean().persist(obj);
    }

    public static void remove(Object... objects)
    {
        getEntityBean().remove(objects);
    }

    public static void remove(Class clazz, Object... id)
    {
        getEntityBean().remove(clazz, id);
    }


    public static <T> T querySingle(Class<T> clazz, String name, Object value)
    {
        return getEntityBean().querySingle(clazz, new String[]{name}, value);
    }

    public static <T> T querySingle(Class<T> clazz, String[] name, Object... value)
    {
        return getEntityBean().querySingle(clazz, name, value);
    }

    public static <T> T querySingle(Class<T> clazz, Map<String, Object> params)
    {
        return getEntityBean().querySingle(clazz, params);
    }


    public static <T> List<T> query(Class<T> clazz, String name, Object value)
    {
        return getEntityBean().query(clazz, new String[]{name}, new Object[]{value}, 0, -1, null, false);
    }

    public static <T> List<T> query(Class<T> clazz, String name, Object value, String sortBy, boolean desc)
    {
        return getEntityBean().query(clazz, new String[]{name}, new Object[]{value}, 0, -1, sortBy, desc);
    }


    public static <T> List<T> query(Class<T> clazz, String[] name, Object[] values, String sortBy, boolean desc)
    {
        return getEntityBean().query(clazz, name, values, 0, -1, sortBy, desc);
    }

    public static <T> List<T> query(Class<T> clazz, Map<String, Object> params, String sortBy, boolean desc)
    {
        return getEntityBean().query(clazz, params, 0, -1, sortBy, desc);
    }

    public static <T> List<T> query(Class<T> clazz, String[] name, Object... value)
    {
        return getEntityBean().query(clazz, name, value, 0, -1, null, false);
    }

    public static <T> List<T> query(Class<T> clazz, int first, int maxResult, String sort, boolean desc)
    {
        return getEntityBean().query(clazz, null, null, first, maxResult, sort, desc);
    }

    public static <T> List<T> query(Class<T> clazz, String name, Object value, int first, int maxResult, String sort, boolean desc)
    {
        return getEntityBean().query(clazz, new String[]{name}, new Object[]{value}, first, maxResult, sort, desc);
    }

    public static <T> List<T> query(Class<T> clazz, String[] name, Object[] value, int first, int maxResult, String sort, boolean desc)
    {
        return getEntityBean().query(clazz, name, value, first, maxResult, sort, desc);
    }

    public static <T> List<T> query(Class<T> clazz, Map<String, Object> params, int first, int maxResult, String sort, boolean desc)
    {
        return getEntityBean().query(clazz, params, first, maxResult, sort, desc);
    }

    public static <T> int queryCount(Class<T> clazz)
    {
        return getEntityBean().queryCount(clazz, null);
    }

    public static <T> int queryCount(Class<T> clazz, String name, Object value)
    {
        return getEntityBean().queryCount(clazz, new String[]{name}, new Object[]{value});
    }

    public static <T> int queryCount(Class<T> clazz, String[] name, Object... value)
    {
        return getEntityBean().queryCount(clazz, name, value);
    }

    public static <T> int queryCount(Class<T> clazz, Map<String, Object> params)
    {
        return getEntityBean().queryCount(clazz, params);
    }

    public static <T> List<T> queryAll(Class<T> clazz)
    {
        return getEntityBean().query(clazz, null, 0, -1, null, false);
    }

    public static <T> List<T> queryAll(Class<T> clazz, String sort, boolean desc)
    {
        return getEntityBean().query(clazz, null, 0, -1, sort, desc);
    }

    public static <T> T getReference(Class<T> clazz, Object obj)
    {
        return getEntityBean().getReference(clazz, obj);
    }

    public static void flush()
    {
        getEntityBean().flush();
    }

    public static void refresh(Object o)
    {
        getEntityBean().refresh(o);
    }

    public static void refresh(Object o, Map<String, Object> stringObjectMap)
    {
        getEntityBean().refresh(o, stringObjectMap);
    }


    public static void clear()
    {
        getEntityBean().clear();
    }

    public static Object detach(Object o)
    {
        return getEntityBean().detach(o);
    }

    public static boolean contains(Object o)
    {
        return getEntityBean().contains(o);
    }


    public static void persist(Object o)
    {
        getEntityBean().persist(o);
    }

    public static <T> T merge(T o)
    {
        return getEntityBean().merge(o);
    }

    public static <T> T find(Class<T> tClass, Object o)
    {
        return getEntityBean().find(tClass, o);
    }

    public static <T> Collection<T> find(Class<T> tClass, Object... o)
    {
        return getEntityBean().find(tClass, o);
    }


    public static Integer getEntityId(Object obj)
    {
        if (obj != null) {
            return DataUtil.getInt(FieldUtil.getFieldValue(obj, "id"));
        }
        return 0;
    }


    static List<String> ignores = new ArrayList<String>();

    {
        ignores.add("getCriteriaBuilder");
        ignores.add("createQuery");
        ignores.add("toString");
    }

    public static boolean execute(Runnable runtime)
    {
        return execute("default", runtime);
    }

    public static boolean execute(String unitName, Runnable runtime)
    {
        EntityManagerFactory emf = EntityContext.getEntityManagerFactory(unitName);

        if (emf == null) {
            return false;
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean ok = false;
        try {
            EntityContext.setEntityManager(em);
            et.begin();
            runtime.run();
            ok = true;
        }
        finally {
            EntityContext.setEntityManager(null);
            if (ok) {
                if (et.getRollbackOnly()) {
                    et.rollback();
                }
                else {
                    et.commit();
                }
            }
            else {
                et.rollback();
            }
            em.close();
        }

        return ok;
    }

    public static EntityBean getEntityBean()
    {
        return getEntityBean("default");
    }

    public static EntityBean getEntityBean(final String unitName)
    {
        EntityBeanImpl entityBean = null;

        EntityManager em = EntityContext.getEntityManager();

        Class[] clazz = {EntityBean.class};
        if (em == null) {
            return (EntityBean) Proxy.newProxyInstance(EntityBean.class.getClassLoader(), clazz, new InvocationHandler()
            {
                EntityBeanImpl entityBean = new EntityBeanImpl();

                public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable
                {
                    String methodName = method.getName();
                    Method invoker = entityBean.getClass().getDeclaredMethod(methodName, method.getParameterTypes());
                    if (ignores.contains(methodName)) {
                        return invoker.invoke(entityBean, args);
                    }
                    else {
                        EntityManager em = EntityContext.getEntityManagerFactory(unitName).createEntityManager();
                        entityBean.setEntityManager(em);
                        try {
                            return invoker.invoke(entityBean, args);
                        }
                        finally {
                            em.close();
                        }
                    }
                }
            });
        }
        else {
            entityBean = new EntityBeanImpl();
            entityBean.setEntityManager(em);
        }

        return entityBean;
    }
}
