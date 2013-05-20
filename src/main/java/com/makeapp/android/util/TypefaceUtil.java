/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.graphics.Typeface;
import android.content.Context;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-10-25 下午3:57 $
 *          $Id$
 */
public class TypefaceUtil
{
    /**
     *  获取字体
     * @param context
     * @param fontPath  字体文件存放目录  以 assets 为根目录
     * @return
     */
    public static Typeface getTypeface(Context context,String fontPath)
    {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
}
