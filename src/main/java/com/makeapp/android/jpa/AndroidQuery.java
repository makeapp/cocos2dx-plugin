package com.makeapp.android.jpa;

import android.database.Cursor;
import com.makeapp.javase.log.Logger;
import com.makeapp.javase.lang.ArrayUtil;
import com.makeapp.javase.util.DataUtil;
import com.makeapp.javase.lang.StringUtil;

import javax.persistence.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-18
 * Time: ����10:48
 */
public class AndroidQuery<X>
    implements TypedQuery<X>
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

    private String[] getBindValues()
    {
        String[] bindValues = new String[values.size()];
        for (int i = 1; i <= values.size(); i++) {
            for (Parameter<?> p : values) {
                if (i == p.getPosition()) {
                    bindValues[i - 1] = DataUtil.getString(((ParameterImpl) p).value, null);
                    break;
                }
            }
        }
        return bindValues;
    }

    public List getResultList()
    {
        logger.info(getSql());

        Cursor cursor = entityManager.rawQuery(getSql(), getBindValues());
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

    public X getSingleResult()
    {
        String[] values = getBindValues();
        StringBuffer buffer = new StringBuffer();
        buffer.append(sql);
        for (String v : values) {
            buffer.append("|").append(v);
        }
        Cursor cursor = entityManager.rawQuery(getSql(), values);
        try {
            if (cursor.moveToNext()) {
                return (X) getEntity(cursor);
            }
        }
        finally {
            cursor.close();
        }
        return null;
    }

    public Object getEntity(Cursor cursor)
    {
        boolean selectValue = sql.indexOf("count") > 0;
        if (resultClass == null || selectValue) {
            int columnCount = cursor.getColumnCount();
            Object[] results = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                results[i] = cursor.getString(i);
            }
            if (selectValue && ArrayUtil.isValid(results)) {
                return results[0];
            }
            return results;
        }
        else {
            return entityManager.getEntity(resultClass, cursor);
        }
    }

    public int executeUpdate()
    {
        return 0;
    }

    public TypedQuery<X> setMaxResults(int maxResult)
    {
        this.maxResult = maxResult;
        return this;
    }

    public int getMaxResults()
    {
        return maxResult;
    }

    public TypedQuery<X> setFirstResult(int startPosition)
    {
        this.startPosition = startPosition;
        return this;
    }

    public int getFirstResult()
    {
        return startPosition;
    }

    public TypedQuery<X> setHint(String hintName, Object value)
    {
        return null;
    }

    public Map<String, Object> getHints()
    {
        return null;
    }

    public <T> TypedQuery<X> setParameter(Parameter<T> param, T value)
    {
        return null;
    }

    public TypedQuery<X> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType)
    {
        return this;
    }

    public TypedQuery<X> setParameter(Parameter<Date> param, Date value, TemporalType temporalType)
    {
        return this;
    }

    public TypedQuery<X> setParameter(String name, Object value)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));
        return this;
    }

    public TypedQuery<X> setParameter(String name, Calendar value, TemporalType temporalType)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));

        return this;
    }

    public TypedQuery<X> setParameter(String name, Date value, TemporalType temporalType)
    {
        values.add(new ParameterImpl(name, -1, value.getClass(), value));
        return this;
    }

    public TypedQuery<X> setParameter(int position, Object value)
    {
        if (position == -1) {
            position = values.size() + 1;
        }
        values.add(new ParameterImpl(null, position, value.getClass(), value));
        return this;
    }

    public TypedQuery<X> setParameter(int position, Calendar value, TemporalType temporalType)
    {
        if (position == -1) {
            position = values.size() + 1;
        }
        values.add(new ParameterImpl(null, position, value.getClass(), value));
        return this;
    }

    public TypedQuery<X> setParameter(int position, Date value, TemporalType temporalType)
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

    public TypedQuery<X> setFlushMode(FlushModeType flushMode)
    {
        return null;
    }

    public FlushModeType getFlushMode()
    {
        return null;
    }

    public TypedQuery<X> setLockMode(LockModeType lockMode)
    {
        return null;
    }

    public LockModeType getLockMode()
    {
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
