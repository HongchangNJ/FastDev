package com.han.base.net;
import android.text.TextUtils;
import rx.Subscriber;

/**
 * @author hongchang
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T extends Response> extends Subscriber<T> {

    @Override
    public void onNext(T response) {
        if (response.result != 1) {
            if (TextUtils.isEmpty(response.msg)) {
                response.msg = "网络错误,请重试";
            }
            onCodeError(response);
            return;
        }
        onSuccess(response);
    }

    @Override
    public void onCompleted() {
    }

    protected abstract void onSuccess(T response);

    protected abstract void onCodeError(Response response);

    @Override
    public void onError(Throwable e) {

    }
}
