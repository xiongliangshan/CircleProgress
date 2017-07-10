package com.xls.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class CircleProressView extends View {

    private static final String TAG  = "CircleProressView";
    private Paint mPaint;
    private int mColor;
    private List<PieData> mData;
    private int mStartAngle ;
    private int mWidth;
    private int mHeight;
    private int mRadius;


    public CircleProressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleProressView);
        mColor = a.getColor(R.styleable.CircleProressView_circleColor,Color.RED);
        mStartAngle = a.getInteger(R.styleable.CircleProressView_startAngle,0);
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mData==null || mData.size()==0){
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = mWidth-paddingLeft-paddingRight;
        int height = mHeight-paddingTop-paddingBottom;
        mRadius = Math.min(width,height)/2;
        canvas.translate(mWidth/2,mHeight/2);
        RectF rect = new RectF(-mRadius,-mRadius,mRadius,mRadius);
        for(PieData pie:mData){
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rect,mStartAngle,360*pie.getPercent(),true,mPaint);
            mStartAngle+=360*pie.getPercent();
        }



    }


    public void setmStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    public void setmData(List<PieData> mData) {
        this.mData = mData;
        invalidate();
    }

    public static class PieData{
        private String name;
        private float percent;
        private int color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }


}
