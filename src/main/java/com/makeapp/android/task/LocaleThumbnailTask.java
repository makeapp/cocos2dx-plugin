/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.makeapp.android.util.AndroidUtil;
import com.makeapp.android.util.BitmapUtil;
import com.makeapp.javase.file.FileUtil;
import com.makeapp.javase.lang.ArrayUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-9-2 上午10:01 $
 *          $Id$
 */
public class LocaleThumbnailTask extends LocaleImageTask
{
    private Context context;

    public LocaleThumbnailTask(Context context, ImageView imageView)
    {
        super(imageView);
        this.context = context;
    }

    public LocaleThumbnailTask(Context context)
    {
        super(null);
        this.context = context;
    }

    public LocaleThumbnailTask(ImageView imageView)
    {
        super(imageView);
    }

    private int imageWidth = -1;

    public int getImageWidth()
    {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth)
    {
        this.imageWidth = imageWidth;
    }

    private int imageHeight = -1;

    public int getImageHeight()
    {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight)
    {
        this.imageHeight = imageHeight;
    }

    public void show(String path)
    {
        File imageDir = AndroidUtil.getApplicationThumbnailDir(context);
        File thumbnail = new File(imageDir, FileUtil.getCacheName(path));
        if (thumbnail.exists()) {
            super.show(thumbnail.getAbsolutePath());
        }
        else {
            super.show(path);
        }
    }


    public Bitmap getBitmap(String path)
    {
        return BitmapUtil.getThumbnails(path, imageWidth, imageHeight);
    }

    protected Bitmap doInBackground(String... params)
    {
        if (ArrayUtil.isInvalid(params)) {
            return null;
        }

        String filePath = params[0];

        File file = new File(filePath);
        if (file.exists()) {
            File imageDir = AndroidUtil.getApplicationThumbnailDir(context);
            if (file.getAbsolutePath().startsWith(imageDir.getAbsolutePath())) {
                return super.doInBackground(file.getAbsolutePath());
            }
            else {
                File thumbnail = new File(imageDir, FileUtil.getCacheName(filePath));
                String extName = FileUtil.getExtFromName(thumbnail);
                if (thumbnail.exists()) {
                    return super.doInBackground(thumbnail.getAbsolutePath());
                }
                else {
                    Bitmap bitmap = super.doInBackground(params);
                    if (imageWidth != -1 && imageHeight != -1) {
                        bitmap = BitmapUtil.resizeFitBitmap(bitmap, imageWidth, imageHeight);
                    }
                    else if (imageWidth != -1 || imageHeight != -1) {
                        bitmap = BitmapUtil.resizeBitmap(bitmap, imageWidth, imageHeight);
                    }
                    if ("PNG".equalsIgnoreCase(extName)) {
                        BitmapUtil.saveBitmap2Png(bitmap, thumbnail.getAbsolutePath());
                    }
                    else {
                        BitmapUtil.saveBitmap2Jpeg(bitmap, thumbnail.getAbsolutePath());
                    }
                    return bitmap;
                }
            }
        }
        return null;
    }
}
