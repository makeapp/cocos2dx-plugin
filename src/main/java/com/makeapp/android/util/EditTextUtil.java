/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.text.Editable;
import android.widget.EditText;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-14 ����6:34 $
 *          $Id$
 */
public class EditTextUtil extends TextViewUtil
{

    /**
     * @param activity
     * @param editTextId
     */
    @Deprecated
    public static String getEditTextString(Object activity, int editTextId)
    {
        EditText textView = (EditText) ViewUtil.findViewById(activity, editTextId);

        if (textView != null) {
            return textView.getText().toString().trim();
        }
        return null;
    }

    public static Editable getEditable(Object activity, int editTextId)
    {
        EditText textView = (EditText) ViewUtil.findViewById(activity, editTextId);

        if (textView != null) {
            return textView.getText();
        }
        return null;
    }
}
