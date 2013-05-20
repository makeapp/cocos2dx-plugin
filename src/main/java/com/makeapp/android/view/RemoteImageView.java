package com.makeapp.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.makeapp.android.task.AsyncTask;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-8-11
 * Time: ÏÂÎç4:59
 */
public class RemoteImageView extends ImageView
{

    public RemoteImageView(Context context)
    {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void load(String url)
    {

    }

    class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap>
    {
        protected Bitmap doInBackground(String... params)
        {
            return null;
        }
    }
}
