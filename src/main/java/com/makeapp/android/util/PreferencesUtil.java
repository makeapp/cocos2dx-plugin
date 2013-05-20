/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.Map;

import android.content.SharedPreferences;
import android.content.Context;
import com.makeapp.javase.util.DataUtil;
import com.makeapp.javase.util.HashMapParameters;
import com.makeapp.javase.util.Parameters;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:2010-9-19 11:26:22 $
 *          $Id$
 */
public class PreferencesUtil
{
    public static boolean put(SharedPreferences settings, String key[], Object obj[])
    {
        SharedPreferences.Editor myEditor = settings.edit();
        put(myEditor, key, obj);
        return myEditor.commit();
    }

    public static boolean put(SharedPreferences settings, String key, Object obj)
    {
        SharedPreferences.Editor myEditor = settings.edit();
        put(myEditor, key, obj);
        return myEditor.commit();
    }

    public static boolean puts(SharedPreferences settings, String key[], Object... obj)
    {
        SharedPreferences.Editor myEditor = settings.edit();
        put(myEditor, key, obj);
        return myEditor.commit();
    }

    public static void puts(SharedPreferences.Editor editor, String key[], Object... obj)
    {
        put(editor, key, obj);
    }

    public static void put(SharedPreferences.Editor editor, String key[], Object[] obj)
    {
        for (int i = 0; i < key.length; i++) {
            put(editor, key[i], obj[i]);
        }
    }

