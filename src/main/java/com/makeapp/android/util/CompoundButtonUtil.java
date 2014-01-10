/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-14 ����12:11 $
 *          $Id$
 */
public class CompoundButtonUtil
{
    public static CompoundButton setOnCheckedChangeListener(View view, int checkboxId, CompoundButton.OnCheckedChangeListener listener)
    {
        CompoundButton checkbox = (CompoundButton) ViewUtil.findViewById(view, checkboxId);
        if (checkbox != null) {
            checkbox.setOnCheckedChangeListener(listener);
        }
        return checkbox;
    }

    public static CompoundButton setViewChecked(View view, int checkboxId, boolean checked)
    {
        CompoundButton checkbox = (CompoundButton) ViewUtil.findViewById(view, checkboxId);
        if (checkbox != null) {
            checkbox.setChecked(checked);
        }
        return checkbox;
    }
}
