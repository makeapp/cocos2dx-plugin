/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author <a href="mailto:yinhua@shqianzhi.com">yinhua</a>
 * @version $Date:11-4-22 ����5:58 $
 *          $Id$
 */
public abstract class ArrayListAdapter<T> extends ArrayAdapter
{
    LayoutInflater layoutInflater;
    int layoutId = 0;

    public ArrayListAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public ArrayListAdapter(Context context, int resource, int textViewResourceId)
    {
        super(context, resource, textViewResourceId);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public ArrayListAdapter(Context context, int textViewResourceId, Object[] objects)
    {
        super(context, textViewResourceId, objects);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public ArrayListAdapter(Context context, int resource, int textViewResourceId, Object[] objects)
    {
        super(context, resource, textViewResourceId, objects);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public ArrayListAdapter(Context context, int textViewResourceId, List objects)
    {
        super(context, textViewResourceId, objects);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public ArrayListAdapter(Context context, int resource, int textViewResourceId, List objects)
    {
        super(context, resource, textViewResourceId, objects);
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = inflateView(layoutInflater,parent,position);
        }

        T t = (T) getItem(position);

        fillView(parent, convertView, t, position);

        return convertView;
    }

    public View inflateView(LayoutInflater layoutInflater,ViewGroup parent, int position)
    {
        return layoutInflater.inflate(layoutId, null);
    }

    public abstract void fillView(ViewGroup parent, View view, T obj, int position);
}
