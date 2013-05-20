/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-12 ÏÂÎç3:48 $
 *          $Id$
 */
public class ResourcesUtil
{

    public static final String RES_TYPE_COLOR = "color";
    public static final String RES_TYPE_DRAWABLE = "drawable";
    public static final String RES_TYPE_INTEGER = "integer";
    public static final String RES_TYPE_BOOL = "bool";
    public static final String RES_TYPE_XML = "xml";
    public static final String RES_TYPE_STRING = "string";
    public final static String RES_TYPE_INTEGER_ARRAY = "intarray";
    public final static String RES_TYPE_STRING_ARRAY = "stringarray";

    public static Resources getResources(Context context)
    {
        return context.getResources();
    }

    public static int getIdentifier(Context context, String name, String defType, String pkgName)
    {
        return getResources(context).getIdentifier(name, defType, pkgName);
    }

    public static int getIdentifier(Context context, String name, String defType)
    {
        return getResources(context).getIdentifier(name, defType, context.getPackageName());
    }

    public static String[] getStringArray(Context context, String name)
    {
        return getResources(context).getStringArray(getIdentifier(context, name, RES_TYPE_STRING_ARRAY));
    }

    public static String getString(Context context, String name, Object... args)
    {
        return getResources(context).getString(getIdentifier(context, name, RES_TYPE_STRING), args);
    }

    public static boolean getBoolean(Context context, String name)
    {
        return getResources(context).getBoolean(getIdentifier(context, name, RES_TYPE_BOOL));
    }

    public static int getInteger(Context context, String name)
    {
        return getResources(context).getInteger(getIdentifier(context, name, RES_TYPE_INTEGER));
    }

    public static int[] getIntegerArray(Context context, String name)
    {
        return getResources(context).getIntArray(getIdentifier(context, name, RES_TYPE_INTEGER_ARRAY));
    }

    public static int getColor(Context context, String name)
    {
        return getResources(context).getColor(getIdentifier(context, name, RES_TYPE_COLOR));
    }

    public static Drawable getDrawable(Context context, String name)
    {
        return getResources(context).getDrawable(getIdentifier(context, name, RES_TYPE_DRAWABLE));
    }

    public static XmlResourceParser getXml(Context context, String name)
    {
        return getResources(context).getXml(getIdentifier(context, name, RES_TYPE_XML));
    }

    public static String[] getStringArray(Context context,String pkgName, String name)
    {
        return getResources(context).getStringArray(getIdentifier(context, name, RES_TYPE_STRING_ARRAY,pkgName));
    }

    public static String getString(Context context,String pkgName, String name, Object... args)
    {
        return getResources(context).getString(getIdentifier(context, name, RES_TYPE_STRING,pkgName), args);
    }

    public static boolean getBoolean(Context context,String pkgName, String name)
    {
        return getResources(context).getBoolean(getIdentifier(context, name, RES_TYPE_BOOL,pkgName));
    }

    public static int getInteger(Context context,String pkgName, String name)
    {
        return getResources(context).getInteger(getIdentifier(context, name, RES_TYPE_INTEGER,pkgName));
    }

    public static int[] getIntegerArray(Context context,String pkgName, String name)
    {
        return getResources(context).getIntArray(getIdentifier(context, name, RES_TYPE_INTEGER_ARRAY,pkgName));
    }

    public static int getColor(Context context,String pkgName, String name)
    {
        return getResources(context).getColor(getIdentifier(context, name, RES_TYPE_COLOR,pkgName));
    }

    public static Drawable getDrawable(Context context,String pkgName, String name)
    {
        return getResources(context).getDrawable(getIdentifier(context, name, RES_TYPE_DRAWABLE,pkgName));
    }

    public static XmlResourceParser getXml(Context context,String pkgName, String name)
    {
        return getResources(context).getXml(getIdentifier(context, name, RES_TYPE_XML,pkgName));
    }
}
