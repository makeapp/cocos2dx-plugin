/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
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
    public static View setChildVisibility(View view, int id, int visibility)
    {
        View v = findViewById(view, id);
        if (v != null) {
            v.setVisibility(visibility);
        }
        return v;
    }

    public static View setViewBackgroundResource(Object contrainer, int id, int resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundResource(resId);
        }
        return v;
    }

    public static View setViewBackgroundResource(Object contrainer, int id, Drawable resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundDrawable(resId);
        }
        return v;
    }

    public static View setViewBackgroundColor(Object contrainer, int id, int resId)
    {
        View v = findViewById(contrainer, id);
        if (v != null) {
            v.setBackgroundColor(resId);
        }
        return v;
    }


    /**
     * 这是activity view的Visibility属性.
     *
     * @param view
     * @param id
     * @param visibility
     */
    public static View setViewVisibility(Object view, int id, int visibility)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setVisibility(visibility);
            }
            return v;
        }
        return null;
    }

    public static View setViewOnClickListener(Object view, int id, View.OnClickListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnClickListener(listener);
            }
            return v;
        }
        return null;
    }

    public static View setViewOnFocusChangeListener(Object view, int id, View.OnFocusChangeListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnFocusChangeListener(listener);
            }
            return v;
        }
        return null;
    }

    public static View setViewOnKeyListener(Object activity, int id, View.OnKeyListener listener)
    {
        if (activity != null) {
            View v = findViewById(activity, id);
            if (v != null) {
                v.setOnKeyListener(listener);
            }
            return v;
        }
        return null;
    }

    public static View setViewOnTouchListener(Object view, int id, View.OnTouchListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnTouchListener(listener);
            }
            return v;
        }
        return null;
    }


    public static View setViewOnLongClickListener(Object activity, int id, View.OnLongClickListener listener)
    {
        if (activity != null) {
            View v = findViewById(activity, id);
            if (v != null) {
                v.setOnLongClickListener(listener);
            }
            return v;
        }
        return null;
    }

    public static View setViewOnCreateContextMenuListener(Object view, int id, View.OnCreateContextMenuListener listener)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setOnCreateContextMenuListener(listener);
            }
            return v;
        }
        return null;
    }

    /**
     * 这是activity view的setSelected属性.
     *
     * @param view
     * @param id
     * @param selected
     */
    public static View setViewSelected(Object view, int id, boolean selected)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setSelected(selected);
            }
            return v;
        }
        return null;
    }

    public static View toggleViewVisibility(Object view, int id)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                int vi = v.getVisibility();
                v.setVisibility(vi == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            }
            return v;
        }
        return null;
    }

    public static View toggleViewGone(Object view, int id)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                int vi = v.getVisibility();
                v.setVisibility(vi == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
            return v;
        }
        return null;
    }

    public static View setViewEnabled(Object view, int id, boolean enabled)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setEnabled(enabled);
            }
            return v;
        }
        return null;
    }

    public static View setViewPressed(Object view, int id, boolean pressed)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setPressed(pressed);
            }
            return v;
        }
        return null;
    }

    public static View setViewAnimation(Object view, int id, Animation animation)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setAnimation(animation);
            }
            return v;
        }
        return null;
    }

    public static View setViewFocusable(Object view, int id, boolean selected)
    {
        if (view != null) {
            View v = findViewById(view, id);
            if (v != null) {
                v.setFocusable(selected);
            }
            return v;
        }
        return null;
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


    public static View startBackgroundAnimationDrawable(Object view, int imgId)
    {
        View imageView = ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        if (imageView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getBackground()).start();
        }

        return imageView;
    }

    public static View startBackgroundAnimationDrawable(Object view, int imgId,int resId)
    {
        View imageView = ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setBackgroundResource(resId);
        if (imageView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getBackground()).start();
        }

        return imageView;
    }

    public static View startBackgroundAnimationDrawable(View imageView)
    {
        if (imageView == null) {
            return null;
        }
        if (imageView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getBackground()).start();
        }

        return imageView;
    }

    public static View stopBackgroundAnimationDrawable(View imageView)
    {
        if (imageView == null) {
            return null;
        }
        if (imageView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getBackground()).stop();
        }

        return imageView;
    }

    public static View stopBackgroundAnimationDrawable(Object view, int imgId)
    {
        View imageView = (View) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        if (imageView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getBackground()).stop();
        }

        return imageView;
    }

}
