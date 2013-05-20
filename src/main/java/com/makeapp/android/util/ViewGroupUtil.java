/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @author <a href="mailto:yinhua@shqianzhi.com">yinhua</a>
 * @version $Date:11-11-8 上午11:49 $
 *          $Id$
 */
public class ViewGroupUtil
{
    /**
     * 指定context，克隆LayoutInflater
     *
     * @param activity 本身Activity
     * @param context  指定的Context
     * @return LayoutInflater
     */
    public static LayoutInflater cloneInContext(final Activity activity, final Context context)
    {
        LayoutInflater lif = activity.getLayoutInflater().cloneInContext(new ContextWrapper(context)
        {
            @Override
            public ClassLoader getClassLoader()
            {
                return activity.getClass().getClassLoader();
            }
        });
        return lif;
    }
}
