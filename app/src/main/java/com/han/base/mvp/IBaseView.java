package com.han.base.mvp;

import android.content.Context;
import com.han.base.net.Response;

/**
 * Created by hongchang on 2017/10/12.
 */

public interface IBaseView {
    void showDialog(String content);

    void showLoading();


    void hideLoading();

    void onTokenError(Response response);

    Context getContext();

    void showToast(String msg);
}
