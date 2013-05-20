/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author <a href="mailto:Administrator@shqianzhi.com">Administrator</a>
 * @version $Date:12-8-16 下午5:52 $
 *          $Id$
 */
public class ListViewUtil
{
    public static void notifyDataSetChanged(Object activity, int listViewId)
    {
        ListAdapter adapter1 = ListViewUtil.getAdapter(activity, listViewId);
        if (adapter1 instanceof BaseAdapter) {
            BaseAdapter baseAdapter = (BaseAdapter) adapter1;
            baseAdapter.notifyDataSetChanged();
        }
    }

    public static void setAdapter(Object activity, int listViewId, ListAdapter adapter)
    {
        ListView listView = (ListView) ViewUtil.findViewById(activity, listViewId);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }


    public static ListAdapter getAdapter(Object activity, int listViewId)
    {
        ListView listView = (ListView) ViewUtil.findViewById(activity, listViewId);
        if (listView != null) {
            return listView.getAdapter();
        }
        return null;
    }


    public static void setOnItemSelectedListener(Object view, int listViewId, AdapterView.OnItemSelectedListener listener)
    {
        ListView listView = (ListView) ViewUtil.findViewById(view, listViewId);
        if (listView != null) {
            listView.setOnItemSelectedListener(listener);
        }
    }
}
