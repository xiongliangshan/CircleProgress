package com.xls.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CircleProgressView extends View {

    private int mWidth;
    private int mHeigth;
    private Paint mPaint;
    private int mPercent;
    private Paint mTextPaint ;

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width= 100;
        int height = 100;
        //width
        if(widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode==MeasureSpec.AT_MOST){
            width = Math.min(widthSize,100);
        }else if(widthMode==MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }

        //height
        if(heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else if(heightMode==MeasureSpec.AT_MOST){
            height = Math.min(heightSize,100);
        }else if(heightMode==MeasureSpec.UNSPECIFIED){
            height = heightSize;
        }

        setMeasuredDimension(width,height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int padingRight = getPaddingRight();
        int padingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = mWidth-paddingLeft-padingRight;
        int height = mHeigth-padingTop-paddingBottom;
        int radius = Math.min(height,width)/2;
        canvas.translate(mWidth/2,mHeigth/2);
        RectF rectF = new RectF(-radius,-radius,radius,radius);
        mPaint.setColor(Color.LTGRAY);
        canvas.drawOval(rectF,mPaint);
        String per = mPercent+"%";
        float textWidth = mTextPaint.measureText(per);
        canvas.drawText(per,-textWidth/2,0,mTextPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(rectF,0,360*mPercent/100,false,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public int getPercent() {
        return mPercent;
    }

    public void setPercent(int mPercent) {
        this.mPercent = mPercent;
        postInvalidate();
    }
}
