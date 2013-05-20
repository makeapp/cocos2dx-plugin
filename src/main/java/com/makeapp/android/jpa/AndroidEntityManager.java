/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.jpa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.makeapp.android.jpa.criteria.CriteriaBuilderImpl;
import com.makeapp.android.jpa.criteria.CriteriaQueryCompiler;
import com.makeapp.javase.log.Logger;
import com.makeapp.javase.util.DataUtil;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:10-11-22 ����6:29 $
 *          $Id$
 */
public class AndroidEntityManager
    implements EntityManager
{
    SQLiteDatabase sqlitedb1;

    PersistenceUnitEntity unitEntity;

    Logger logger = Logger.getLogger("SQLiteJPA");

    AndroidEntityManagerFactory androidEntityManagerFactory;

    private String database = null;

    private static Object writeLock = new Object();

    public AndroidEntityManager(PersistenceUnitEntity unitEntity, String database, AndroidEntityManagerFactory androidEntityManagerFactory)
    {
        this.unitEntity = unitEntity;
        this.database = database;
        this.androidEntityManagerFactory = androidEntityManagerFactory;
    }

    private SQLiteDatabase openSQLiteDatabase(boolean readOnly)
    {
        if (sqlitedb1 == null || !sqlitedb1.isOpen()) {
            logger.info("open database", database);
            sqlitedb1 = SQLiteDatabase.openDatabase(database, null, SQLiteDatabase.CREATE_IF_NECESSARY |
                (readOnly ? SQLiteDatabase.OPEN_READONLY : SQLiteDatabase.OPEN_READWRITE));
        }
        return sqlitedb1;
    }


    public void persist(Object entity)
    {
        synchronized (writeLock) {
            SQLiteDatabase db = openSQLiteDatabase(false);
//            try {
            EntityClass entityClass = unitEntity.getEntityClass(entity.getClass().getName());
            if (entityClass != null) {
                EntityField primaryKey = entityClass.getPrimaryKeyField();
                Object id = primaryKey.getGetMethodValue(entity);

                if (id == null || DataUtil.getInt(id) <= 0) {
                    executeInsert(db, entity, entityClass);
                }
                else {
                    executeUpdate(db, entity, entityClass, id);
                }
            }
            else {
                logger.error("Invalid EntityClass "+entity.getClass().getName());
            }
//            }
//            finally {
//                close();
//            }
        }
    }

    private void executeInsert(SQLiteDatabase db, Object entity, EntityClass entityClass)
    {
        String tableName = entityClass.getTableName();
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("insert into ").append(tableName).append(" (");
        List<EntityField> fields = entityClass.getInsertFields();
        for (int i = 0; i < fields.size(); i++) {
            EntityField field = fields.get(i);
            if (i > 0) {
                sqlBuf.append(",");
            }
            sqlBuf.append(field.getColumeName());
        }
        sqlBuf.append(") values (");
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sqlBuf.append(",");
            }
            sqlBuf.append("?");
        }
        sqlBuf.append(")");

        SQLiteStatement ps = db.compileStatement(sqlBuf.toString());

        sqlBuf.append(" ");

        for (int i = 1; i <= fields.size(); i++) {
            EntityField field = fields.get(i - 1);
            Object value = field.getGetMethodValue(entity);
            if (field.getDataType().equals(SqlDataType.Varchar)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    String v = DataUtil.getString(value, "");
                    ps.bindString(i, v);
                }
            }
            else if (field.getDataType().equals(SqlDataType.Integer)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    ps.bindLong(i, DataUtil.getInt(value));
                }
            }
            else if (field.getDataType().equals(SqlDataType.DateTime)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    Date date = (Date) value;
                    ps.bindLong(i, date.getTime());
                }
            }
            else if (field.getDataType().equals(SqlDataType.Boolean)) {
                if (value == null) {
                    ps.bindLong(i, 0);
                }
                else {
                    ps.bindLong(i, DataUtil.getBoolean(value, false) ? 0 : 1);
                }
            }
            sqlBuf.append(value).append("|");
        }

        logger.info("execute insert :" + sqlBuf.toString());

        long result = ps.executeInsert();
        EntityField keyField = entityClass.getPrimaryKeyField();
        if (keyField != null) {
            keyField.setSetMethodValue(entity, result);
        }
        logger.info("insert result "+result);
    }

    private void executeUpdate(SQLiteDatabase db, Object entity, EntityClass entityClass, Object id)
    {

        String tableName = entityClass.getTableName();
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("update ").append(tableName).append(" set ");
        List<EntityField> fields = entityClass.getInsertFields();
        boolean isFirst = true;
        for (int i = 0; i < fields.size(); i++) {
            EntityField field = fields.get(i);
            if (field.isPrimaryKey()) {
                continue;
            }
            if (!isFirst) {
                sqlBuf.append(",");
            }
            else {
                isFirst = false;
            }
            sqlBuf.append(field.getColumeName());
            sqlBuf.append("=");
            sqlBuf.append("?");
        }
        sqlBuf.append(" where id = '" + id + "'");

        SQLiteStatement ps = db.compileStatement(sqlBuf.toString());

        sqlBuf.append(" ");

        for (int i = 1; i <= fields.size(); i++) {
            EntityField field = fields.get(i - 1);
            Object value = field.getGetMethodValue(entity);
            if (field.getDataType().equals(SqlDataType.Varchar)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    String v = DataUtil.getString(value, "");
                    ps.bindString(i, v);
                }
            }
            else if (field.getDataType().equals(SqlDataType.Integer)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    ps.bindLong(i, DataUtil.getInt(value));
                }
            }
            else if (field.getDataType().equals(SqlDataType.DateTime)) {
                if (value == null) {
                    ps.bindNull(i);
                }
                else {
                    Date date = (Date) value;
                    ps.bindLong(i, date.getTime());
                }
            }
            else if (field.getDataType().equals(SqlDataType.Boolean)) {
                if (value == null) {
                    ps.bindLong(i, 0);
                }
                else {
                    ps.bindLong(i, DataUtil.getBoolean(value, false) ? 0 : 1);
                }
            }
            sqlBuf.append(value).append("|");
        }

        logger.info("execute update :" + sqlBuf.toString());

        long result = ps.executeInsert();

        logger.info("update result "+result);
    }


    public <T> T merge(T entity)
    {
        return null;
    }

    public void remove(Object entity)
    {
        if (entity == null) {
            return;
        }
        synchronized (writeLock) {

            SQLiteDatabase db = openSQLiteDatabase(false);
//            try {
            EntityClass entityClass = unitEntity.getEntityClass(entity.getClass().getName());
            if (entityClass != null) {
                EntityField field = entityClass.getPrimaryKeyField();
                if (field != null) {
                    Object idValue = field.getGetMethodValue(entity);
                    if (idValue != null) {
                        StringBuffer sqlBuf = new StringBuffer();
                        sqlBuf.append("delete from ").append(entityClass.getTableName());
                        sqlBuf.append(" where ").append(field.getColumeName()).append("=").append("?");
                        SQLiteStatement ps = db.compileStatement(sqlBuf.toString());
                        if (field.getDataType().equals(SqlDataType.Varchar)) {
                            ps.bindString(1, (String) idValue);
                        }
                        else if (field.getDataType().equals(SqlDataType.Integer)) {
                            ps.bindLong(1, DataUtil.getInt(idValue));
                        }
                        sqlBuf.append(" ").append(idValue);
                        logger.info(sqlBuf.toString());
                        ps.execute();
                    }
                    else {
                        logger.error("invalid PrimaryKey id value");
                    }
                }
                else {
                    logger.error("invalid PrimaryKey entity");
                }
            }
//            }
//            finally {
//                close();
//            }
        }
    }

    public <T> T find(Class<T> clazz, Object primaryKey)
    {
        SQLiteDatabase db = openSQLiteDatabase(true);

//        try {
            EntityClass entityClass = unitEntity.getEntityClass(clazz.getName());
            if (entityClass != null) {
                EntityField field = entityClass.getPrimaryKeyField();
                if (field != null) {
                    StringBuffer sqlBuf = new StringBuffer();
                    sqlBuf.append("select * from ").append(entityClass.getTableName());
                    sqlBuf.append(" where ").append(field.getColumeName()).append("=");

                    if (field.getDataType().equals(SqlDataType.Varchar)) {
                        sqlBuf.append("'").append(primaryKey).append("'");
                    }
                    else if (field.getDataType().equals(SqlDataType.Integer)) {
                        sqlBuf.append(primaryKey);
                    }
                    logger.info(sqlBuf.toString());
                    Cursor cursor = db.rawQuery(sqlBuf.toString(), null);
                    try {
                        if (cursor.moveToFirst() && cursor.getCount() > 0) {
                            return getEntity(clazz, cursor);
                        }
                    }
                    finally {
                        cursor.close();
                    }
                }
            }
//        }
//        finally {
//            close();
//        }
        return null;
    }

    public <T> T getEntity(final Class<T> clazz, Cursor cursor)
    {

        EntityClass entityClass = unitEntity.getEntityClass(clazz.getName());
        T obj = null;
        try {
            obj = clazz.newInstance();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (EntityField field : entityClass.getFields()) {
            Object value = null;
            if (field.getDataType().equals(SqlDataType.Varchar)) {
                value = cursor.getString(cursor.getColumnIndex(field.getColumeName()));
            }
            else if (field.getDataType().equals(SqlDataType.Integer)) {
                value = cursor.getInt(cursor.getColumnIndex(field.getColumeName()));
            }
            else if (field.getDataType().equals(SqlDataType.Date)) {
                value = new Date(cursor.getLong(cursor.getColumnIndex(field.getColumeName())));
            }
            else if (field.getDataType().equals(SqlDataType.DateTime)) {
                long timestamp = cursor.getLong(cursor.getColumnIndex(field.getColumeName()));
                if (timestamp > 0) {
                    value = new Date(timestamp);
                }
            }
            else if (field.getDataType().equals(SqlDataType.Boolean)) {
                value = cursor.getInt(cursor.getColumnIndex(field.getColumeName()));
            }
            if (value != null) {
                field.setSetMethodValue(obj, value);
            }
        }

        return obj;
    }

    public <T> List<T> getEntities(final Class<T> clazz, Cursor cursor)
    {
        return getEntities(clazz, cursor, true);
    }

    public <T> List<T> getEntities(final Class<T> clazz, Cursor cursor, boolean closeCursor)
    {
        List<T> entities = new ArrayList<T>();

        if (clazz == null || cursor == null) {
            return entities;
        }

        try {
            do {
                entities.add(getEntity(clazz, cursor));
            }
            while (cursor.moveToNext());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties)
    {
        return find(entityClass, primaryKey);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode)
    {
        return find(entityClass, primaryKey);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties)
    {
        return find(entityClass, primaryKey);
    }

    public <T> T getReference(Class<T> clazz, Object primaryKey)
    {
        EntityClass entityClass = unitEntity.getEntityClass(clazz.getName());
        if (entityClass != null) {
            EntityField field = entityClass.getPrimaryKeyField();
            if (field != null) {
                try {
                    T obj = clazz.newInstance();
                    field.setSetMethodValue(obj, primaryKey);
                    return obj;
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void flush()
    {

    }

    public void setFlushMode(FlushModeType flushMode)
    {

    }

    public FlushModeType getFlushMode()
    {
        return null;
    }

    public void lock(Object entity, LockModeType lockMode)
    {

    }

    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties)
    {

    }

    public void refresh(Object entity)
    {

    }

    public void refresh(Object entity, Map<String, Object> properties)
    {

    }

    public void refresh(Object entity, LockModeType lockMode)
    {

    }

    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties)
    {

    }

    public void clear()
    {

    }

    public void detach(Object entity)
    {

    }

    public boolean contains(Object entity)
    {
        SQLiteDatabase db = openSQLiteDatabase(true);

//        try {
            EntityClass entityClass = unitEntity.getEntityClass(entity.getClass().getName());
            if (entityClass != null) {
                EntityField field = entityClass.getPrimaryKeyField();
                if (field != null) {
                    Object primaryKey = field.getGetMethodValue(entity);
                    StringBuffer sqlBuf = new StringBuffer();
                    sqlBuf.append("select count(*) from ").append(entityClass.getTableName());
                    sqlBuf.append(" where ").append(field.getColumeName()).append("=");

                    if (field.getDataType().equals(SqlDataType.Varchar)) {
                        sqlBuf.append("'").append(primaryKey).append("'");
                    }
                    else if (field.getDataType().equals(SqlDataType.Integer)) {
                        sqlBuf.append(primaryKey);
                    }
                    logger.info(sqlBuf.toString());
                    Cursor cursor = db.rawQuery(sqlBuf.toString(), null);
                    try {
                        if (cursor.moveToFirst() && cursor.getCount() > 0) {
                            return cursor.getInt(1) > 0;
                        }
                    }
                    finally {
                        cursor.close();
                    }
                }
            }
//        }
//        finally {
//            close();
//        }
        return false;
    }

    public LockModeType getLockMode(Object entity)
    {
        return null;
    }

    public void setProperty(String propertyName, Object value)
    {

    }

    public Map<String, Object> getProperties()
    {
        return null;
    }

    public Query createQuery(String qlString)
    {
        return new AndroidQuery(this, qlString, null);
    }

    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery)
    {
        CriteriaQueryCompiler compiler = new CriteriaQueryCompiler(this);
        return compiler.compile(criteriaQuery);
    }

    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass)
    {
        return new AndroidQuery<T>(this, qlString, resultClass);
    }

    public Query createNamedQuery(String name)
    {
        return null;
    }

    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass)
    {
        return null;
    }

    public Query createNativeQuery(String sqlString)
    {
        return new AndroidQuery(this, sqlString, null);
    }

    public Query createNativeQuery(String sqlString, Class resultClass)
    {
        return new AndroidQuery(this, sqlString, resultClass);
    }

    public Query createNativeQuery(String sqlString, String resultSetMapping)
    {
        return new AndroidQuery(this, sqlString, null);
    }

    public void joinTransaction()
    {

    }

    public <T> T unwrap(Class<T> cls)
    {
        return null;
    }

    public Object getDelegate()
    {
        return null;
    }

    public void close()
    {
        if (sqlitedb1 != null) {
            logger.info("close database {0}", database);
            if (sqlitedb1.isOpen()) {
                sqlitedb1.close();
            }
        }
    }

    public boolean isOpen()
    {
        return sqlitedb1.isOpen();
    }

    public EntityTransaction getTransaction()
    {
        return null;
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return androidEntityManagerFactory;
    }

    public CriteriaBuilder getCriteriaBuilder()
    {
        return new CriteriaBuilderImpl(androidEntityManagerFactory);
    }

    public Metamodel getMetamodel()
    {
        return null;
    }

    public Cursor rawQuery(String sql, String[] bindValues)
    {
        SQLiteDatabase db = openSQLiteDatabase(true);
        logger.info(sql);
        return db.rawQuery(sql, bindValues);
    }
}
