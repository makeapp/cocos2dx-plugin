/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.view.View;
import android.widget.PopupWindow;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-4 ����3:00 $
 *          $Id$
 */
public class PopupWindowUtil
{
    public static boolean toggle(PopupWindow dialog, View parent, int g, int x, int y)
    {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                return false;
            }
            else {
                dialog.showAtLocation(parent, g, x, y);
                return true;
            }
        }
        return false;
    }

    public static boolean toggleAsDropDown(PopupWindow dialog, View parent, int x, int y)
    {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                return false;
            }
            else {
                dialog.showAsDropDown(parent, x, y);
                return true;
            }
        }
        return false;
    }

    public static boolean toggleAsDropDown(PopupWindow dialog, View parent)
    {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                return false;
            }
            else {
                dialog.showAsDropDown(parent);
                return true;
            }
        }
        return false;
    }


    public static void dismiss(PopupWindow dialog)
    {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
