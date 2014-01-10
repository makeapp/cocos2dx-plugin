/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import com.makeapp.android.image.ImageListener;
import com.makeapp.android.task.LocaleThumbnailTask;
import com.makeapp.android.task.RemoteThumbnailTask;
import com.makeapp.android.view.DrawableSwitch;
import com.makeapp.javase.http.HttpClient;
import com.makeapp.javase.util.DataUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-9 下午7:43 $
 *          $Id$
 */
public class HtmlImageGetter
    implements Html.ImageGetter
{
    Context context = null;

    private String[] images;

    HttpClient client = null;

    int resWait = 0;

    private float scale;

    private int imageWidth = 300;

    private int imageHeight = 300;

    public HtmlImageGetter(Context context, String[] images, int resWait)
    {
        this.context = context;
        this.images = images;
        this.resWait = resWait;
    }

    public HttpClient getClient()
    {
        return client;
    }

    public void setClient(HttpClient client)
    {
        this.client = client;
    }

    private ImageListener imageListener;

    public ImageListener getImageListener()
    {
        return imageListener;
    }

    public void setImageListener(ImageListener imageListener)
    {
        this.imageListener = imageListener;
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public int getImageWidth()
    {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth)
    {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight()
    {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight)
    {
        this.imageHeight = imageHeight;
    }

    public Drawable getDrawable(String source)
    {
        if (source.startsWith("/drawable/")) {
            String resId = source.substring("/drawable/".length());
            Drawable d = ResourcesUtil.getDrawable(context, resId);
            if (d != null) {
                if (d instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) d;
                    d = new BitmapDrawable(BitmapUtil.resizeBitmap(bitmapDrawable.getBitmap(), 80, 80));
                }
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        }
        else if (source.startsWith("/")) {
            File file = new File(source);
            if (file.exists()) {
                return BitmapUtil.getThumbnailsDrawable(source);
            }
            else {
                return downloadDrawable(source);
            }
        }
        else if (source.startsWith("exp://")) {
            int attachIndex = DataUtil.getInt(source.substring("exp://".length()));
            int resId = ResourcesUtil.getIdentifier(context, "exp" + attachIndex, "drawable");
            Drawable d = null;
            try {
                d = context.getResources().getDrawable(resId);
            }
            catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            if (d != null) {
                if (scale > 0) {
                    d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * scale), (int) (d.getIntrinsicHeight() * scale));
                }
                else {
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                }
            }
            return d;
        }
        else if (source.startsWith("dexp://")) {
            int attachIndex = DataUtil.getInt(source.substring("dexp://".length()));
            int resId = ResourcesUtil.getIdentifier(context, "dexp" + attachIndex, "drawable");
            Drawable d = context.getResources().getDrawable(resId);
            return d;
        }
        else if (source.startsWith("attach://")) {
            int attachIndex = DataUtil.getInt(source.substring("attach://".length()));
            if (images != null && attachIndex < images.length) {
                String newSource = images[attachIndex];
                if (newSource.startsWith("http://")) {
                    return downloadDrawable(newSource);
                }
                else if (newSource.startsWith("/") || newSource.startsWith("file://")) {
                    return getLocalDrawable(newSource);
                }
            }
        }
        else if (source.startsWith("http://")) {
            return downloadDrawable(source);
        }
        return null;
    }

    public Drawable getLocalDrawable(String filePath)
    {
        final DrawableSwitch switchDrawable = new DrawableSwitch();
        switchDrawable.setBounds(0, 0, imageWidth, imageHeight);

        LocaleThumbnailTask task = new LocaleThumbnailTask(context)
        {
            protected void onPostExecute(Bitmap bitmap)
            {
                if (bitmap != null) {
                    Drawable bitmapDrawable = BitmapUtil.getBitmapDrawable(bitmap);

                    Rect rect = bitmapDrawable.getBounds();
                    bitmapDrawable.setBounds(0, 0, imageHeight * rect.right / rect.bottom, imageHeight);
                    switchDrawable.setBounds(0, 0, imageHeight * rect.right / rect.bottom, imageHeight);
                    switchDrawable.setDrawable2(bitmapDrawable);
                    super.onPostExecute(bitmap);
                }
            }
        };
        task.setImageListener(imageListener);
        task.setImageHeight(imageHeight);
//        task.setWidth(imageWidth);
//        task.setHeight(imageHeight);
        task.show(filePath);
        if (switchDrawable.getDrawable2() != null) {
            return switchDrawable.getDrawable2();
        }
        else {
            return switchDrawable;
        }
    }

    public Drawable downloadDrawable(final String filePath)
    {
        final DrawableSwitch switchDrawable = new DrawableSwitch();
        switchDrawable.setBounds(0, 0, imageWidth, imageHeight);

        RemoteThumbnailTask thumbnailDownTask = new RemoteThumbnailTask(client, context)
        {
            protected void onPostExecute(String s)
            {
                Drawable bitmapDrawable = BitmapUtil.getBitmapDrawable(getBitmap());
                if (bitmapDrawable == null) {
                    return;
                }
                Rect rect = bitmapDrawable.getBounds();

                bitmapDrawable.setBounds(0, 0, imageHeight * rect.right / rect.bottom, imageHeight);
                switchDrawable.setBounds(0, 0, imageHeight * rect.right / rect.bottom, imageHeight);
                switchDrawable.setDrawable2(bitmapDrawable);
                super.onPostExecute(s);
            }
        };
        thumbnailDownTask.setWidth(-1);
        thumbnailDownTask.setHeight(imageHeight);
        thumbnailDownTask.setImageListener(imageListener);
        thumbnailDownTask.download(filePath);
        Drawable drawable = context.getResources().getDrawable(resWait);
        switchDrawable.setDrawable1(drawable);
        if (switchDrawable.getDrawable2() != null) {
            return switchDrawable.getDrawable2();
        }
        else {
            return switchDrawable;
        }
    }
}
