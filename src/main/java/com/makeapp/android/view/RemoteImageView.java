package com.makeapp.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-8-11
 * Time: ����4:59
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

    public void loadImage(String url)
    {
//        ImageDownTask task = new ImageDownTask()
//        {
//            protected void onPostExecute(String s)
//            {
//                if (getBitmap() != null) {
//                    setImageBitmap(getBitmap());
//                }
//            }
//        };
//        task.execute(url);
    }

    public void loadThumbnail(String url)
    {
//        ThumbnailDownTask task = new ThumbnailDownTask()
//        {
//            protected void onPostExecute(String s)
//            {
//                if (getBitmap() != null) {
//                    setImageBitmap(getBitmap());
//                }
//            }
//        };
//        task.execute(url);
    }
}
