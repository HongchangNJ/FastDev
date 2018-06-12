package com.han.uikit.slideclose;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.han.uikit.R;

public class SlidingLayout extends FrameLayout {
    private static final int SHADOW_WIDTH = 16;
    private Activity mActivity;
    private Scroller mScroller;
    private Drawable mLeftShadow;
    private int mShadowWidth;
    private int mInterceptDownX;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mTouchDownX;
    private int mLastTouchX;
    private int mLastTouchY;
    private boolean isConsumed;

    private int mTopEdge;

    public SlidingLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    public SlidingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isConsumed = false;
        this.initView(context);
    }

    private void initView(Context context) {
        this.mScroller = new Scroller(context);
        this.mLeftShadow = this.getResources().getDrawable(R.drawable.left_shadow);
        int density = (int)this.getResources().getDisplayMetrics().density;
        this.mShadowWidth = 16 * density;
        this.mTopEdge = 65 * density;
    }

    public void bindActivity(Activity activity) {
        this.mActivity = activity;
        ViewGroup decorView = (ViewGroup)this.mActivity.getWindow().getDecorView();
        View child = decorView.getChildAt(0);
        decorView.removeView(child);
        this.addView(child);
        decorView.addView(this);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        switch(ev.getAction()) {
            case 0:
                intercept = false;
                this.mInterceptDownX = x;
                this.mLastInterceptX = x;
                this.mLastInterceptY = y;
                break;
            case 1:
                intercept = false;
                this.mInterceptDownX = this.mLastInterceptX = this.mLastInterceptY = 0;
                break;
            case 2:
                int deltaX = x - this.mLastInterceptX;
                int deltaY = y - this.mLastInterceptY;
                if(this.mInterceptDownX < this.getWidth() / 10 && Math.abs(deltaX) > Math.abs(deltaY)
                        && this.mLastInterceptY > mTopEdge) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                this.mLastInterceptX = x;
                this.mLastInterceptY = y;
        }

        return intercept;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        switch(ev.getAction()) {
            case 0:
                this.mTouchDownX = x;
                this.mLastTouchX = x;
                this.mLastTouchY = y;
                break;
            case 1:
                this.isConsumed = false;
                this.mTouchDownX = this.mLastTouchX = this.mLastTouchY = 0;
                if(-this.getScrollX() < this.getWidth() / 2) {
                    this.scrollBack();
                } else {
                    this.scrollClose();
                }
                break;
            case 2:
                int deltaX = x - this.mLastTouchX;
                int deltaY = y - this.mLastTouchY;
                if(!this.isConsumed && this.mTouchDownX < this.getWidth() / 10 && Math.abs(deltaX) > Math.abs(deltaY)) {
                    this.isConsumed = true;
                }

                if(this.isConsumed) {
                    int rightMovedX = this.mLastTouchX - (int)ev.getX();
                    if(this.getScrollX() + rightMovedX >= 0) {
                        this.scrollTo(0, 0);
                    } else {
                        this.scrollBy(rightMovedX, 0);
                    }
                }

                this.mLastTouchX = x;
                this.mLastTouchY = y;
        }

        return true;
    }

    private void scrollBack() {
        int startX = this.getScrollX();
        int dx = -this.getScrollX();
        this.mScroller.startScroll(startX, 0, dx, 0, 300);
        this.invalidate();
    }

    private void scrollClose() {
        int startX = this.getScrollX();
        int dx = -this.getScrollX() - this.getWidth();
        this.mScroller.startScroll(startX, 0, dx, 0, 300);
        this.invalidate();
    }

    public void computeScroll() {
        if(this.mScroller.computeScrollOffset()) {
            this.scrollTo(this.mScroller.getCurrX(), 0);
            this.postInvalidate();
        } else if(-this.getScrollX() >= this.getWidth()) {
            this.mActivity.finish();
        }

    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.drawShadow(canvas);
    }

    private void drawShadow(Canvas canvas) {
        this.mLeftShadow.setBounds(0, 0, this.mShadowWidth, this.getHeight());
        canvas.save();
        canvas.translate((float)(-this.mShadowWidth), 0.0F);
        this.mLeftShadow.draw(canvas);
        canvas.restore();
    }
}
