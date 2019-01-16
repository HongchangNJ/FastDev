package com.han.demo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.han.base.mvp.BaseActivity;
import com.han.base.mvp.BaseFragment;
import com.han.base.mvp.BasePresenter;
import com.han.base.ui.adapter.MainTabAdapter;
import com.han.base.ui.view.bottomlayout.BottomBarLayout;
import com.han.demo.ui.fragment.home.ControlFragment;
import com.han.demo.ui.fragment.home.HomeFragment;
import com.han.demo.ui.fragment.home.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * created by HanHongchang
 * 2018/6/21
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.bottom_bar)
    BottomBarLayout mBottomBar;

    private HomeFragment mHomeFragment;
    private ControlFragment mControlFragment;
    private MineFragment mMineFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private MainTabAdapter mTabAdapter;

    public final static int RC_CAMERA_AND_LOCATION = 0;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        mHomeFragment = HomeFragment.newInstance();
        mControlFragment = ControlFragment.newInstance();
        mMineFragment = MineFragment.newInstance();

        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mControlFragment);
        mFragmentList.add(mMineFragment);

        mTabAdapter = new MainTabAdapter(mFragmentList, getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mBottomBar.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};

        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, RC_CAMERA_AND_LOCATION, perms)
                        .setRationale("")
                        .setPositiveButtonText("OK")
                        .setNegativeButtonText("cancel")
                        //.setTheme(R.style.my_fancy_style)
                        .build());*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /*@AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "相机和位置权限",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }*/

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
