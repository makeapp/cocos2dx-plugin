/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.io.File;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.makeapp.android.image.ImageListener;
import com.makeapp.android.util.AndroidUtil;
import com.makeapp.android.util.BitmapUtil;
import com.makeapp.javase.http.HttpClient;
import com.makeapp.javase.lang.StringUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-31 下午2:17 $
 *          $Id$
 */
public class RemoteImageTask extends RemoteFileTask
{
    private static final HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>(10 / 2, 0.75f);

    Bitmap bitmap;

    Context context;

    ImageView imageView;

    ImageListener imageListener;

    public RemoteImageTask(HttpClient httpClient, Context context)
    {
        this.context = context;
        setHttpClient(httpClient);
        setOverwrite(false);
        setSavePath(AndroidUtil.getApplicationImageDir(context));
    }

    public RemoteImageTask(HttpClient httpClient, ImageView imageView)
    {
        this(httpClient, imageView.getContext());
        this.imageView = imageView;
    }

    public ImageListener getImageListener()
    {
        return imageListener;
    }

    public void setImageListener(ImageListener imageListener)
    {
        this.imageListener = imageListener;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void download(String fileURL)
    {
        if (bitmapCache.containsKey(fileURL)) {
            bitmap = bitmapCache.get(fileURL);
            Log.d("FileDownload", "cache get " + fileURL);
            onPostExecute(fileURL);
        }
        else {
            super.download(fileURL);
        }
    }

    public boolean doDownload(File localFile, int type)
    {
        if (type == 0) {
            return true;
        }
        else {
            return super.doDownload(localFile, type);
        }
    }

    protected String doInBackground(String... params)
    {
        bitmap = bitmapCache.get(params[0]);
        if (bitmap == null) {
            String downloadPath = super.doInBackground(params);
            bitmap = BitmapUtil.getThumbnails(downloadPath);
            if (bitmap != null) {
                bitmapCache.put(params[0], bitmap);
            }
            return downloadPath;
        }
        else {
            Log.d("FileDownload", "cache get " + params[0]);
        }
        return null;
    }

    protected void onPostExecute(String s)
    {
        if (imageListener != null && StringUtil.isValid(s)) {
            imageListener.onLoaded(getBitmap());
        }

        if (imageView != null && bitmap != null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
        }
    }
}
