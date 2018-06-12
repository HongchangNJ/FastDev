package com.han.base.mvp.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dotwin.framework.utils.ToastUtils;

import com.han.base.net.ApiConstant;
import com.han.base.net.Response;
import com.han.base.other.PermissionListener;
import com.han.base.utils.DialogUtils;
import com.han.demo.MainActivity;
import com.han.uikit.slideclose.SlidingLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;

/**
 * @author hongchang
 * @description: activity的基类
 * @date 2017/6/10  16:42
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    private static long mPreTime;
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static List<Activity> mActivities = new LinkedList<Activity>();
    protected Bundle savedInstanceState;

    public PermissionListener mPermissionListener;
    public Dialog mLoadingDialog;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // umeng
        //PushAgent.getInstance(this).onAppStart();

        if (enableSlideClose()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }

        this.savedInstanceState = savedInstanceState;

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPresenter = createPresenter();

        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }


    public boolean enableSlideClose() {
        return true;
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        // destory 之前，dismiss dialog，防止出现
        // “not attached to window manager”
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        super.onDestroy();

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public final void showSoftKeyboard(EditText editText) {
        WeakReference<EditText> weakReference = new WeakReference<EditText>(editText);
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(weakReference.get(), 0);
    }

    public final void hideSoftKeyboard(EditText editText) {
        WeakReference<EditText> weakReference = new WeakReference<EditText>(editText);
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(weakReference.get().getWindowToken(), 0);
    }

    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
//                UIUtils.showToast("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
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

    /**
     * 申请运行时权限
     */
    @TargetApi(23)
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            if (permissionListener != null) {
                permissionListener.onGranted();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }

                    if (mPermissionListener == null) {
                        return;
                    }
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

    /*---IBaseView-----*/

    /**
     * 确认对话框
     *
     * @param content
     */
    public void showDialog(String content) {
        //DialogUtils.showdTitleOneButton(this, content, "确定", null);
    }

    /**
     * 加载框
     */
    public void showLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = DialogUtils.showLoading(this);
    }

    /**
     * 加载框
     */
    public void showLoading(int seconds) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = DialogUtils.showLoading(this, seconds);
    }

    /**
     * 退出加载框
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    // 获取Context
    public Context getContext() {
        return this;
    }

    public void showToast(String msg) {
        ToastUtils.showShortToast(this, msg);
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
                //AccountManager.logout();
                content = "设备登录超时，请重新登录";
                break;
            default:
                String msg = response.msg;
                if (TextUtils.isEmpty(msg)) {
                    msg = "网络错误";
                }
                showToast(msg);
                return;
        }

        DialogUtils.IOnClickListener listener = new DialogUtils.IOnClickListener() {
            @Override
            public void onRightClick() {

            }

            @Override
            public void onLeftClick() {
                //LoginActivity.fastStartActivity(BaseActivity.this, true);
            }
        };

        // 退出登录（我的页面处理UI）
        //EventBus.getDefault().post(new LogoutEvent());
        //DialogUtils.showTitleTwoButton(this, content, "确定", "取消", listener);
    }
}
