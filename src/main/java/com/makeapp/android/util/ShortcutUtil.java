package com.makeapp.android.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-17
 * Time: 下午3:18
 */
public class ShortcutUtil
{
    /**
     * 为程序创建桌面快捷方式.
     * <p/>
     * 在AndroidManifest.xml 文件中声明 创建和删除快捷方式时声明权限
     * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     *
     * @param activity
     * @param name
     * @param packageName
     * @param className
     */
    private void addShortcut(Activity activity, String name, String packageName, String className, int iconResId)
    {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        shortcut.putExtra("duplicate", false); //不允许重复创建

        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
        ComponentName comp = new ComponentName(packageName, "." + className);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        //快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity,iconResId);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除程序的快捷方式.
     * 在AndroidManifest.xml 文件中声明 创建和删除快捷方式时声明权限
     * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
     *
     * @param activity
     * @param name
     * @param packageName
     * @param className
     */
    @Deprecated
    private void removeShortcut(Activity activity, String name, String packageName, String className)
    {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = packageName + "." + className;
        ComponentName comp = new ComponentName(packageName, appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        activity.sendBroadcast(shortcut);
    }


    /**
     * 为程序创建桌面快捷方式
     *
     * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     * @param activity
     * @param shortcutName 快捷方式名称
     * @param shortcutIcon 快捷方式图标
     * @param isRepeat     是否能重复创建
     */
    @Deprecated
    public static void addShortcut(Activity activity, String shortcutName, int shortcutIcon, boolean isRepeat)
    {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        shortcut.putExtra("duplicate", isRepeat); //不允许重复创建

        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
        ComponentName comp = new ComponentName(activity.getPackageName(), "." + activity.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        //快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity, shortcutIcon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除快捷方式
     *
     * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
     * @param activity
     * @param shortcutName 快捷方式名称
     */
    public static void remShortcut(Activity activity, String shortcutName)
    {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);

        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = activity.getPackageName() + "." + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        activity.sendBroadcast(shortcut);
    }
}
