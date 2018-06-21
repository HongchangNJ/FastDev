package com.han.demo;

import android.support.v4.view.ViewPager;

import com.chaychan.library.BottomBarLayout;
import com.han.base.mvp.BaseActivity;
import com.han.base.mvp.BaseFragment;
import com.han.base.mvp.BasePresenter;
import com.han.base.ui.adapter.MainTabAdapter;
import com.han.demo.ui.fragment.home.ControlFragment;
import com.han.demo.ui.fragment.home.HomeFragment;
import com.han.demo.ui.fragment.home.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * created by HanHongchang
 * 2018/6/21
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.bottom_bar)
    BottomBarLayout mBottomBar;

    private HomeFragment mHomeFragment;
    private ControlFragment mControlFragment;
    private MineFragment mMineFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private MainTabAdapter mTabAdapter;


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
}
