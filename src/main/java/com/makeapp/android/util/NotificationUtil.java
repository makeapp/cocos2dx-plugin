package com.makeapp.android.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-6-3
 * Time: ????3:47
 */
public class NotificationUtil
{


    public static void showNotification(Context context, Class clazz, Integer notificationIcon, CharSequence name, CharSequence title, CharSequence text, int notificationNum, boolean isOngoing)
    {
        NotificationManager notificationManager = (NotificationManager)
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(notificationIcon, name, System.currentTimeMillis());
        if (isOngoing) {
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }

        CharSequence contentTitle = title;
        CharSequence contentText = text;
        Intent notificationIntent = new Intent(context, clazz);
        Bundle b = new Bundle();
        notificationIntent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);

        notificationManager.notify(notificationNum, notification);
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
