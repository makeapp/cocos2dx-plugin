/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.List;

import android.app.Activity;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;


/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-9-20 上午11:06 $
 *          $Id$
 */
public class PackageUtil
{
    public static PackageInfo getPackageInfo(Activity activity)
    {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context, String packageName)
    {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPackageVersionName(Context activity)
    {
        return getPackageVersionName(activity, activity.getPackageName());
    }

    public static String getPackageVersionName(Context context, String packageName)
    {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    public static int getPackageVersionCode(Context activity)
    {
        return getPackageVersionCode(activity, activity.getPackageName());
    }

    public static int getPackageVersionCode(Context context, String packageName)
    {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return -1;
    }

    public static String getPackageLabel(Context context, PackageInfo packageInfo)
    {
        if (context != null && packageInfo != null) {
            PackageManager packageManager = context.getPackageManager();
            return (String) packageInfo.applicationInfo.loadLabel(packageManager);
        }
        return null;
    }

    public static Drawable getPackageIcon(Context context, PackageInfo packageInfo)
    {
        if (context != null && packageInfo != null) {
            PackageManager packageManager = context.getPackageManager();
            return packageInfo.applicationInfo.loadIcon(packageManager);
        }
        return null;
    }

    public static Intent getLaunchIntent(Context context, String packageName)
    {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getLaunchIntentForPackage(packageName);
    }


    public static List<PackageInfo> getInstalledPackages(Context context)
    {
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        }
        return null;
    }
}
