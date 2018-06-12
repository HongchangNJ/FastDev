package com.han.uikit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.han.uikit.R;

/**
 * Created by hongchang on 16/12/2.
 */

public class LoadingDialog extends Dialog {

    public final static long DIALOG_DISMISS_TIME = 10 * 1000;
    private int mImgResId = R.mipmap.loading_progress_bar;
    private String mMessage;

    private int mLayoutId;
    private CountTimer mCountTimer;


    public LoadingDialog(Context context, int style, int layout) {
        super(context, style);
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        Params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(Params);
        mLayoutId = layout;
        mCountTimer = new CountTimer(DIALOG_DISMISS_TIME, 15 * 1000);
    }

    public LoadingDialog(Context context, int style, int layout, int seconds) {
        super(context, style);
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        Params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(Params);
        mLayoutId = layout;
        mCountTimer = new CountTimer(DIALOG_DISMISS_TIME, seconds * 1000);
    }

    public LoadingDialog(Context context, int iconResId, String msg) {
        this(context, R.style.hud_dialog_style, R.layout.dialog_loading);
        setMessage(msg);
        setImageRes(iconResId);
    }

    public LoadingDialog(Context context) {
        this(context, R.style.hud_dialog_style, R.layout.dialog_loading);
    }

    public LoadingDialog(Context context, int seconds) {
        this(context, R.style.hud_dialog_style, R.layout.dialog_loading, seconds);
    }

    public LoadingDialog setMessage(String msg) {
        mMessage = msg;
        return this;
    }

    public LoadingDialog setImageRes(int resId) {
        mImgResId = resId;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        if (!TextUtils.isEmpty(mMessage)) {
            TextView message = (TextView) findViewById(R.id.hud_dialog_message);
            message.setVisibility(View.VISIBLE);
            message.setText(mMessage);
        }
    }

    @Override
    public void show() {
        super.show();
        mCountTimer.start();
    }

    public void cancelCounter() {
        if (isShowing()) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mCountTimer.cancel();
    }

    class CountTimer extends CountDownTimer {
        /**
         * 构造函数
         *
         * @param millisInFuture    倒计的时间数,以毫秒为单位
         * @param countDownInterval 倒计每秒中间的间隔时间,以毫秒为单位
         */
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 超时时，也要dismiss
            cancelCounter();
            cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }

}
