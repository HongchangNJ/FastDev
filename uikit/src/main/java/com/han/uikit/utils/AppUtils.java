package com.han.uikit.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by han on 2016/9/10 0010.
 */
public class AppUtils {
    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 将独立像素单位dp的尺寸 转换为 像素单位的尺寸
     * @param context
     * @param value dp value
     * @return
     */
    public static int dp2px(Context context, int value){
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5f);
    }

    public static int sp2px(Context context, int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,
                context.getResources().getDisplayMetrics());
    }
}
