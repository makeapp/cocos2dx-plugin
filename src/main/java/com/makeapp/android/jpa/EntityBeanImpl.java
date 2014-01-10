/****************************************************************************************
 * Copyright(c) Shanghai YiJun Network Info Technologies Inc. All right reserved.     *
 ****************************************************************************************/

package com.makeapp.android.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.makeapp.javase.lang.ArrayUtil;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.DataUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:2010-4-29 0:36:42 $
 *          $Id$
 */
public class EntityBeanImpl
    implements EntityBean
{
    private javax.persistence.EntityManager em;

    List empty = new ArrayList();

    public EntityManager getEntityManager()
    {
        if (em == null) {
            return EntityContext.getEntityManager();
        }
        return em;
    }

    public void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    public boolean getRollbackOnly()
    {
        return getEntityManager().getTransaction().getRollbackOnly();
    }

    public void setRollbackOnly()
    {
        getEntityManager().getTransaction().setRollbackOnly();
    }


    public Integer nativeExecute(String sql, Object... params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql);
        addParameters(query, params);
        try {
            return query.executeUpdate();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer nativeExecute(String sql, Map<String, Object> params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql);

        addParameters(query, params);

        try {
            return query.executeUpdate();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addParameters(Query query, Map<String, Object> params)
    {
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
    }

    public void persist(Object... objects)
    {
        EntityManager em = getEntityManager();

        if (objects == null) {
            return;
        }
        for (Object obj : objects) {
            if (obj instanceof Object[]) {
                Object[] objs = (Object[]) obj;
                for (Object o : objs) {
                    persist(o);
                }
            }
            else if (obj instanceof Collection) {
                Collection s = (Collection) obj;
                for (Object o : s) {
                    persist(o);
                }
            }
            else {
                em.persist(obj);
            }
        }
    }


    public void remove(Object obj)
    {
        EntityManager em = getEntityManager();

        if (obj == null) {
            return;
        }
        if (obj instanceof Object[]) {
            Object[] objs = (Object[]) obj;
            for (Object o : objs) {
                remove(o);
            }
        }
        else if (obj instanceof Collection) {
            Collection s = (Collection) obj;
            for (Object o : s) {
                em.remove(o);
            }
        }
        else {
            em.remove(obj);
        }
    }

    public void remove(Class clazz, Object id)
    {
        EntityManager em = getEntityManager();

        if (id instanceof Number || id instanceof String) {
            Object obj = em.getReference(clazz, id);
            if (obj != null) {
                em.remove(obj);
            }
        }
        else if (id instanceof int[]) {
            for (Object o : (int[]) id) {
                remove(clazz, o);
            }
        }
        else if (id instanceof Integer[]) {
            for (Object o : (Integer[]) id) {
                remove(clazz, o);
            }
        }
        else if (id instanceof long[]) {
            for (Object o : (long[]) id) {
                remove(clazz, o);
            }
        }
        else if (id instanceof Long[]) {
            for (Object o : (Long[]) id) {
                remove(clazz, o);
            }
        }
        else if (id instanceof Number[]) {
            Number[] ids = (Number[]) id;
            for (int i = 0; i < ids.length; i++) {
                remove(clazz, ids[i]);
            }
        }
        else if (id instanceof String[]) {
            String[] s = (String[]) id;
            for (int i = 0; i < s.length; i++) {
                remove(clazz, DataUtil.getInt(s[i]));
            }
        }
        else if (id instanceof Object[]) {
            for (Object o : (Object[]) id) {
                remove(clazz, o);
            }
        }
        else if (id instanceof Collection) {
            for (Object obj : (Collection) id) {
                remove(clazz, obj);
            }
        }
    }


    public <T> T querySingle(Class<T> clazz, String[] name, Object... value)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("from " + clazz.getName());
        addParameters(q, name, value);
        try {
            return (T) q.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T querySingle(Class<T> clazz, Map<String, Object> params)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("from " + clazz.getName());
        addParameters(q, params);
        try {
            return (T) q.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List nativeQuery(Class clazz, String sql, int first, int pageSize, Map<String, Object> params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql, clazz);
        addParameters(query, params);
        setFirstAndMaxResult(query, first, pageSize);

        try {
            return query.getResultList();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return empty;
    }

    public List nativeQuery(Class clazz, String sql, int first, int pageSize, Object... params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql, clazz);
        addParameters(query, params);
        setFirstAndMaxResult(query, first, pageSize);
        try {
            return query.getResultList();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return empty;
    }

    private void addParameters(Query query, Object... params)
    {
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
    }

    public List nativeQuery(String sql, int first, int pageSize, Map<String, Object> params)
    {
        return nativeQuery(null, sql, first, pageSize, params);
    }

    public Object nativeQuerySingle(String sql, Map<String, Object> params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql);
        addParameters(query, params);
        try {
            return query.getSingleResult();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object nativeQuerySingle(String sql, Object... params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql);
        addParameters(query, params);
        try {
            return query.getSingleResult();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public List nativeQuery(String sql, int first, int pageSize, Object... params)
    {
        EntityManager em = getEntityManager();

        Query query = em.createNativeQuery(sql);
        addParameters(query, params);
        setFirstAndMaxResult(query, first, pageSize);

        try {
            return query.getResultList();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return empty;
    }

    public Integer nativeQueryCount(String sql, Object... params)
    {
        return getCountResult(nativeQuerySingle(sql, params));
    }

    private int getCountResult(Object obj)
    {
        if (obj instanceof Object[]) {
            Object[] result = (Object[]) obj;
            if (ArrayUtil.isValid(result)) {
                return DataUtil.getInt(result[0]);
            }
        }
        return 0;
    }

    public Integer nativeQueryCount(String sql, Map<String, Object> params)
    {

        return getCountResult(nativeQuerySingle(sql, params));
    }

    public <T> int queryCount(Class<T> clazz, String[] name, Object... value)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery(getCountQuerySql(clazz));
        addParameters(q, name, value);
        try {
            return getCountResult(q.getSingleResult());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addParameters(Query q, String[] name, Object[] value)
    {
        if (name != null) {
            for (int i = 0; i < name.length; i++) {
                q.setParameter(name[i], value[i]);
            }
        }
    }

    public <T> int queryCount(Class<T> clazz, Map<String, Object> params)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery(getCountQuerySql(clazz));
        addParameters(q, params);
        try {
            return getCountResult(q.getSingleResult());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public <T> List<T> query(Class<T> clazz, String[] name, Object[] value, int first, int maxResult, String sort, boolean desc)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery(getQuerySql(clazz, sort, desc));
        addParameters(q, name, value);
        setFirstAndMaxResult(q, first, maxResult);
        try {
            return q.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return empty;
    }

    private <T> String getQuerySql(Class<T> clazz, String sort, boolean desc)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("from").append(" ").append(clazz.getName());
        if (StringUtil.isValid(sort)) {
            buffer.append("order by ").append(sort);
            if (desc) {
                buffer.append(" desc");
            }
        }
        return buffer.toString();
    }

    private <T> String getCountQuerySql(Class<T> clazz)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("count from").append(" ").append(clazz.getName());
        return buffer.toString();
    }

    private void setFirstAndMaxResult(Query q, int first, int maxResult)
    {
        if (first > 0) {
            q.setFirstResult(first);
        }
        if (maxResult != -1) {
            q.setMaxResults(maxResult);
        }
    }

    public <T> List<T> query(Class<T> clazz, Map<String, Object> params, int first, int maxResult, String sort, boolean desc)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery(getQuerySql(clazz, sort, desc));
        addParameters(q, params);
        setFirstAndMaxResult(q, first, maxResult);
        try {
            return q.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return empty;
    }


    public <T> T getReference(Class<T> clazz, Object obj)
    {
        return getEntityManager().getReference(clazz, obj);
    }

    public void flush()
    {
        getEntityManager().flush();
    }

    public void refresh(Object o)
    {
        getEntityManager().refresh(o);
    }

    public void refresh(Object o, Map<String, Object> stringObjectMap)
    {
        getEntityManager().refresh(o, stringObjectMap);
    }


    public void clear()
    {
        getEntityManager().clear();
    }

    public Object detach(Object o)
    {
        if (o == null) {
            return null;
        }
        getEntityManager().detach(o);

        return o;
    }


    public boolean contains(Object o)
    {
        return getEntityManager().contains(o);
    }

    public <T> T merge(T o)
    {
        return getEntityManager().merge(o);
    }

    @Deprecated
    public void mergePersist(Object o)
    {
        Object no = getEntityManager().merge(o);
        getEntityManager().persist(no);
    }


    public <T> T find(Class<T> tClass, Object o)
    {
        return getEntityManager().find(tClass, o);
    }

    public <T> Collection<T> find(Class<T> tClass, Object... o)
    {
        List result = new ArrayList();
        for (Object obj : o) {
            result.add(em.find(tClass, obj));
        }
        return result;
    }

}
