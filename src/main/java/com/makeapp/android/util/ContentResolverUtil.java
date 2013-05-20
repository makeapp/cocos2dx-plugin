/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.net.URI;
import java.net.URISyntaxException;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-11-5 下午5:33 $
 *          $Id$
 */
public class ContentResolverUtil
{
    /**
     * 调用系统下载
     *
     * @param context
     * @param url
     * @param name    要保存的文件名
     */
    public static void download(Context context, String url, String name)
    {
        URI uri = null;
        try {
            uri = new URI(url);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put("uri", uri.toString());
        values.put("useragent", "Mozilla/5.0 (Linux; U; Android 1.5; en-us; sdk Build/CUPCAKE) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1");
        values.put("notificationpackage", context.getPackageName());
        values.put("notificationclass", "HelloWorld");
        values.put("visibility", 1);
        values.put("hint", name);
        values.put("description", uri.getHost());
        values.put("total_bytes", 1349528);

        ContentResolver mResolver = context.getContentResolver();
        mResolver.insert(Uri.parse("content://downloads/download"), values);
    }

}
