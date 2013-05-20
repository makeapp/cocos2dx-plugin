/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@shqianzhi.com">Administrator</a>
 * @version $Date:2010-6-11 11:13:46 $
 *          $Id$
 */
public class DatabaseUtil
{
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static SQLiteDatabase createOrOpenDB(Context context, String dbName, int modeType, SQLiteDatabase.CursorFactory cursor)
    {
        return context.openOrCreateDatabase(dbName, modeType, cursor);
    }

    public static void createTable(SQLiteDatabase myDB, String tableName, HashMap<String, String> columns)
    {
        StringBuffer sql = new StringBuffer("CREATE TABLE " + tableName + "(");
        int i = 1;
        for (String key : columns.keySet()) {
            sql.append(key);
            sql.append("  ");
            sql.append(columns.get(key));
            if (i++ < columns.size()) {
                sql.append(" , ");
            }
        }
        sql.append(")");
        myDB.execSQL(sql.toString());
    }

    public static boolean isExistsTable(SQLiteDatabase myDB, String tableName)
    {
        boolean b = false;
        Cursor c = myDB.rawQuery("select count(*) from sqlite_master where type='table' and name='" + tableName + "'", null); //判断是否存在这张表
        try {
            c.moveToFirst();
            int isExist = c.getInt(c.getColumnIndex("count(*)"));
            if (isExist > 0) {
                b = true;
            }
        }
        finally {
            c.close();
        }
        return b;
    }

    public static void closeDB(SQLiteDatabase myDB)
    {
        myDB.close();
    }


    //------------- cursor helper

    public static void close(Cursor cursor)
    {
        if (cursor != null) {
            try {
                cursor.close();
            }
            catch (Exception e) {
            }
        }
    }

    public static String getSetMethodName(String columnName, Class clazz)
    {
        StringBuffer buf = new StringBuffer();
        buf.append("set");
        if (clazz.isAssignableFrom(boolean.class)) {
            buf.append(columnName.substring(2));
        }
        else {
            buf.append(Character.toUpperCase(columnName.charAt(0)));
            buf.append(columnName.substring(1));
        }
        return buf.toString();
    }

    private static Object getValue(Class clazz, Cursor c1, String columnName)
    {
        if (clazz.isAssignableFrom(int.class)) {
            return c1.getInt(c1.getColumnIndex(columnName));
        }
        else if (clazz.isAssignableFrom(String.class)) {
            return c1.getString(c1.getColumnIndex(columnName));
        }
        else if (clazz.isAssignableFrom(boolean.class)) {
            return c1.getInt(c1.getColumnIndex(columnName)) > 0 ? true : false;
        }
        else if (clazz.isAssignableFrom(Date.class)) {
            Date d = null;
            try {
                d = format.parse(c1.getString(c1.getColumnIndex(columnName)));
            }
            catch (Exception e) {
                d = new Date();
            }
            return d;
        }
        return null;
    }

    public static <T> T getEntity(final Class<T> clazz, Cursor cursor)
    {
        return getEntity(clazz, cursor, true);
    }

    public static <T> T getEntity(final Class<T> clazz, Cursor cursor, boolean closeCursor)
    {
        T t = null;
        try {
            if (clazz == null || cursor == null) {
                return t;
            }
            t = clazz.newInstance();
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Field field = clazz.getDeclaredField(columnName);
                String methodName = getSetMethodName(columnName, field.getType());
                if (field != null) {
                    Method method = clazz.getDeclaredMethod(methodName, field.getType());
                    if (method != null) {
                        Object value = getValue(field.getType(), cursor, columnName);
                        if (value != null) {
                            method.invoke(t, value);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (closeCursor) {
                cursor.close();
            }
        }
        return t;
    }

    public static <T> List<T> getEntities(final Class<T> clazz, Cursor cursor)
    {
        return getEntities(clazz, cursor, true);
    }

    public static <T> List<T> getEntities(final Class<T> clazz, Cursor cursor, boolean closeCursor)
    {
        List<T> entities = new ArrayList<T>();

        if (clazz == null || cursor == null) {
            return entities;
        }

        try {
            do {
                entities.add(getEntity(clazz, cursor, false));
            }
            while (cursor.moveToNext());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (closeCursor) {
                cursor.close();
            }
        }
        return entities;
    }
}
