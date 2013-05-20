package com.makeapp.android.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-20
 * Time: 下午1:10
 */
public class ToastUtil
{
    /**
     * 显示内容提示.
     *
     * @param context  上下文.
     * @param text     文本内容
     * @param position 位置 Gravity.CENTER;Gravity.LEFT等
     * @param time     显示时间  Toast.LENGTH_SHORT 或者 Toast.LENGTH_LONG
     * @param view     附加图片
     */
    public static Toast show(Context context, String text, int position, int time, View view)
    {
        Toast toast = Toast.makeText(context, text, time);
        toast.setGravity(position, 0, 0);
        if (view != null) {
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.addView(view, 0);
        }
        toast.show();

        return toast;
    }


    public static void postShow(final Context context, final String text, final int position, final int time, final View view)
    {
        HandlerUtil.postMain(new Runnable()
        {
            public void run()
            {
                Toast toast = Toast.makeText(context, text, time);
                toast.setGravity(position, 0, 0);
                if (view != null) {
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    toastView.addView(view, 0);
                }
                toast.show();
            }
        });

    }

    public static void postShow(final Context context, final int resId, final int position, final int time, final View view)
    {
        HandlerUtil.postMain(new Runnable()
        {
            public void run()
            {
                Toast toast = Toast.makeText(context, resId, time);
                toast.setGravity(position, 0, 0);
                if (view != null) {
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    toastView.addView(view, 0);
                }
                toast.show();
            }
        });
    }

    public static Toast show(Context context, int resId, int position, int time, View view)
    {
        Toast toast = Toast.makeText(context, resId, time);
        toast.setGravity(position, 0, 0);
        if (view != null) {
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.addView(view, 0);
        }
        toast.show();

        return toast;
    }

    /**
     * 显示内容提示.
     *
     * @param context 上下文.
     * @param text    文本内容
     */
    public static Toast show(Context context, String text)
    {
        return show(context, text, Gravity.CENTER, Toast.LENGTH_SHORT, null);
    }

    public static void postShow(Context context, String text)
    {
        postShow(context, text, Gravity.CENTER, Toast.LENGTH_SHORT, null);
    }

    public static Toast show(Context context, int resId)
    {
        return show(context, resId, Gravity.CENTER, Toast.LENGTH_SHORT, null);
    }

    public static void postShow(Context context, int resId)
    {
        postShow(context, resId, Gravity.CENTER, Toast.LENGTH_SHORT, null);
    }


    /**
     * 显示内容提示.
     *
     * @param context 上下文.
     * @param text    文本内容
     * @param time    显示时间  Toast.LENGTH_SHORT 或者 Toast.LENGTH_LONG
     */
    public static Toast show(Context context, String text, int time)
    {
        return show(context, text, Gravity.CENTER, time, null);
    }

    public static Toast show(Context context, int resId, int time)
    {
        return show(context, resId, Gravity.CENTER, time, null);
    }

    /**
     * 显示内容提示.
     *
     * @param context  上下文.
     * @param view     任意View对象.
     * @param position 位置 Gravity.CENTER;Gravity.LEFT等
     * @param time     显示时间  Toast.LENGTH_SHORT 或者 Toast.LENGTH_LONG
     * @param view     附加图片
     */
    public static Toast show(Context context, View view, int position, int time)
    {
        Toast toast = new Toast(context);
        toast.setGravity(position, 0, 0);
        toast.setDuration(time);
        toast.setView(view);
        toast.show();

        return toast;
    }

    public static void postShow(final Context context, final View view, final int position, final int time)
    {
        HandlerUtil.postMain(new Runnable()
        {
            public void run()
            {
                Toast toast = new Toast(context);
                toast.setGravity(position, 0, 0);
                toast.setDuration(time);
                toast.setView(view);
                toast.show();
            }
        });
    }
}
