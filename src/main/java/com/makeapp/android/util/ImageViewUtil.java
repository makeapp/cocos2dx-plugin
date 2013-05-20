/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-13 10:31 $
 *          $Id$
 */
public class ImageViewUtil extends ViewUtil
{
    static ImageDownloader downloader = new ImageDownloader();

    public static ImageView setImageSrcUrl(Object view, int imgId, String imgUrl)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        setImageSrcUrl(imageView, imgUrl);
        return imageView;
    }

    public static void setImageSrcUrl(ImageView imageView, String url)
    {
        if (imageView != null && url != null) {
            downloader.download(url, imageView);
        }
    }

    /**
     * @param imgId
     * @param imgSrcId
     */
    public static ImageView setImageSrcId(Object view, int imgId, int imgSrcId)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
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
        imageView.setImageURI(uri);
        return imageView;
    }

    public static ImageView setImageBitmap(Object view, int imgId, Bitmap bitmap)
    {
        ImageView imageView = (ImageView) ViewUtil.findViewById(view, imgId);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

}
