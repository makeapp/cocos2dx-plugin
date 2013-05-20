/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-5-10 下午3:58 $
 *          $Id$
 */
public class AnimationUtil
{
    /**
     * 设置一个 旋转动画
     *
     * @param context
     * @param animId
     * @param imageId
     */
    public static void setAnimation(Activity context, int animId, int imageId)
    {
        Animation anim = AnimationUtils.loadAnimation(context, animId);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
        context.findViewById(imageId).startAnimation(anim);
    }
}
