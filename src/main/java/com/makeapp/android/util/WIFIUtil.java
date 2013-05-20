/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.net.wifi.WifiManager;
import android.content.*;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-9-18 下午12:24 $
 *          $Id$
 */
public class WIFIUtil
{
    public static void enableWifi(Context context)
    {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        if (!wifimanage.isWifiEnabled()) {
            wifimanage.setWifiEnabled(true);
        }
    }

    public static void setWifiStatus(Context context, boolean enable)
    {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        wifimanage.setWifiEnabled(enable);
    }

    public static void disableWifi(Context context)
    {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        if (wifimanage.isWifiEnabled()) {
            wifimanage.setWifiEnabled(false);
        }
    }

    public static boolean isWifiEnable(Context context)
    {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        return wifimanage.isWifiEnabled();
    }

    public static String intToIP(int i)
    {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }
}
