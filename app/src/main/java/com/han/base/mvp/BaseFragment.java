package com.han.base.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.han.framework.utils.ToastUtils;
import com.han.base.net.ApiConstant;
import com.han.base.net.Response;
import com.han.base.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author hongchang
 * @description: Fragment的基类
 * @date 2017/6/10  17:09
 */

public abstract class BaseFragment<T extends BasePresenter> extends LazyLoadFragment {

    protected T mPresenter;
    private View rootView;
    protected Activity mActivity;
    public Dialog mLoadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getContentViewId(), container, false);
            ButterKnife.bind(this, rootView);

            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    /**
     * 初始化一些view
     *
     * @param rootView
     */
    public void initView(View rootView) {
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }


    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int getContentViewId();

    //加载数据
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        // destory 之前，dismiss dialog，防止出现
        // “not attached to window manager”
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    /*-------------  IBaseView ----------------*/
    public void showDialog(String content) {
        //DialogUtils.showdTitleOneButton(getContext(), content, "确定", null);
    }

    /**
     * 加载框
     */
    public void showLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = DialogUtils.showLoading(getContext());
    }

    /**
     * 隐藏加载框
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        ToastUtils.showLongToast(getContext(), msg);
    }

    // 超时，多设备登录处理
    public void onTokenError(Response response) {
        hideLoading();
        int result = response.result;
        String content = null;
        switch (result) {
            case ApiConstant.MULTI_DEVICE_CODE:
                content = "您的账号在别的设备登录，请重新登录";
                //AccountManager.logout();
                break;
            case ApiConstant.EXPIRE_CODE:
                content = "设备登录超时，请重新登录";
                //AccountManager.logout();
                break;
            default:
                String msg = response.msg;
                if (TextUtils.isEmpty(msg)) {
                    msg = "网络错误";
                }
                //mStateView.showRetry();
                showToast(msg);
                return;
        }

        DialogUtils.IOnClickListener listener = new DialogUtils.IOnClickListener() {
            @Override
            public void onRightClick() {

            }

            @Override
            public void onLeftClick() {
                //LoginActivity.fastStartActivity(getActivity(), true);
            }
        };
        //DialogUtils.showTitleTwoButton(getActivity(), content, "确定", "取消", listener);
    }

}
