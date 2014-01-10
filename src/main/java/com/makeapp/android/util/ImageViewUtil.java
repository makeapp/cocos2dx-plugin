/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.widget.ImageView;
import com.makeapp.android.task.RemoteImageTask;
import com.makeapp.android.task.RemoteThumbnailTask;
import com.makeapp.javase.http.HttpClient;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-13 10:31 $
 *          $Id$
 */
public class ImageViewUtil extends ViewUtil
{
    public static ImageView setImageSrcUrl(Object view, int imgId, String imgUrl)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        setImageSrcUrl(imageView, imgUrl);
        return imageView;
    }

    public static void setImageSrcUrl(ImageView imageView, String url)
    {
        if (imageView != null && url != null) {
            RemoteImageTask task = new RemoteImageTask(null, imageView);
            task.download(url);
        }
    }

    public static ImageView setImageSrcUrl(HttpClient httpClient, Object view, int imgId, String imgUrl)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        setImageSrcUrl(httpClient, imageView, imgUrl);
        return imageView;
    }

    public static ImageView setImageSrcUrl(HttpClient httpClient, Object view, int imgId, String imgUrl, int defResId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        imageView.setImageResource(defResId);
        setImageSrcUrl(httpClient, imageView, imgUrl);
        return imageView;
    }

    public static void setImageSrcUrl(HttpClient httpClient, ImageView imageView, String url)
    {
        if (imageView != null && url != null) {
            RemoteImageTask task = new RemoteImageTask(httpClient, imageView);
            task.download(url);
        }
    }

    public static ImageView setThumbnailSrcUrl(HttpClient httpClient, Object view, int imgId, String imgUrl)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        setThumbnailSrcUrl(httpClient, imageView, imgUrl);
        return imageView;
    }

    public static ImageView setThumbnailSrcUrl(HttpClient httpClient, Object view, int imgId, String imgUrl, int defResId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setImageResource(defResId);
        setThumbnailSrcUrl(httpClient, imageView, imgUrl);
        return imageView;
    }

    public static void setThumbnailSrcUrl(HttpClient httpClient, ImageView imageView, String url)
    {
        if (imageView != null && url != null) {
            RemoteThumbnailTask task = new RemoteThumbnailTask(httpClient, imageView);
            task.download(url);
        }
    }

    /**
     * @param imgId
     * @param imgSrcId
     */
    public static ImageView setImageSrcId(Object view, int imgId, int imgSrcId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setImageResource(imgSrcId);
        return imageView;
    }

    /**
     * @param imgId
     * @param uri
     */
    public static ImageView setImageSrcUri(Object view, int imgId, Uri uri)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setImageURI(uri);
        return imageView;
    }

    public static ImageView setImageBitmap(Object view, int imgId, Bitmap bitmap)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    public static ImageView startAnimationDrawable(Object view, int imgId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }

        return imageView;
    }

    public static ImageView startAnimationDrawable(Object view, int imgId, int resId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        imageView.setImageResource(resId);
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }

        return imageView;
    }

    public static ImageView stopAnimationDrawable(Object view, int imgId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        if (imageView == null) {
            return null;
        }
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).stop();
        }

        return imageView;
    }

    public static ImageView startAnimationDrawable(ImageView imageView)
    {
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }

        return imageView;
    }

    public static ImageView stopAnimationDrawable(ImageView imageView)
    {
        if (imageView == null) {
            return null;
        }
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).stop();
        }

        return imageView;
    }

}
