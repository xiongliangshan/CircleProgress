package com.xls.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/7/17.
 */

public class CircleProgressView extends View {

    private Paint mPaint;
    private Paint mBgArcPaint;
    private float mValue;
    private float mMaxValue;
    private float mStartAngle;
    private long mAnimatorTime;
    private float mCenterTextSize;
    private float mCenterTextHeightOffset;
    private float mPercent;
    private Paint mTextPaint ;
    private float mArcWidth;
    DecimalFormat df;
    //属性动画
    private ValueAnimator mAnimator;

    private Point mCenterPoint;
    private float mRadius;
    private RectF mRect;


    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        init();
        setValue(mValue);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressView);
        mMaxValue = a.getFloat(R.styleable.CircleProgressView_maxValue,100);
        mStartAngle = a.getFloat(R.styleable.CircleProgressView_startAngle,0);
        mArcWidth = a.getDimension(R.styleable.CircleProgressView_arcWidth,1);
        mAnimatorTime = a.getInteger(R.styleable.CircleProgressView_animatorTime,500);
        mCenterTextSize = a.getDimension(R.styleable.CircleProgressView_centerTextSize,20);
        a.recycle();
    }


    private void init(){
        mAnimator = new ValueAnimator();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setColor(Color.RED);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mCenterTextSize);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBgArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgArcPaint.setColor(Color.LTGRAY);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mArcWidth);

        mRect = new RectF();
        mCenterPoint = new Point();
        df  = new DecimalFormat("#");
        mPercent = 0f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultSize = 160;
        int width = defaultSize;
        int height = defaultSize;
        //width
        if(widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode==MeasureSpec.AT_MOST){
            width = Math.min(widthSize,defaultSize);
        }else if(widthMode==MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }

        //height
        if(heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else if(heightMode==MeasureSpec.AT_MOST){
            height = Math.min(heightSize,defaultSize);
        }else if(heightMode==MeasureSpec.UNSPECIFIED){
            height = heightSize;
        }

        setMeasuredDimension(width,height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);

        float circleW = w-getPaddingLeft()-getPaddingRight()-2*mArcWidth;
        float circleH = h-getPaddingTop()-getPaddingBottom()-2*mArcWidth;
        mRadius = Math.min(circleH,circleW)/2;
        mCenterPoint.x = w/2;
        mCenterPoint.y = h/2;
        mRect.left = mCenterPoint.x-mRadius-mArcWidth/2;
        mRect.top = mCenterPoint.y-mRadius-mArcWidth/2;
        mRect.right = mCenterPoint.x+mRadius+mArcWidth/2;
        mRect.bottom = mCenterPoint.y+mRadius+mArcWidth/2;

        mCenterTextHeightOffset = mCenterPoint.y + measureTextHeight(mTextPaint)/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawArc(canvas);
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        float currentAngle = 360*mPercent;
        canvas.drawArc(mRect, mStartAngle+currentAngle, 360 - currentAngle , false, mBgArcPaint);

        canvas.drawArc(mRect,mStartAngle,currentAngle,false,mPaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        String text = df.format(mValue*100/mMaxValue)+"%";
        canvas.drawText(text,mCenterPoint.x,mCenterTextHeightOffset,mTextPaint);
    }

    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                mValue = mMaxValue*mPercent;
                invalidate();
            }
        });
        mAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        if(mValue>mMaxValue){
            mValue = mMaxValue;
        }
        if(mValue<0){
            mValue=0;
        }
        float start = mPercent;
        float end = value / mMaxValue;
        startAnimator(start, end, mAnimatorTime);
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    /**
     * 重置
     */
    public void reset() {
        startAnimator(mPercent, 0.0f, 1000L);
    }

    public long getAnimatorTime() {
        return mAnimatorTime;
    }

    public void setAnimatorTime(long mAnimatorTime) {
        this.mAnimatorTime = mAnimatorTime;
    }

    /**
     * 测量文字高度
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
    }
}
