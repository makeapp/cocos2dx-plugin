/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.DataUtil;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-14 下午3:51 $
 *          $Id$
 */
public class TextViewUtil extends ViewUtil
{
    /**
     * 设置textView 内容
     *
     * @param container
     * @param textViewId textVie id
     * @param text       值
     */
    public static TextView setText(Object container, int textViewId, CharSequence text)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            textView.setText(text);
        }
        return textView;
    }

    public static TextView setText(Object container, int textViewId, int textId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            textView.setText(textId);
        }
        return textView;
    }

    /**
     * 获取 textView 的 内容
     *
     * @param container
     * @param textViewId
     */
    public static CharSequence getText(Object container, int textViewId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            return textView.getText();
        }
        return null;
    }


    public static String getTextString(TextView textView)
    {
        if (textView != null) {
            CharSequence charSequence = textView.getText();
            if (charSequence instanceof String) {
                return ((String) charSequence).trim();
            }
            else if (charSequence instanceof Editable) {
                Editable editable = (Editable) charSequence;
                return editable.toString().trim();
            }
            else {
                return charSequence.toString().trim();
            }
        }
        return null;
    }

    public static String getTextHtml(TextView textView)
    {
        if (textView != null) {
            CharSequence charSequence = textView.getText();
            if (charSequence instanceof String) {
                return ((String) charSequence).trim();
            }
            else if (charSequence instanceof Editable) {
                Editable editable = (Editable) charSequence;
                return com.makeapp.android.text.Html.toHtml(editable);
            }
            else {
                return charSequence.toString().trim();
            }
        }
        return null;
    }

    public static String getTextString(Object activity, int editTextId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, editTextId);
        return getTextString(textView);
    }

    public static String getTextHtml(Object activity, int editTextId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, editTextId);
        return getTextHtml(textView);
    }

    public static String getTextString(Object activity, int editTextId, String defValue)
    {
        return StringUtil.getString(getTextString(activity, editTextId), defValue);
    }

    public static int getTextInt(Object activity, int editTextId)
    {
        return getTextInt(activity, editTextId);
    }

    public static int getTextInt(Object activity, int editTextId, int defValue)
    {
        return DataUtil.getInt(getTextString(activity, editTextId), defValue);
    }

    public static float getTextFloat(Object activity, int editTextId)
    {
        return getTextFloat(activity, editTextId, 0);
    }

    public static float getTextFloat(Object activity, int editTextId, float defValue)
    {
        return DataUtil.getFloat(getTextString(activity, editTextId), defValue);
    }

    @Deprecated
    public static String getEditTextText(Object activity, int editTextId)
    {
        return getTextString(activity, editTextId);
    }

    /**
     * 设置textView 内容
     *
     * @param activity
     * @param textViewId textVie id
     * @param text       值
     * @param tf         字体样式
     */
    public static TextView setText(Object activity, int textViewId, CharSequence text, android.graphics.Typeface tf)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, textViewId);
        if (textView != null) {
            textView.setText(DataUtil.getString(text, ""));
            textView.setTypeface(tf);
        }
        return textView;
    }

    public static TextView setTextColor(Object activity, int textViewId, int color)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, textViewId);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return textView;
    }

    public static TextView setTextColorResource(Activity activity, int textViewId, int colorRes)
    {
        TextView textView = (TextView) activity.findViewById(textViewId);
        if (textView != null) {
            textView.setTextColor(activity.getResources().getColor(colorRes));
        }
        return textView;
    }

    public static TextView setTextColorResource(View view, int textViewId, int colorRes)
    {
        TextView textView = (TextView) view.findViewById(textViewId);
        if (textView != null) {
            textView.setTextColor(view.getResources().getColor(colorRes));
        }
        return textView;
    }

}
