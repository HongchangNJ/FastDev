package com.han.uikit.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.han.uikit.utils.AppUtils;

/**
 * Created by 钟光新 on 2016/9/24 0024.
 */

public class IndicationTabLayout extends LinearLayout {
    public static final String TAG = "dotwin";

    //指示器
    private int mSelectedIndicatorHeight;
    private Paint mSelectedIndicatorPaint;
    private int mSelectedPosition = -1;
    private float mSelectionOffset;
    private int mIndicatorLeft = -1;
    private int mIndicatorRight = -1;

    private int mIndicatorWidth = 45;
    private int mIndicatorCorner = 3;

    public IndicationTabLayout(Context context) {
        this(context, null);
    }

    public IndicationTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicationTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        mSelectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setSelectedIndicatorColor(int color) {
        if (mSelectedIndicatorPaint.getColor() != color) {
            mSelectedIndicatorPaint.setColor(color);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setSelectedIndicatorHeight(int height) {
        if (mSelectedIndicatorHeight != height) {
            mSelectedIndicatorHeight = height;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        updateIndicatorPosition();
    }

    private void updateIndicatorPosition() {
        final View selectedTitle = getChildAt(mSelectedPosition);
        int left, right;

        if (selectedTitle != null && selectedTitle.getWidth() > 0) {
            int selectTextPadding = (int) ((selectedTitle.getWidth() - measureTextLength(selectedTitle)) / 2 + 0.5f);
            left = computeLeftPositionOfText(selectedTitle);
            right = computeRightPositionOfRight(selectedTitle);
            Log.d(TAG, "current, left: " + left + ", right: " + right);

            if (mSelectionOffset > 0f && mSelectedPosition < getChildCount() - 1) {
                View nextTitle = getChildAt(mSelectedPosition + 1);
                int textPadding = (int) ((nextTitle.getWidth() - measureTextLength(nextTitle)) / 2 + 0.5f);
                int moveLeft = computeLeftPositionOfText(nextTitle);
                int moveRight = computeRightPositionOfRight(nextTitle);
                Log.d(TAG, "next, moveleft: " + moveLeft + ", moveright: " + moveRight);
                left = (int) (left + (moveLeft - left) * mSelectionOffset);
                right = (int) (right + (moveRight - right) * mSelectionOffset);

                Log.d(TAG, "indicator, indicator left: " + left + ", indicator right: " + right);

            }
        } else {
            left = right = -1;
        }
        setIndicatorPosition(left, right);
    }

    private int computeLeftPositionOfText(View textTitle) {
        int left = textTitle.getLeft();
        int right = textTitle.getRight();

        if (right - left > mIndicatorWidth) {
            return left + (textTitle.getWidth() - mIndicatorWidth)/2;
        } else {
            return left;
        }
    }


    private int computeRightPositionOfRight(View textTitle) {
        int left = textTitle.getLeft();
        int right = textTitle.getRight();

        if (right - left > mIndicatorWidth) {
            return left + (textTitle.getWidth() - mIndicatorWidth)/2 + mIndicatorWidth;
        } else {
            return right;
        }
    }

    private void setIndicatorPosition(int left, int right) {
        if (left != mIndicatorLeft || right != mIndicatorRight) {
            mIndicatorLeft = left;
            mIndicatorRight = right;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private float measureTextLength(View measureView) {
        if (measureView instanceof TextView) {
            TextView textView = ((TextView) measureView);
            String text = textView.getText().toString();
            return textView.getPaint().measureText(text);
        }
        return 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {

            RectF oval = new RectF(mIndicatorLeft, getHeight() - mSelectedIndicatorHeight,
                    mIndicatorRight, getHeight());
            canvas.drawRoundRect(oval, AppUtils.dp2px(getContext(), mIndicatorCorner),
                    AppUtils.dp2px(getContext(), mIndicatorCorner), mSelectedIndicatorPaint);
        }
    }
}
