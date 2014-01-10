/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.jpa;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.makeapp.javase.lang.ArrayUtil;
import com.makeapp.javase.lang.FieldUtil;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.log.Logger;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:10-11-22 ����6:28 $
 *          $Id$
 */
public class AndroidEntityManagerFactory
    implements EntityManagerFactory
{
    PersistenceUnitEntity unitEntity;
    Logger logger = Logger.getLogger("SQLiteJPA");

    public AndroidEntityManagerFactory(PersistenceUnitEntity unitEntity)
    {
        this.unitEntity = unitEntity;

        buildTables();
    }

    private void buildTables()
    {
        SQLiteDatabase sqLiteDatabase = openSQLiteDatabase();

        try {
            List<EntityClass> entities = unitEntity.getEntityClasses();
            for (EntityClass ec : entities) {
                buildEntityField(ec);
                buildEntityTable(sqLiteDatabase, ec);
            }
//            metamodel = MetamodelImpl.buildMetamodel(entities);

        }
        finally {
            if (sqLiteDatabase != null) {
                logger.info("close database");
                sqLiteDatabase.close();
            }
        }
    }

    private void buildEntityField(EntityClass entity)
    {
        Entity entityAn = (Entity) entity.getClazz().getAnnotation(Entity.class);
        if (entityAn == null) {
            logger.info("invalid entity {0}", entity.getClassName());
            return;
        }
        Table table = (Table) entity.getClazz().getAnnotation(Table.class);
        String tableName = null;
        if (table != null) {
            tableName = table.name();
        }
        if (StringUtil.isInvalid(tableName)) {
            tableName = entity.getClass().getSimpleName();
        }
        entity.setTableName(tableName);
        Field[] fields = entity.getClazz().getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            Transient trst = field.getAnnotation(Transient.class);
            Id id = field.getAnnotation(Id.class);
            Method getMethod = FieldUtil.getGetMethod(field);
            Method setMethod = FieldUtil.getSetMethod(field);
            if (getMethod == null || setMethod == null) {
                continue;
            }
            if (column == null) {
                column = getMethod.getAnnotation(Column.class);
            }
            if (trst == null) {
                trst = getMethod.getAnnotation(Transient.class);
            }
            if (trst != null) {
                continue;
            }
            if (id == null) {
                id = getMethod.getAnnotation(Id.class);
            }

            EntityField entityField = new EntityField();
            entityField.setSetMethod(setMethod);
            entityField.setGetMethod(getMethod);
            if (column != null) {
                entityField.setColumeName(StringUtil.isValid(column.name()) ? column.name() : field.getName());
                entityField.setMaxLength(column.length());
            }
            else {
                entityField.setColumeName(field.getName());
                entityField.setMaxLength(250);
            }
            entityField.setPrimaryKey(id != null);
            entityField.setField(field);
            entityField.setName(field.getName());
            if (id != null) {
                GeneratedValue gv = field.getAnnotation(GeneratedValue.class);
                if (gv == null) {
                    gv = getMethod.getAnnotation(GeneratedValue.class);
                }
                if (gv != null) {
                    if (GenerationType.AUTO.equals(gv.strategy())) {
                        entityField.setAutoIncrement(true);
                    }
                }
                entity.setPrimaryKeyField(entityField);
            }
            Class filedType = field.getType();
            String filedTypeName = filedType.getName();
            if (filedType.equals(Integer.class) || "int".equals(filedTypeName)) {
                entityField.setDataType(SqlDataType.Integer);
            }
            else if (filedType.equals(String.class)) {
                entityField.setDataType(SqlDataType.Varchar);
            }
            else if (filedType.equals(Boolean.class) || "boolean".equals(filedTypeName)) {
                entityField.setDataType(SqlDataType.Boolean);
            }
            else if (filedType.equals(Date.class)) {
                entityField.setDataType(SqlDataType.DateTime);
            }
            else {
                entityField.setDataType(SqlDataType.Varchar);
            }
            entity.addEntityField(entityField);
        }
    }

    private void buildEntityTable(SQLiteDatabase sqlitedb, EntityClass ec)
    {
        Cursor cursor = null;
        String[] columnNames;
        try {
            cursor = sqlitedb.rawQuery("select * from " + ec.getTableName() + " limit 1", new String[0]);
            columnNames = cursor.getColumnNames();
            cursor.close();
        }
        catch (Throwable e) {
            e.printStackTrace();
            columnNames = new String[0];
        }

        if (ArrayUtil.isValid(columnNames)) {
            List<String> allNames = new ArrayList();
            for (String name : columnNames) {
                allNames.add(name.toLowerCase());
            }
            for (EntityField field : ec.getFields()) {
                if(allNames.contains(field.getColumeName().toLowerCase())){
                   continue;
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append("ALTER TABLE ").append(ec.getTableName()).append(" ADD ");
                buffer.append(field.getColumeName()).append(" ");
                SqlDataType dataType = field.getDataType();
                if (dataType.equals(SqlDataType.Varchar)) {
                    buffer.append(" varchar(").append(field.getMaxLength()).append(")");
                }
                else if (dataType.equals(SqlDataType.DateTime)) {
                    buffer.append(" integer ");
                }
                else {
                    buffer.append(dataType.toString());
                }
                if (field.isPrimaryKey()) {
                    buffer.append(" PRIMARY KEY ");
                }
                if (field.isAutoIncrement()) {
                    buffer.append(" AUTOINCREMENT ");
                }
                buffer.append(";");
                logger.info("alter table:" + buffer.toString());
                try {
                    sqlitedb.execSQL(buffer.toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table IF NOT EXISTS  ");
            buffer.append(ec.getTableName());
            buffer.append("(");
            int i = 0;
            for (EntityField field : ec.getFields()) {
                if (i > 0) {
                    buffer.append(",\n");
                }
                buffer.append(field.getColumeName()).append(" ");
                SqlDataType dataType = field.getDataType();
                if (dataType.equals(SqlDataType.Varchar)) {
                    buffer.append(" varchar(").append(field.getMaxLength()).append(")");
                }
                else if (dataType.equals(SqlDataType.DateTime)) {
                    buffer.append(" integer ");
                }
                else {
                    buffer.append(dataType.toString());
                }
                if (field.isPrimaryKey()) {
                    buffer.append(" PRIMARY KEY ");
                }
                if (field.isAutoIncrement()) {
                    buffer.append(" AUTOINCREMENT ");
                }
                i++;
            }
            buffer.append(")");
            logger.info("create table:" + buffer.toString());
            try {
                sqlitedb.execSQL(buffer.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean tableExists(SQLiteDatabase sqlitedb, String name)
    {
        sqlitedb.compileStatement("select * from " + name + " where ");

        return false;
    }

    public EntityManager createEntityManager()
    {
        return createEntityManager(null);
    }

    private SQLiteDatabase openSQLiteDatabase()
    {

        String url = unitEntity.getProperty("url");
        String dbQname = url.substring("jdbc:sqldroid:".length());
        File file = new File(dbQname);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        logger.info("open database {0}", url);

        return SQLiteDatabase.openDatabase(dbQname, null, SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.OPEN_READWRITE);
    }

    public EntityManager createEntityManager(Map map)
    {
        String url = unitEntity.getProperty("url");
        String dbQname = url.substring("jdbc:sqldroid:".length());

        return new AndroidEntityManager(unitEntity, dbQname, this);
    }

//    public CriteriaBuilder getCriteriaBuilder()
//    {
//        return new CriteriaBuilderImpl(this);
//    }
//
//    public Metamodel getMetamodel()
//    {
//        return metamodel;
//    }

    public boolean isOpen()
    {
        return false;
    }

    public void close()
    {

    }

    public Map<String, Object> getProperties()
    {
        return null;
    }

    public Cache getCache()
    {
        return null;
    }
//
//    public PersistenceUnitUtil getPersistenceUnitUtil()
//    {
//        return null;
//    }
}
