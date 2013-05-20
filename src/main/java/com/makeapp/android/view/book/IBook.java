package com.makeapp.android.view.book;

import android.view.View;

public interface IBook
{
    public View getView(int position);

    public int getCount();
}
