/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;


/**
 * @author <a href="mailto:yinhua@shqianzhi.com">yinhua</a>
 * @version $Date:11-11-8 上午11:49 $
 *          $Id$
 */
public class ViewUtil
{
    public static View findViewById(Object contrainer, int id)
    {
        if (contrainer instanceof View) {
            return ((View) contrainer).findViewById(id);
        }
        else if (contrainer instanceof PopupWindow) {
            return findViewById(((PopupWindow) contrainer).getContentView(), id);
        }
        else if (contrainer instanceof Dialog) {
            return ((Dialog) contrainer).findViewById(id);
        }
        else if (contrainer instanceof Activity) {
            return ((Activity) contrainer).findViewById(id);
        }
        Log.e("ViewUtil", "can't find view from " + contrainer + " id=" + id);
        return null;
    }

    /**
     * 指定context，并且可以加载布局，返回View
     *
     * @param activity 本身Activity
     * @param context  指定的Context
     * @param resId    指定的资源
     *
     * @return 新的View
     */
    public static View getViewInContext(final Activity activity, final Context context, int resId)
    {
        LayoutInflater layoutInflater = ViewGroupUtil.cloneInContext(activity, context);
        return layoutInflater.inflate(resId, null);
    }

    /**
     * 启动view动画.
     *
     * @param context
     * @param view
     * @param resId
     */
    public static void startAnimation(Context context, View view, int resId)
    {
        Animation animation = AnimationUtils.loadAnimation(context, resId);
        if (animation != null) {
            view.startAnimation(animation);
        }
    }

    /**
     * 设置子view的Visibility属性.
     *
     * @param view
     * @param id
     * @param visibility
     */
    public static void setChildVisibility(View view, int id, int visibility)
    {
        View v = findViewById(view, id);
        if (v != null) {
            v.setVisibility(visibility);
        }
    }

    public static void setViewBackgroundResource(Object contrainer, int id, int resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundResource(resId);
        }
    }

    public static void setViewBackgroundResource(Object contrainer, int id, Drawable resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundDrawable(resId);
        }
    }

    public static void setViewBackgroundColor(Object contrainer, int id, int resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundColor(resId);
        }
    }



    /**
     * 这是activity view的Visibility属性.
     *
     * @param view
     * @param id
     * @param visibility
     */
    public static void setViewVisibility(Object view, int id, int visibility)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setVisibility(visibility);
            }
        }
    }

    public static void setViewOnClickListener(Object view, int id, View.OnClickListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnClickListener(listener);
            }
        }
    }

    public static void setViewOnFocusChangeListener(Object view, int id, View.OnFocusChangeListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnFocusChangeListener(listener);
            }
        }
    }

    public static void setViewOnKeyListener(Object activity, int id, View.OnKeyListener listener)
    {
        if (activity != null) {
            View v = findViewById(activity, id);
            if (v != null) {
                v.setOnKeyListener(listener);
            }
        }
    }

    public static void setViewOnTouchListener(Object view, int id, View.OnTouchListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnTouchListener(listener);
            }
        }
    }


    public static void setViewOnLongClickListener(Object activity, int id, View.OnLongClickListener listener)
    {
        if (activity != null) {
            View v = findViewById(activity, id);
            if (v != null) {
                v.setOnLongClickListener(listener);
            }
        }
    }

    public static void setViewOnCreateContextMenuListener(Object view, int id, View.OnCreateContextMenuListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnCreateContextMenuListener(listener);
            }
        }
    }

    /**
     * 这是activity view的setSelected属性.
     *
     * @param view
     * @param id
     * @param selected
     */
    public static void setViewSelected(Object view, int id, boolean selected)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setSelected(selected);
            }
        }
    }

    public static void setViewEnabled(Object view, int id, boolean enabled)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setEnabled(enabled);
            }
        }
    }

    public static void setViewPressed(Object view, int id, boolean pressed)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setPressed(pressed);
            }
        }
    }

    public static void setViewAnimation(Object view, int id, Animation animation)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setAnimation(animation);
            }
        }
    }

    public static void setViewFocusable(Object view, int id, boolean selected)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setFocusable(selected);
            }
        }
    }

    public static Bitmap saveView(View view, String fileName)
    {
        if (view == null) {
            return null;
        }

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        try {
            BitmapUtil.saveBitmap(b1, Bitmap.CompressFormat.JPEG, fileName);
        }
        finally {
            view.destroyDrawingCache();
        }
        return b1;
    }

}
