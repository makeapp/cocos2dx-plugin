package com.makeapp.android.view.book;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BookAdapter implements IAdapter
{
    private List<String> strList = new ArrayList<String>();

    private Context mContext;

    public BookAdapter(Context context)
    {
        super();
        this.mContext = context;
    }

    public void addItem(List<String> list)
    {
        strList.addAll(list);
    }

    public int getCount()
    {
        return strList.size();
    }

    public String getItem(int position)
    {
        return strList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position)
    {
        TextView textView = new TextView(mContext);
        textView.setText(strList.get(position));
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
//        textView.setBackgroundResource(R.drawable.ly);
        textView.setPadding(10, 10, 10, 10);
        textView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        return textView;
    }

}
