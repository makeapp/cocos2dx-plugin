package com.makeapp.android.jpa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-13
 * Time: ÏÂÎç4:30
 */
public class EntityClass
{
    private Class clazz;

    public Class getClazz()
    {
        return clazz;
    }

    public void setClazz(Class clazz)
    {
        this.clazz = clazz;
    }

    private String className;

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    private String tableName;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    private List<EntityField> fields = new ArrayList<EntityField>();

    public void addEntityField(EntityField field)
    {
        fields.add(field);
    }

    public List<EntityField> getFields()
    {
        return fields;
    }

    public List<EntityField> getInsertFields()
    {
        List<EntityField> result = new ArrayList<EntityField>();
        for (EntityField field : fields) {
            if (!field.isPrimaryKey()) {
                result.add(field);
            }
        }
        return result;
    }

    private EntityField primaryKeyField;

    public EntityField getPrimaryKeyField()
    {
        return primaryKeyField;
    }

    public void setPrimaryKeyField(EntityField primaryKeyField)
    {
        this.primaryKeyField = primaryKeyField;
    }

    private String entityName;

    public String getEntityName()
    {
        return entityName;
    }
}
