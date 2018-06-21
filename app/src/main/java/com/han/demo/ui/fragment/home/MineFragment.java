package com.han.demo.ui.fragment.home;

import android.os.Bundle;

import com.han.base.mvp.BaseFragment;
import com.han.base.mvp.BasePresenter;
import com.han.demo.R;

/**
 * created by HanHongchang
 * 2018/6/19
 */
public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void loadData() {

    }
}
