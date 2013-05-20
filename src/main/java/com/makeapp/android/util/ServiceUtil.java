package com.makeapp.android.util;

import java.util.ArrayList;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import com.makeapp.javase.lang.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-15
 * Time: ����5:09
 */
public class ServiceUtil
{

    public static PackageManager getPackageManager(Context context)
    {
        return (PackageManager) context.getPackageManager();
    }

    public static AssetManager getAssetManager(Context context)
    {
        return (AssetManager) context.getAssets();
    }

    public static WindowManager getWindowManager(Context context)
    {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static ActivityManager getActivityManager(Context context)
    {
        return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public static AccountManager getAccountManager(Context context)
    {
        return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
    }

    public static AlarmManager getAlarmManager(Context context)
    {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static AccessibilityManager getAccessibilityManager(Context context)
    {
        return (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public static AudioManager getAudioManager(Context context)
    {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static ClipboardManager getClipboardManager(Context context)
    {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public static ConnectivityManager getConnectivityManager(Context context)
    {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static DevicePolicyManager getDevicePolicyManager(Context context)
    {
        return (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    public static DropBoxManager getDropBoxManager(Context context)
    {
        return (DropBoxManager) context.getSystemService(Context.DROPBOX_SERVICE);
    }

    public static InputMethodManager getInputMethodManager(Context context)
    {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyguardManager getKeyguardManager(Context context)
    {
        return (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    }

    public static LayoutInflater getLayoutInflater(Context context)
    {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LocationManager getLocationManager(Context context)
    {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static NotificationManager getNotificationManager(Context context)
    {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static PowerManager getPowerManager(Context context)
    {
        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }

    public static SearchManager getSearchManager(Context context)
    {
        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }

    public static SensorManager getSensorManager(Context context)
    {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public static TelephonyManager getTelephonyManager(Context context)
    {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static UiModeManager getUiModeManager(Context context)
    {
        return (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
    }

    public static Vibrator getVibrator(Context context)
    {
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static WallpaperManager getWallpaperManager(Context context)
    {
        return (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
    }

    public static boolean isServiceRunning(Context context, Class clazz)
    {
        return isServiceRunning(context, clazz.getName());
    }

    public static boolean isServiceRunning(Context context, String clazzName)
    {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServices = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(60);
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
            if (StringUtil.equalsIgnoreCase(serviceInfo.service.getClassName(), clazzName)) {
                return true;
            }
        }
        return false;
    }

    public static void startSerivce(Context context, Class clazz, int flags)
    {
        startSerivce(context, clazz, flags, null);
    }

    public static void startSerivce(Context context, Class clazz)
    {
        startSerivce(context, clazz, -1, null);
    }

    public static void startSerivce(Context context, Class clazz, int flags, Bundle bundel)
    {
        Intent intent = new Intent(context, clazz);
        if (flags != -1) {
            intent.setFlags(flags);
        }
        if (bundel != null) {
            intent.putExtras(bundel);
        }
        context.startActivity(intent);
    }

}
