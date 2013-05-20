/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.makeapp.javase.util.DataUtil;

/**
 * @author <a href="mailto:Administrator@shqianzhi.com">Administrator</a>
 * @version $Date:12-7-13 下午1:42 $
 *          $Id$
 */
public class SpinnerUtil {

    public static void setSelection(View view, int spinnerId, int select) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        if (spinner != null && spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0) {
            setSelection(spinner, select);
        }
    }


    public static void setSelection(Activity activity, int spinnerId, int select) {
        Spinner spinner = (Spinner) activity.findViewById(spinnerId);
        if (spinner != null && spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0) {
            setSelection(spinner, select);
        }
    }

    public static void setSelection(Spinner spinner, int select) {
        if (spinner != null && spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0) {
            if (select < spinner.getAdapter().getCount()) {
                spinner.setSelection(DataUtil.getInt(select, 0));
            } else {
                spinner.setSelection(spinner.getAdapter().getCount() - 1);
            }
        }
    }

    public static void setAdapter(Activity activity, int spinnerId, SpinnerAdapter adapter) {
        Spinner spinner = (Spinner) activity.findViewById(spinnerId);
        spinner.setAdapter(adapter);
    }

    public static void setAdapter(View view, int spinnerId, SpinnerAdapter adapter) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        spinner.setAdapter(adapter);
    }

    public static int getSelectedItemPosition(Activity activity, int spinnerId) {
        Spinner spinner = (Spinner) activity.findViewById(spinnerId);
        return spinner.getSelectedItemPosition();
    }

    public static void setOnItemSelectedListener(View view, int spinnerId, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    public static void setOnItemClickListener(View view, int spinnerId, AdapterView.OnItemClickListener onItemClickListener) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        spinner.setOnItemClickListener(onItemClickListener);
    }

    public static int getSelectedItemPosition(View view, int spinnerId) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        return spinner.getSelectedItemPosition();
    }

    public static View getSelectedView(View view, int spinnerId) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        return spinner.getSelectedView();
    }

    public static View getSelectedView(Activity activity, int spinnerId) {
        Spinner spinner = (Spinner) activity.findViewById(spinnerId);
        return spinner.getSelectedView();
    }

    public static Object getSelectedItem(View view, int spinnerId) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        return spinner.getSelectedItem();
    }
}
