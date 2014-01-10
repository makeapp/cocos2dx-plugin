package com.makeapp.android.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import android.database.Cursor;
import com.makeapp.javase.lang.ArrayUtil;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.log.Logger;
import com.makeapp.javase.util.DataUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-18
 * Time: ����10:48
 */
public class AndroidQuery
    implements Query
{
    private String sql = null;

    private Set<Parameter<?>> values = new HashSet<Parameter<?>>();

    private Class resultClass = null;

    private int maxResult = -1;

    private int startPosition = -1;

    AndroidEntityManager entityManager;

    Logger logger = Logger.getLogger("SQLiteJPA");

    public AndroidQuery(AndroidEntityManager entityManager, String qlString, Class resultClass)
    {
        this.sql = qlString;
        this.entityManager = entityManager;
        this.resultClass = resultClass;
    }

    // limit 0
    private String getSql()
    {
        if (sql.startsWith("from")) {
            StringBuffer buffer = new StringBuffer();
            String className = sql.substring(5);
            int pos = className.indexOf(" ");
            if (pos > 0) {
                className = className.substring(0, pos);
            }
            EntityClass entityClass = entityManager.getEntityClass(className);
            buffer.append("select * from ");
            resultClass = entityClass.getClazz();
            if (entityClass != null) {
                buffer.append(entityClass.getTableName());
            }
            buffer.append(getWhere());
            sql = buffer.toString();
        }
        else if (sql.startsWith("count from")) {
            StringBuffer buffer = new StringBuffer();
            String className = sql.substring("count from".length() + 1);
            int pos = className.indexOf(" ");
            if (pos > 0) {
                className = className.substring(0, pos);
            }
            EntityClass entityClass = entityManager.getEntityClass(className);
            buffer.append("select count(*) as ct from ");
            resultClass = entityClass.getClazz();
            if (entityClass != null) {
                buffer.append(entityClass.getTableName());
            }
            buffer.append(getWhere());
            sql = buffer.toString();
        }

        if (maxResult > 0 || startPosition > 0) {
            if (maxResult > 0 && startPosition > 0) {
                return sql + " limit " + startPosition + ", " + maxResult;
            }
            else {
                return sql + " limit " + maxResult;
            }
        }

        return sql;
    }

    private String getWhere()
    {
        StringBuffer buf = new StringBuffer();
        if (values.size() > 0) {
            buf.append(" where ");
            boolean added = false;
            for (Parameter<?> p : values) {
                if (added) {
                    buf.append(" and ");
                }
                added = true;
                buf.append(p.getName()).append("=");
                buf.append("?");
            }
        }
        return buf.toString();
    }

    private String[] getBindValues()
    {
        String[] bindValues = new String[values.size()];
        int i = 0;
        for (Parameter pv : values) {
            i++;
            for (Parameter<?> p : values) {
                if (i == p.getPosition()) {
                    bindValues[i - 1] = DataUtil.getString(((ParameterImpl) p).value, null);
                    break;
                }
            }
            bindValues[i - 1] = DataUtil.getString(((ParameterImpl) pv).value, null);
        }
        return bindValues;
    }

    public List getResultList()
    {
        String sql = getSql();

        logger.info(sql);

        Cursor cursor = entityManager.rawQuery(sql, getBindValues());
        List entities = new ArrayList();
        try {
            while (cursor.moveToNext()) {
                entities.add(getEntity(cursor));
            }
        }
        finally {
            cursor.close();
        }
        return entities;
    }

    public Object getSingleResult()
    {
        maxResult = 1;
        String[] values = getBindValues();
        StringBuffer buffer = new StringBuffer();
        buffer.append(getSql());
        for (String v : values) {
            buffer.append("|").append(v);
        }
        logger.info(buffer.toString());
        Cursor cursor = entityManager.rawQuery(getSql(), values);
        try {
            if (cursor.moveToNext()) {
                return getEntity(cursor);
            }
        }
        finally {
            cursor.close();
        }
        return null;
    }

    public Object getEntity(Cursor cursor)
    {
        boolean selectValue = false;//sql.indexOf("count") > 0  && sql.indexOf("group")==-1;
        if (resultClass == null || selectValue) {
            int columnCount = cursor.getColumnCount();
            Object[] results = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                results[i] = cursor.getString(i);
            }
//            if (selectValue && ArrayUtil.isValid(results)) {
//                return results[0];
//            }
            return results;
        }
        else {
            return entityManager.getEntity(resultClass, cursor);
        }
    }

    public int executeUpdate()
    {
        try {


            entityManager.executeUpdate(getSql(), getBindValues());
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public Query setMaxResults(int maxResult)
    {
        this.maxResult = maxResult;
        return this;
    }

    public int getMaxResults()
    {
        return maxResult;
    }

    public Query setFirstResult(int startPosition)
    {
        this.startPosition = startPosition;
        return this;
    }

    public int getFirstResult()
    {
        return startPosition;
    }

    public Query setHint(String hintName, Object value)
    {
        return null;
    }

    public Map<String, Object> getHints()
    {
        return null;
    }

    public <T> Query setParameter(Parameter<T> param, T value)
    {
        return null;
    }

    public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType)
    {
        return this;
    }

    public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType)
    {
        return this;
    }

    public Query setParameter(String name, Object value)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));
        return this;
    }

    public Query setParameter(String name, Calendar value, TemporalType temporalType)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));

        return this;
    }

    public Query setParameter(String name, Date value, TemporalType temporalType)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));
        return this;
    }

    public Query setParameter(int position, Object value)
    {
        if (position == -1) {
            position = values.size() + 1;
        }
        values.add(new ParameterImpl(null, position, value.getClass(), value));
        return this;
    }

    public Query setParameter(int position, Calendar value, TemporalType temporalType)
    {
        if (position == -1) {
            position = values.size() + 1;
        }
        values.add(new ParameterImpl(null, position, value.getClass(), value));
        return this;
    }

    public Query setParameter(int position, Date value, TemporalType temporalType)
    {
        if (position == -1) {
            position = values.size() + 1;
        }
        values.add(new ParameterImpl(null, position, value.getClass(), value));
        return this;
    }

    public Set<Parameter<?>> getParameters()
    {
        return values;
    }

    public Parameter<?> getParameter(String name)
    {
        for (Parameter<?> p : values) {
            if (StringUtil.equals(name, p.getName())) {
                return p;
            }
        }
        return null;
    }

    public <T> Parameter<T> getParameter(String name, Class<T> type)
    {
        for (Parameter<?> p : values) {
            if (StringUtil.equals(name, p.getName())) {
                return (Parameter<T>) p;
            }
        }
        return null;
    }

    public Parameter<?> getParameter(int position)
    {
        for (Parameter<?> p : values) {
            if (position == p.getPosition()) {
                return p;
            }
        }
        return null;
    }

    public <T> Parameter<T> getParameter(int position, Class<T> type)
    {
        for (Parameter<?> p : values) {
            if (position == p.getPosition()) {
                return (Parameter<T>) p;
            }
        }
        return null;
    }

    public boolean isBound(Parameter<?> param)
    {
        return false;
    }

    public <T> T getParameterValue(Parameter<T> param)
    {
        return null;
    }

    public Object getParameterValue(String name)
    {
        for (Parameter<?> p : values) {
            if (StringUtil.equals(name, p.getName())) {
                return ((ParameterImpl) p).value;
            }
        }
        return null;
    }

    public Object getParameterValue(int position)
    {
        for (Parameter<?> p : values) {
            if (position == p.getPosition()) {
                return ((ParameterImpl) p).value;
            }
        }
        return null;
    }


    public <T> T unwrap(Class<T> cls)
    {
        return null;
    }

    class ParameterImpl implements Parameter
    {
        private String name = null;

        private Integer position;

        private Class paramType;

        private Object value;

        ParameterImpl(String name, Integer position, Class paramType, Object value)
        {
            this.name = name;
            this.position = position;
            this.paramType = paramType;
            this.value = value;
        }

        public String getName()
        {
            return name;
        }

        public Integer getPosition()
        {
            return position;
        }

        public Class getParameterType()
        {
            return paramType;
        }
    }
}
