/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-12 ����5:19 $
 *          $Id$
 */
public class GridViewUtil
{
    public static GridView setGridAdapter(Activity activity, Integer gridId, BaseAdapter baseAdapter)
    {
        GridView gridView = (GridView) activity.findViewById(gridId);
        gridView.setAdapter(baseAdapter);
        return gridView;
    }

    public static GridView setGridAdapterAndOnClick(Activity activity, int gridId, BaseAdapter baseAdapter, android.widget.AdapterView.OnItemClickListener listener)
    {
        GridView gridView = (GridView) activity.findViewById(gridId);
        System.out.println(gridView);
        if (baseAdapter != null) {
            gridView.setAdapter(baseAdapter);
        }
        if (listener != null) {
            gridView.setOnItemClickListener(listener);
        }
        return gridView;
    }
}
