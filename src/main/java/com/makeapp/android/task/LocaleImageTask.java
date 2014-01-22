/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.makeapp.android.image.ImageListener;
import com.makeapp.android.util.BitmapUtil;
import com.makeapp.javase.lang.ArrayUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-9-2 上午9:51 $
 *          $Id$
 */
public class LocaleImageTask
    extends AsyncTask<String, Integer, Bitmap>
{
    private static final HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>(10 / 2, 0.75f);

    ImageView imageView = null;

    public LocaleImageTask(ImageView imageView)
    {
        this.imageView = imageView;
    }

    private Bitmap bitmap;

    private ImageListener imageListener;

    public ImageListener getImageListener()
    {
        return imageListener;
    }

    public void setImageListener(ImageListener imageListener)
    {
        this.imageListener = imageListener;
    }

    private boolean cacheable = true;

    public boolean isCacheable()
    {
        return cacheable;
    }

    public void setCacheable(boolean cacheable)
    {
        this.cacheable = cacheable;
    }

    public void show(String path)
    {
        if (bitmapCache.containsKey(path) && cacheable) {
            bitmap = bitmapCache.get(path);
            Log.d("FileDownload", "cache get " + path);
            onPostExecute(bitmap);
        }
        else {
            execute(path);
        }
    }


    protected Bitmap doInBackground(String... params)
    {
        if (ArrayUtil.isInvalid(params)) {
            return null;
        }

        if (bitmapCache.containsKey(params[0]) && cacheable) {
            bitmap = bitmapCache.get(params[0]);
            Log.d("LocalImage", "cache get " + params[0]);
            onPostExecute(bitmap);
        }
        else {
            bitmap = getBitmap(params[0]);
            bitmapCache.put(params[0], bitmap);
        }

        return bitmap;
    }

    public Bitmap getBitmap(String path)
    {
        return BitmapUtil.getThumbnails(path);
    }

    protected void onPostExecute(Bitmap bitmap)
    {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            if (imageListener != null) {
                imageListener.onLoaded(bitmap);
            }
            if (imageView != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
