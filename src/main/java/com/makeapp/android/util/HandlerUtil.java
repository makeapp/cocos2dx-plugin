package com.makeapp.android.util;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-15
 * Time: ����4:59
 */
public class HandlerUtil
{
    static Handler mainHandper = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler()
    {
        return mainHandper;
    }

    public static Handler getHandler()
    {
        return new Handler();
    }

    public static void handleMessage(Message msg)
    {
        getHandler().handleMessage(msg);
    }

    public static void dispatchMessage(Message msg)
    {
        getHandler().dispatchMessage(msg);
    }

    public static void handleMainMessage(Message msg)
    {
        getMainHandler().handleMessage(msg);
    }

    public static void dispatchMainMessage(Message msg)
    {
        getHandler().dispatchMessage(msg);
    }

    public static Message obtainMessage()
    {
        return getHandler().obtainMessage();
    }

    public static Message obtainMessage(int what)
    {
        return getHandler().obtainMessage(what);
    }

    public static Message obtainMessage(int what, Object obj)
    {
        return getHandler().obtainMessage(what, obj);
    }

    public static Message obtainMessage(int what, int arg1, int arg2)
    {
        return getHandler().obtainMessage(what, arg1, arg2);
    }

    public static Message obtainMessage(int what, int arg1, int arg2, Object obj)
    {
        return getHandler().obtainMessage(what, arg1, arg2, obj);
    }

    public static boolean post(Runnable r)
    {
        return getHandler().post(r);
    }

    public static boolean postAtTime(Runnable r, long uptimeMillis)
    {
        return getHandler().postAtTime(r, uptimeMillis);
    }

    public static boolean postAtTime(Runnable r, Object token, long uptimeMillis)
    {
        return getHandler().postAtTime(r, token, uptimeMillis);
    }

    public static boolean postDelayed(Runnable r, long delayMillis)
    {
        return getHandler().postDelayed(r, delayMillis);
    }

    public static boolean postAtFrontOfQueue(Runnable r)
    {
        return getHandler().postAtFrontOfQueue(r);
    }

    public static void removeCallbacks(Runnable r)
    {
        getHandler().removeCallbacks(r);
    }

    public static void removeCallbacks(Runnable r, Object token)
    {
        getHandler().removeCallbacks(r, token);
    }

    public static boolean sendMessage(Message msg)
    {
        return getHandler().sendMessage(msg);
    }

    public static boolean sendEmptyMessage(int what)
    {
        return getHandler().sendEmptyMessage(what);
    }

    public static boolean sendEmptyMessageDelayed(int what, long delayMillis)
    {
        return getHandler().sendEmptyMessageDelayed(what, delayMillis);
    }

    public static boolean sendEmptyMessageAtTime(int what, long uptimeMillis)
    {
        return getHandler().sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public static boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        return getHandler().sendMessageDelayed(msg, delayMillis);
    }

    public static boolean sendMessageAtTime(Message msg, long uptimeMillis)
    {
        return getHandler().sendMessageAtTime(msg, uptimeMillis);
    }

    public static boolean sendMessageAtFrontOfQueue(Message msg)
    {
        return getHandler().sendMessageAtFrontOfQueue(msg);
    }

    public static void removeMessages(int what)
    {
        getHandler().removeMessages(what);
    }

    public static void removeMessages(int what, Object object)
    {
        getHandler().removeMessages(what, object);
    }

    public static void removeCallbacksAndMessages(Object token)
    {
        getHandler().removeCallbacksAndMessages(token);
    }

    public static boolean hasMessages(int what)
    {
        return getHandler().hasMessages(what);
    }

    public static boolean hasMessages(int what, Object object)
    {
        return getHandler().hasMessages(what, object);
    }

    public static Looper getLooper()
    {
        return getHandler().getLooper();
    }


    public static boolean postMain(Runnable r)
    {
        return getMainHandler().post(r);
    }

    public static boolean postMainAtTime(Runnable r, long uptimeMillis)
    {
        return getMainHandler().postAtTime(r, uptimeMillis);
    }

    public static boolean postMainAtTime(Runnable r, Object token, long uptimeMillis)
    {
        return getMainHandler().postAtTime(r, token, uptimeMillis);
    }

    public static boolean postMainDelayed(Runnable r, long delayMillis)
    {
        return getMainHandler().postDelayed(r, delayMillis);
    }

    public static boolean postMainAtFrontOfQueue(Runnable r)
    {
        return getMainHandler().postAtFrontOfQueue(r);
    }

    public static void removeMainCallbacks(Runnable r)
    {
        getMainHandler().removeCallbacks(r);
    }

    public static void removeMainCallbacks(Runnable r, Object token)
    {
        getMainHandler().removeCallbacks(r, token);
    }

    public static boolean sendMainMessage(Message msg)
    {
        return getMainHandler().sendMessage(msg);
    }

    public static boolean sendMainEmptyMessage(int what)
    {
        return getMainHandler().sendEmptyMessage(what);
    }

    public static boolean sendMainEmptyMessageDelayed(int what, long delayMillis)
    {
        return getMainHandler().sendEmptyMessageDelayed(what, delayMillis);
    }

    public static boolean sendMainEmptyMessageAtTime(int what, long uptimeMillis)
    {
        return getMainHandler().sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public static boolean sendMainMessageDelayed(Message msg, long delayMillis)
    {
        return getMainHandler().sendMessageDelayed(msg, delayMillis);
    }

    public static boolean sendMainMessageAtTime(Message msg, long uptimeMillis)
    {
        return getMainHandler().sendMessageAtTime(msg, uptimeMillis);
    }

    public static boolean sendMainMessageAtFrontOfQueue(Message msg)
    {
        return getMainHandler().sendMessageAtFrontOfQueue(msg);
    }

    public static void removeMainMessages(int what)
    {
        getMainHandler().removeMessages(what);
    }

    public static void removeMainMessages(int what, Object object)
    {
        getMainHandler().removeMessages(what, object);
    }

    public static void removeMainCallbacksAndMessages(Object token)
    {
        getMainHandler().removeCallbacksAndMessages(token);
    }

    public static boolean hasMainMessages(int what)
    {
        return getMainHandler().hasMessages(what);
    }

    public static boolean hasMainMessages(int what, Object object)
    {
        return getMainHandler().hasMessages(what, object);
    }


}
