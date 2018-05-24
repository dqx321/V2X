package com.intelligent.v2xapp.review;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.intelligent.v2xapp.R;

/**
 * Created by daiqinxue on 2018/2/5.
 */

public class MyButton extends View {

    private RectF rectF = new RectF();
    private Paint tipPaint;
    private int mWidth;
    private int mHeight;
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tipPaint=getPaint(2, getResources().getColor(R.color.color_black),Paint.Style.STROKE);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);



    }
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.set(0, 0, 100, 30);
        canvas.drawRoundRect(rectF, 15, 15, tipPaint);
    }
    /**
     * 统一处理paint
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }
}
