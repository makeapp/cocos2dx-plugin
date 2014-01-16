/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.makeapp.android.util.HandlerUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-13 下午2:23 $
 *          $Id$
 */
public class ResizeLinearLayout extends LinearLayout
{
    OnResizeListener onResizeListener;

    public OnResizeListener getOnResizeListener()
    {
        return onResizeListener;
    }

    public void setOnResizeListener(OnResizeListener onResizeListener)
    {
        this.onResizeListener = onResizeListener;
    }

    public ResizeLinearLayout(Context context)
    {
        super(context);
    }

    public ResizeLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (onResizeListener != null) {
            HandlerUtil.postMain(new Runnable()
            {
                public void run()
                {
                    onResizeListener.onSizeChanged(w, h, oldw, oldh);

                }
            });
        }
    }

    public interface OnResizeListener
    {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }
}
