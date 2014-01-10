/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-9 上午10:51 $
 *          $Id$
 */
public class DrawableSwitch extends Drawable
{
    Drawable drawable1;
    Drawable drawable2;

    public Drawable getDrawable2()
    {
        return drawable2;
    }

    public void setDrawable2(Drawable drawable2)
    {
        this.drawable2 = drawable2;
    }

    public Drawable getDrawable1()
    {
        return drawable1;
    }

    public void setDrawable1(Drawable drawable1)
    {
        this.drawable1 = drawable1;
    }

    @Override
    public void draw(Canvas canvas)
    {
        if (drawable2 != null) {
//            setBounds(drawable2.getBounds());
            drawable2.draw(canvas);
        }
        else if (drawable1 != null) {
//            setBounds(drawable1.getBounds());
            drawable1.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha)
    {

    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {

    }

    @Override
    public int getOpacity()
    {
        return 0;
    }
}
