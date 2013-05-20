/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author <a href="mailto:yinhua@shqianzhi.com">yinhua</a>
 * @version $Date:11-4-29 ����5:13 $
 *          $Id$
 */
public class ProgressAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private int resourceId;

    public ProgressAdapter(Context context, int resourceId)
    {
        layoutInflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    public int getCount()
    {
        return 1;
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = layoutInflater.inflate(resourceId, null);
        }
        return convertView;
    }
}
