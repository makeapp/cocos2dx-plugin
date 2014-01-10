/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-9-2 下午12:56 $
 *          $Id$
 */
public class RadioGroupUtil
{
    public static RadioGroup setOnCheckedChangeListener(View view, int checkboxId, RadioGroup.OnCheckedChangeListener listener)
    {
        RadioGroup checkbox = (RadioGroup) ViewUtil.findViewById(view, checkboxId);
        if (checkbox != null) {
            checkbox.setOnCheckedChangeListener(listener);
        }
        return checkbox;
    }

}
