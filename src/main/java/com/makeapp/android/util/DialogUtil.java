/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-11-5 ÏÂÎç5:31 $
 *          $Id$
 */
public class DialogUtil
{
    public static ProgressDialog createProgressDialog(Context context)
    {
        return createProgressDialog(context, null);
    }

    public static ProgressDialog createProgressDialog(Context context, String title)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public static AlertDialog showAlert(Context context)
    {
        return new AlertDialog.Builder(context).create();
    }

    public static void dismiss(Dialog dialog)
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

    public static void dismiss(DialogInterface dialog)
    {
        if (dialog != null) {
            try {
                dialog.dismiss();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static AlertDialog showAlert(Context context, String title, String message,
                                        String cancelString, DialogInterface.OnClickListener cancelClickListener,
                                        String okString, DialogInterface.OnClickListener okClickListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message)
            .setNegativeButton(cancelString, cancelClickListener).setPositiveButton(okString, okClickListener);
        AlertDialog dialog = builder.create();

        dialog.show();

        return dialog;
    }

    public static AlertDialog showAlert(Context context, String title, String message,
                                        String cancelString,
                                        String okString, DialogInterface.OnClickListener okClickListener)
    {
        return showAlert(context, title, message, cancelString, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }, okString, okClickListener);
    }
}
