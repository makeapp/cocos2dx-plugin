/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-9 上午11:55 $
 *          $Id$
 */
public abstract class LoopAdapter
    extends BaseAdapter
{
    private int count = 0;

    public LoopAdapter(int count)
    {
        this.count = count;
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
        return 0;
    }

    public abstract View makeView(int position);

    public abstract void fillView(ViewGroup parent, View view, int position);

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = makeView(position);
        }
        fillView(parent, convertView, position);
        return convertView;
    }
}
