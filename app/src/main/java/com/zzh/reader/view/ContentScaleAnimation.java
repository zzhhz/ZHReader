package com.zzh.reader.view;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by jayce on 15-2-4.
 */
//public class ContentScaleAnimation extends Animation {
//    private final float mFromX;
//    private final float mToX;
//    private final float mFromY;
//    private final float mToY;
//    private float mPivotX;
//    private float mPivotY;
//    private float mPivotXValue;
//    private float mPivotYValue;
//    private boolean mReverse;
//
//    public ContentScaleAnimation(float mFromX, float mToX, float mFromY, float mToY, float mPivotXValue, float mPivotYValue, boolean mReverse) {
//        this.mFromX = mFromX;
//        this.mToX = mToX;
//        this.mFromY = mFromY;
//        this.mToY = mToY;
//        this.mPivotXValue = mPivotXValue;
//        this.mPivotYValue = mPivotYValue;
//        this.mReverse = mReverse;
//    }
//
//    @Override
//    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        float sx = 1.0f;
//        float sy = 1.0f;
//        if (mFromX != 1.0f || mToX != 1.0f) {
//            sx = mReverse ? mToX + (mFromX - mToX) * interpolatedTime : mFromX + (mToX - mFromX) * interpolatedTime;
//        }
//        if (mFromY != 1.0f || mToY != 1.0f) {
//            sy = mReverse ? mToY + (mFromY - mToY) * interpolatedTime : mFromY + (mToY - mFromY) * interpolatedTime;
//        }
//
//
//        if (mPivotX == 0 && mPivotY == 0) {
//            t.getMatrix().postScale(sx, sy);
//        } else {
//            t.getMatrix().postScale(sx, sy, mPivotXValue-mPivotX, mPivotYValue-mPivotY);
//        }
//    }
//
//    @Override
//    public void initialize(int width, int height, int parentWidth, int parentHeight) {
//        super.initialize(width, height, parentWidth, parentHeight);
//        mPivotX = resolvePivotX(mPivotXValue, parentWidth, width);
//        mPivotY = resolvePivoY(mPivotYValue, parentHeight, height);
//    }
//
//    private float resolvePivotX(float margingLeft, int parentWidth, int width) {
//        return (margingLeft * parentWidth) / (parentWidth - width);
//    }
//
//    private float resolvePivoY(float marginTop, int parentHeight, int height) {
//
//        return (marginTop * parentHeight) / (parentHeight - height);
//    }
//    public void reverse() {
//        mReverse = !mReverse;
//    }
//
//    public boolean getMReverse() {
//        return mReverse;
//    }
//}

public class ContentScaleAnimation extends Animation {
    private float mPivotX;
    private float mPivotY;
    private float mPivotXValue;
    private float mPivotYValue;
    private final float scaleTimes;
    private boolean mReverse;



    public ContentScaleAnimation(float mPivotXValue, float mPivotYValue, float scaleTimes, boolean mReverse) {

        this.mPivotXValue = mPivotXValue;
        this.mPivotYValue = mPivotYValue;
        this.scaleTimes=scaleTimes;
        this.mReverse = mReverse;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix=t.getMatrix();
        if (mReverse) {
            matrix.postScale(1 + (scaleTimes - 1) * (1.0f - interpolatedTime), 1 + (scaleTimes - 1) * (1.0f - interpolatedTime),mPivotX-mPivotXValue,mPivotY-mPivotYValue);
        } else {
            //matrix.postScale(1 + (scaleTimes - 1) * interpolatedTime, 1 + (scaleTimes - 1) * interpolatedTime, mPivotX, mPivotY);
            matrix.postScale(1 + (scaleTimes - 1) * interpolatedTime, 1 + (scaleTimes - 1) * interpolatedTime,mPivotX-mPivotXValue,mPivotY-mPivotYValue);
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mPivotX = resolvePivotX(mPivotXValue, parentWidth, width);
        mPivotY = resolvePivoY(mPivotYValue, parentHeight, height);
    }

    private float resolvePivotX(float margingLeft, int parentWidth, int width) {
        return (margingLeft * parentWidth) / (parentWidth - width);
    }

    private float resolvePivoY(float marginTop, int parentHeight, int height) {

        return (marginTop * parentHeight) / (parentHeight - height);
    }
    public void reverse() {
        mReverse = !mReverse;
    }

    public boolean getMReverse() {
        return mReverse;
    }
}