    public static void put(SharedPreferences.Editor editor, String key, Object obj)
    {
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        }
        else if (obj instanceof Float) {
            editor.putFloat(key, (Float) (obj));
        }
        else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) (obj));
        }
        else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        }
        else {
            editor.putString(key, String.valueOf(obj));
        }
    }

    @Deprecated
    public static boolean add(Context context, String preferencesName, String key, Object obj)
    {
        return put(context, preferencesName, key, obj);
    }

    public static boolean put(Context context, String preferencesName, String key, Object obj)
    {
        SharedPreferences setting = context.getSharedPreferences(preferencesName, 0);
        return put(setting, key, obj);
    }

    public static boolean put(Context context, String key, Object obj)
    {
      return put(context,context.getPackageName(),key,obj);
    }

    public static boolean put(Context context, String preferencesName, String key[], Object... obj)
    {
        SharedPreferences setting = context.getSharedPreferences(preferencesName, 0);
        return put(setting, key, obj);
    }

    public static boolean put(Context context, String key[], Object... obj)
    {
        return put(context,context.getPackageName(), key, obj);
    }

    /**
     * »ñÈ¡Öµ
     *
     * @param settings
     * @param key
     */
    public static Object get(SharedPreferences settings, String key)
    {
        return settings.getAll().get(key);
    }

    public static int getInt(SharedPreferences settings, String key,int def)
    {
        return DataUtil.getInt(get(settings,key),def);
    }

    public static int getInt(SharedPreferences settings, String key)
    {
        return DataUtil.getInt(get(settings,key));
    }

    public static long getLong(SharedPreferences settings, String key,long def)
    {
        return DataUtil.getLong(get(settings,key),def);
    }

    public static long getLong(SharedPreferences settings, String key)
    {
        return DataUtil.getLong(get(settings,key),0);
    }

    public static boolean getBoolean(SharedPreferences settings, String key,boolean def)
    {
        return DataUtil.getBoolean(get(settings,key),def);
    }

    public static boolean getBoolean(SharedPreferences settings, String key)
    {
        return DataUtil.getBoolean(get(settings,key),false);
    }

    public static float getFloat(SharedPreferences settings, String key,float def)
    {
        return DataUtil.getFloat(get(settings,key),def);
    }

    public static float getFloat(SharedPreferences settings, String key)
    {
        return DataUtil.getFloat(get(settings,key),0);
    }

    public static float getByte(SharedPreferences settings, String key,byte def)
    {
        return DataUtil.getByte(get(settings,key),def);
    }

    public static float getByte(SharedPreferences settings, String key)
    {
        return DataUtil.getByte(get(settings,key),(byte)0);
    }

    public static double getDouble(SharedPreferences settings, String key,double def)
    {
        return DataUtil.getDouble(get(settings,key),def);
    }

    public static double getDouble(SharedPreferences settings, String key)
    {
        return DataUtil.getDouble(get(settings,key),0);
    }

    public static String getString(SharedPreferences settings, String key,String def)
    {
        return DataUtil.getString(get(settings,key),def);
    }

    public static Object getString(SharedPreferences settings, String key)
    {
        return DataUtil.getInt(get(settings,key));
    }

    public static Object get(Context context, String preferencesName, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, 0);
        return get(settings, key);
    }

    public static Object get(Context context, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(context.getPackageName(), 0);
        return get(settings, key);
    }

    public static int getInt(Context context, String preferencesName, String key,int def)
    {
        return DataUtil.getInt(get(context,preferencesName, key),def);
    }

    public static int getInt(Context context, String preferencesName, String key)
    {
        return DataUtil.getInt(get(context,preferencesName, key));
    }

    public static long getLong(Context context, String preferencesName, String key,long def)
    {
        return DataUtil.getLong(get(context,preferencesName, key),def);
    }

    public static long getLong(Context context, String preferencesName, String key)
    {
        return DataUtil.getLong(get(context,preferencesName, key),0);
    }

    public static boolean getBoolean(Context context, String preferencesName, String key,boolean def)
    {
        return DataUtil.getBoolean(get(context,preferencesName, key),def);
    }

    public static boolean getBoolean(Context context, String preferencesName, String key)
    {
        return DataUtil.getBoolean(get(context,preferencesName, key),false);
    }

    public static float getFloat(Context context, String preferencesName, String key,float def)
    {
        return DataUtil.getFloat(get(context,preferencesName, key),def);
    }

    public static float getFloat(Context context, String preferencesName, String key)
    {
        return DataUtil.getFloat(get(context,preferencesName, key),0);
    }

    public static double getDouble(Context context, String preferencesName, String key,double def)
    {
        return DataUtil.getDouble(get(context,preferencesName, key),def);
    }

    public static double getDouble(Context context, String preferencesName, String key)
    {
        return DataUtil.getDouble(get(context,preferencesName, key),0);
    }

    public static String getString(Context context, String preferencesName, String key,String def)
    {
        return DataUtil.getString(get(context,preferencesName, key),def);
    }

    public static String getStringDefault(Context context, String preferencesName, String key)
    {
        return DataUtil.getString(get(context,preferencesName, key),"");
    }

    public static int getInt(Context context, String key,int def)
    {
        return DataUtil.getInt(get(context, key),def);
    }

    public static int getInt(Context context, String key)
    {
        return DataUtil.getInt(get(context, key));
    }

    public static long getLong(Context context,String key,long def)
    {
        return DataUtil.getLong(get(context, key),def);
    }

    public static long getLong(Context context, String key)
    {
        return DataUtil.getLong(get(context, key),0);
    }

    public static boolean getBoolean(Context context, String key,boolean def)
    {
        return DataUtil.getBoolean(get(context, key),def);
    }

    public static boolean getBoolean(Context context, String key)
    {
        return DataUtil.getBoolean(get(context, key),false);
    }

    public static float getFloat(Context context, String key,float def)
    {
        return DataUtil.getFloat(get(context, key),def);
    }

    public static float getFloat(Context context, String key)
    {
        return DataUtil.getFloat(get(context, key),0);
    }

    public static double getDouble(Context context, String key,double def)
    {
        return DataUtil.getDouble(get(context, key),def);
    }

    public static double getDouble(Context context, String key)
    {
        return DataUtil.getDouble(get(context, key),0);
    }

    public static String getString(Context context, String key,String def)
    {
        return DataUtil.getString(get(context, key),def);
    }

    public static String getString(Context context, String key)
    {
        return DataUtil.getString(get(context, key),"");
    }

    public static byte getByte(Context context, String key,byte def)
    {
        return DataUtil.getByte(get(context, key),def);
    }

    public static byte getByte(Context context, String key)
    {
        return DataUtil.getByte(get(context, key),(byte)0);
    }

    public static Parameters getPreferences(Context context, String preferencesName){
        SharedPreferences settings = context.getSharedPreferences(preferencesName, 0);
        return new HashMapParameters((Map<String, Object>) settings.getAll());
    }

    public static void remove(SharedPreferences settings, String... key)
    {
        SharedPreferences.Editor myEditor = settings.edit();
        for (String k : key) {
            myEditor.remove(k);
        }
        myEditor.commit();
    }

    public static void remove(Context context, String preferencesName, String... key)
    {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, 0);
        remove(settings, key);
    }

    public static void clear(SharedPreferences settings)
    {
        SharedPreferences.Editor myEditor = settings.edit();
        myEditor.clear();
        myEditor.commit();
    }

    public static void clear(Context context, String preferencesName)
    {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, 0);
        clear(settings);
    }
}
