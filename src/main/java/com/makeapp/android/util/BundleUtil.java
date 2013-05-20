/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-21 下午3:38 $
 *          $Id$
 */
public class BundleUtil
{
    /**
     * 从bundle 中获取 string 值
     *
     * @param activity
     * @param key
     * @return
     */
    public static String getString(Activity activity, String key)
    {
        try {
            Bundle b = activity.getIntent().getExtras();
            if (b != null) {
                return b.getString(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从bundle 中获取 int 值
     *
     * @param activity
     * @param key
     * @return
     */
    public static int getInt(Activity activity, String key)
    {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return b.getInt(key);
        }
        return -1;
    }

    /**
     * 从bundle 中获取 对象值
     *
     * @param activity
     * @param key
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T getSerializable(Activity activity, String key, Class T)
    {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return (T) b.getSerializable(key);
        }
        return null;
    }

    /**
     * 从 bundle 中获取 boolean 值
     *
     * @param activity
     * @param key
     * @return
     */
    public static boolean getBoolean(Activity activity, String key)
    {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return b.getBoolean(key);
        }
        return false;
    }

    /**
     * 获取 String 型 的arraylist
     *
     * @param activity
     * @param key
     * @return
     */
    public static ArrayList<String> getStringArrayList(Activity activity, String key)
    {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return b.getStringArrayList(key);
        }
        return null;
    }

}
