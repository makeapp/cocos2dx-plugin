/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.List;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:2010-8-31 18:22:51 $
 *          $Id$
 */
public class NetworkUtil
{
    /**
     *
     * @param context
     */
    public static boolean checkNetwork(Context context)
    {
        boolean flag = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager.getActiveNetworkInfo() != null) {
            flag = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    public static boolean checkNetwork(Context context, int resourceId)
    {
        boolean flag = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager.getActiveNetworkInfo() != null) {
            flag = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            Toast.makeText(context, resourceId, Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    /**
     *
     * @param context
     */
    public static boolean isGpsEnabled(Context context)
    {
        LocationManager locationManager = ((LocationManager) context
            .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     */
    public static boolean isTelephonyEnabled(Context context)
    {
        ConnectivityManager mgrConn = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
            .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
            .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
            .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
     *
     * @param context
     *
     * @return boolean
     */
    public static boolean isWifiEnabled(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
            && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param context
     *
     * @return boolean
     */
    public static boolean is3GEnabled(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
            && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static void setMobileDataEnabled(Context context, boolean enable)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.setMobileDataEnabled(enable);
    }

    public static boolean isMobileDataEnabled(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getMobileDataEnabled();
    }

}
