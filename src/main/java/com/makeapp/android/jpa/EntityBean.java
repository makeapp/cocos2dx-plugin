/****************************************************************************************
 * Copyright(c) Shanghai YiJun Network Info Technologies Inc. All right reserved.     *
 ****************************************************************************************/

package com.makeapp.android.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-18
 * Time: 下午1:47
 */
public interface EntityBean
{
    public Integer nativeExecute(String sql, Object... params);

    public Integer nativeExecute(String sql, Map<String, Object> params);

    public List nativeQuery(String sql, int first, int pageSize, Map<String, Object> params);

    public List nativeQuery(String sql, int first, int pageSize, Object... params);

    public List nativeQuery(Class clazz, String sql, int first, int pageSize, Map<String, Object> params);

    public List nativeQuery(Class clazz, String sql, int first, int pageSize, Object... params);

    public Object nativeQuerySingle(String sql, Map<String, Object> params);

    public Object nativeQuerySingle(String sql, Object... params);

    public Integer nativeQueryCount(String sql, Object... params);

    public Integer nativeQueryCount(String sql, Map<String, Object> params);

    void persist(Object... obj);

    void remove(Object objects);

    void remove(Class clazz, Object obj);

    <T> T querySingle(Class<T> clazz, String[] name, Object... value);

    <T> T querySingle(Class<T> clazz, Map<String, Object> params);

    <T> List<T> query(Class<T> clazz, String[] name, Object[] value, int first, int maxResult, String sort, boolean desc);

    <T> List<T> query(Class<T> clazz, Map<String, Object> params, int first, int maxResult, String sort, boolean desc);

    <T> int queryCount(Class<T> clazz, Map<String, Object> params);

    <T> int queryCount(Class<T> clazz, String[] name, Object... value);

    <T> T getReference(Class<T> clazz, Object obj);

    void flush();

    void refresh(Object o);

    void refresh(Object o, Map<String, Object> stringObjectMap);

    void clear();

    Object detach(Object o);

    boolean contains(Object o);

    <T> T merge(T o);

    <T> T find(Class<T> tClass, Object o);

    <T> Collection<T> find(Class<T> tClass, Object... o);
}
