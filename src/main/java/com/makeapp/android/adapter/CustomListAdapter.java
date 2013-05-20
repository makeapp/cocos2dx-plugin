/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author <a href="mailto:yinhua@shqianzhi.com">yinhua</a>
 * @version $Date:11-4-22 ����5:58 $
 *          $Id$
 */

@Deprecated
public class CustomListAdapter extends BaseAdapter
{
    private List<?> items;
    private Context context;
    LayoutInflater layoutInflater;
    int layoutId = 0;

    private int count;
    CustomView customView;

    public void setCount(int count)
    {
        this.count = count;
    }

    public CustomListAdapter(Context context, List items, CustomView customView)
    {
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        if (count == 0) {
            count = items.size();
        }
        this.customView = customView;

    }

    public int getCount()
    {
        return count;
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        return customView.getView(convertView, items, position);
    }

    public interface CustomView
    {
        public View getView(View v, List items, int position);
    }
}
