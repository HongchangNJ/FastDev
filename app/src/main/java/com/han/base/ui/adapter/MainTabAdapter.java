package com.han.base.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;


import com.han.base.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongchang
 * @description: 首页页签的adapter
 * @date 2017/6/12  21:36
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {
    public final static String TAG_PRE = "fragment_tag";

    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public MainTabAdapter(List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        if (fragmentList != null) {
            mFragmentList = fragmentList;
        }
        mSupportFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mFragmentTransaction = mSupportFragmentManager.beginTransaction();
        //下面判断是否有该Fragment，如果有就直接Attach到Activity里面去，没有的话就通过getItem方法获取新的实例
        Fragment fragment = mSupportFragmentManager.findFragmentByTag(String.valueOf(position));
        if (fragment != null) {
            //transaction.attach(fragment);
            mFragmentTransaction.show(fragment);
        } else {
            fragment = getItem(position);
            mFragmentTransaction.add(container.getId(), fragment, String.valueOf(position));
        }

        mFragmentTransaction.commitAllowingStateLoss();
        return fragment;
    }
}
