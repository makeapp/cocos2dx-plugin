/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-7 ÉÏÎç11:26 $
 *          $Id$
 */
public class InputMethodUtil
{
    public static void postShow(final Context context)
    {
        HandlerUtil.postMainAtTime(new Runnable()
        {
            public void run()
            {
                InputMethodManager imm = ServiceUtil.getInputMethodManager(context);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 2000);
    }

    public static void hidden(Context context, View textView)
    {
        InputMethodManager imm = ServiceUtil.getInputMethodManager(context);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }
}
