package com.han.base.utils;

import android.app.Dialog;
import android.content.Context;
import com.han.uikit.dialog.LoadingDialog;

/**
 * Created by hongchang on 2017/10/11.
 */

public class DialogUtils {

    public static Dialog showLoading(Context context) {
        return showLoading(context, "请稍后...");
    }

    public static Dialog showLoading(Context context, String message) {
        if (context == null)
            return null;
        final LoadingDialog hud = new LoadingDialog(context).setMessage(message);
        hud.setCancelable(false);
        hud.show();
        return hud;
    }

    public static Dialog showLoading(Context context, int seconds) {
        if (context == null)
            return null;
        String message = "请稍后...";
        final LoadingDialog hud = new LoadingDialog(context, seconds).setMessage(message);
        hud.setCancelable(false);
        hud.show();
        return hud;
    }

    public interface IOnClickListener {
        void onLeftClick();

        void onRightClick();
    }
}
