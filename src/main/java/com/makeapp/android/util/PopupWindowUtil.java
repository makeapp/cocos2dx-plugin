/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Dialog;
import android.widget.PopupWindow;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-4 обнГ3:00 $
 *          $Id$
 */
public class PopupWindowUtil
{
    public static void dismiss(PopupWindow dialog)
    {
        if (dialog!=null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
