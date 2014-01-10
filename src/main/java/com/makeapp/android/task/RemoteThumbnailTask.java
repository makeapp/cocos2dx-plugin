/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import android.content.Context;
import android.widget.ImageView;
import com.makeapp.javase.http.HttpClient;
import com.makeapp.javase.lang.StringUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-31 下午2:17 $
 *          $Id$
 */
public class RemoteThumbnailTask extends RemoteImageTask
{
    private int width = 100;

    private int height = 100;

    public RemoteThumbnailTask(HttpClient httpClient, ImageView imageView)
    {
        super(httpClient, imageView);

//        width = imageView.getWidth() * 2;
//        height = imageView.getHeight() * 2;
    }

    public RemoteThumbnailTask(HttpClient httpClient, Context context)
    {
        super(httpClient, context);
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void download(String fileURL)
    {
        super.download(getThumbnailUrl(fileURL));
    }

    //http://192.168.1.11:8084/thumbnail/upload/2013/08/30/201308301248599.jpg?width=200
    private String getThumbnailUrl(String s)
    {
        if (StringUtil.isInvalid(s)) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        int pos = -1;
        if (s.startsWith("http://")) {
            pos = s.indexOf("/", "http://".length() + 1);
        }
        else if (s.startsWith("https://")) {
            pos = s.indexOf("/", "https://".length() + 1);
        }
        if (pos > 0) {
            buf.append(s.substring(0, pos + 1));
            buf.append("thumbnail");
            buf.append(s.substring(pos));
        }
        else {
            buf.append("/thumbnail");
            buf.append(s);
        }
        if (s.indexOf("?") > 0) {
            buf.append("&");
        }
        else {
            buf.append("?");
        }
        buf.append("width=").append(width);
        buf.append("&height=").append(height);

        return buf.toString();
    }
}
