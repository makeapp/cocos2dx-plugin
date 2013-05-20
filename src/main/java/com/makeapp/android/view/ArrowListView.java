package com.makeapp.android.view;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-20
 * Time: 下午1:49
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * 支持上下箭头的ListView
 *
 * @author Mr. Lu
 */
public class ArrowListView extends ListView
{

    private final float scale = getContext().getResources().getDisplayMetrics().density;
    private float topArrowPadding;
    private float bottomArrowPadding;

    private static float DEFAULT_TOP_PADDING_DP = 2.0f;
    private static float DEFAULT_BOTTOM_PADDING_DP = 2.0f;

    private int resArrowUp;
    private int resArrowDown;

    public ArrowListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        String strTopArrowPadding = attrs.getAttributeValue(null,
                "topArrowPadding");
        String strBottomArrowPadding = attrs.getAttributeValue(null,
                "bottomArrowPadding");

        topArrowPadding = convertDisplayUom(strTopArrowPadding,
                DEFAULT_TOP_PADDING_DP);
        bottomArrowPadding = convertDisplayUom(strBottomArrowPadding,
                DEFAULT_BOTTOM_PADDING_DP);

        Log.v("ArrowListView", String.valueOf(topArrowPadding));
    }

    /**
     * 单位转换
     */
    private float convertDisplayUom(String sour, float defaultValue)
    {
        try {
            if (sour.toLowerCase().endsWith("px")) {
                return Float.parseFloat(sour.toLowerCase().replace("px", ""));
            } else if (sour.toLowerCase().endsWith("dp")) {
                return Integer.parseInt(sour.toLowerCase().replace("dp",
                        ""))
                        * scale + 0.5f;
            }
        } catch (Exception e) {
        }

        return (defaultValue * scale + 0.5f);
    }

    /**
     * onDraw方法，根据ListView滚动位置绘出箭头.
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();

        // 取得箭头的图片，此处是固定图片，其实上可以做成配置方式
        Bitmap topPic = ((BitmapDrawable) getResources().getDrawable(resArrowUp)).getBitmap();
        Bitmap bottomPic = ((BitmapDrawable) getResources().getDrawable(resArrowDown)).getBitmap();

        // 取得ListView的绘制区域大小
        Rect r = new Rect();
        this.getDrawingRect(r);

        // 计算箭头的绘制位置
        float top = r.top + topArrowPadding;
        float bottom = r.bottom - bottomArrowPadding - bottomPic.getHeight();
        float left = r.left + (r.right - r.left - topPic.getWidth()) / 2;

        // 计算是否需要绘制
        boolean drawTop = false;
        boolean drawBottom = false;

        if (this.getChildCount() > 0) {
            Rect rTop = new Rect();
            this.getChildAt(0).getLocalVisibleRect(rTop);
            Rect rBottom = new Rect();
            View lastChild = this.getChildAt(this.getChildCount() - 1);
            lastChild.getLocalVisibleRect(rBottom);

            drawTop = (this.getFirstVisiblePosition() > 0 || this
                    .getFirstVisiblePosition() == 0
                    && rTop.top > 0);
            drawBottom = (this.getLastVisiblePosition() < this.getAdapter()
                    .getCount() - 1 || this.getLastVisiblePosition() == this
                    .getAdapter().getCount() - 1
                    && rBottom.bottom < lastChild.getHeight());
        }
        // 绘出箭头
        if (drawTop) {
            canvas.drawBitmap(topPic, left, top, paint);
        }

        if (drawBottom) {
            canvas.drawBitmap(bottomPic, left, bottom, paint);
        }
    }
}
