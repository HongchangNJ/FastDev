package com.han.demo.ui.fragment.home;

import android.os.Bundle;

import com.han.base.mvp.BaseFragment;
import com.han.base.mvp.BasePresenter;
import com.han.demo.R;

/**
 * created by HanHongchang
 * 2018/6/19
 */
public class ControlFragment extends BaseFragment {

    public static ControlFragment newInstance() {

        Bundle args = new Bundle();

        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_control;
    }

    @Override
    protected void loadData() {

    }
}
