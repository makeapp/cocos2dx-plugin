package com.makeapp.android.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.makeapp.javase.util.DataUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-13
 * Time: ����4:31
 */
public class EntityField
{
    private String columeName;
    private Class type;
    private Field field;

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    public String getColumeName()
    {
        return columeName;
    }

    public void setColumeName(String columeName)
    {
        this.columeName = columeName;
    }

//    private Column column;
//
//    public Column getColumn()
//    {
//        return column;
//    }
//
//    public void setColumn(Column column)
//    {
//        this.column = column;
//    }

    private int maxLength = 50;

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(int maxLength)
    {
        if (maxLength > 0) {
            this.maxLength = maxLength;
        }
    }

    private SqlDataType dataType;

    public SqlDataType getDataType()
    {
        return dataType;
    }

    public void setDataType(SqlDataType dataType)
    {
        this.dataType = dataType;
    }

    Method setMethod;

    public Method getSetMethod()
    {
        return setMethod;
    }

    public void setSetMethod(Method setMethod)
    {
        this.setMethod = setMethod;
    }

    public void setSetMethodValue(Object obj, Object value)
    {
        if (setMethod != null) {
            try {
                setMethod.invoke(obj, DataUtil.getObject(setMethod.getParameterTypes()[0], value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    Method getMethod;

    public Method getGetMethod()
    {
        return getMethod;
    }

    public void setGetMethod(Method getMethod)
    {
        this.getMethod = getMethod;
    }


    public Object getGetMethodValue(Object obj)
    {
        if (getMethod != null) {
            try {
                return getMethod.invoke(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean primaryKey;

    public boolean isPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    private boolean autoIncrement = false;

    public boolean isAutoIncrement()
    {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement)
    {
        this.autoIncrement = autoIncrement;
    }

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isOptional()
    {
        return true;
    }

    public Class getType()
    {
        return type;
    }
}
