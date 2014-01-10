package com.makeapp.android.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-6-3
 * Time: ????3:47
 */
public class NotificationUtil
{


//    public static void showNotification(Context context, Class clazz, Integer notificationIcon, CharSequence name, CharSequence title, CharSequence text, int notificationNum)
//    {
//        showNotification(context, clazz, notificationIcon, name, title, text, notificationNum, isOngoing, null, false, null);
//    }

    public static void cancel(Context context, int id)
    {
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public static void showNotification(Context context, Intent notificationIntent,Integer notificationIcon, RemoteViews remoteViews, int flags, int notificationId, int totalNum, Uri soundUri, boolean vibrate)
    {
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification();
        notification.icon = notificationIcon;
//        notification.tickerText = name;
        notification.contentView = remoteViews;
       /* if (isOngoing) {
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }*/
        if (soundUri != null) {
            notification.sound = soundUri;
        }
        else {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        notification.flags = flags;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent=pendingIntent;

        notification.number = totalNum;
        notification.when = System.currentTimeMillis();
        notificationManager.notify(notificationId, notification);
    }

    public static void showNotification(Context context, Intent notificationIntent, Integer notificationIcon, CharSequence name, CharSequence title, CharSequence text, int flags, int notificationId, int totalNum, Uri soundUri, boolean vibrate)
    {
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(notificationIcon, name, System.currentTimeMillis());
       /* if (isOngoing) {
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }*/
        if (soundUri != null) {
            notification.sound = soundUri;
        }
        else {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        notification.flags = flags;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, title, text, pendingIntent);
        notification.number = totalNum;
        notification.when = System.currentTimeMillis();
        notificationManager.notify(notificationId, notification);
    }

    /**
     * @param context
     */
    public static void cancelNotification(Context context, int notificationNum)
    {
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationNum);
    }

}
