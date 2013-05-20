/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-14 ÏÂÎç12:11 $
 *          $Id$
 */
public class CompoundButtonUtil extends ViewUtil
{
    public static void setOnCheckedChangeListener(View view, int checkboxId, CompoundButton.OnCheckedChangeListener listener)
    {
        CheckBox checkbox = (CheckBox) ViewUtil.findViewById(view, checkboxId);
        if (checkbox != null) {
            checkbox.setOnCheckedChangeListener(listener);
        }
    }

    public static void setViewChecked(View view, int checkboxId, boolean checked)
    {
        CheckBox checkbox = (CheckBox) ViewUtil.findViewById(view, checkboxId);
        if (checkbox != null) {
            checkbox.setChecked(checked);
        }
    }
}
