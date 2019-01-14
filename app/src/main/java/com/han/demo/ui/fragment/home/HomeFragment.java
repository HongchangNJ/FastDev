package com.han.demo.ui.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.han.base.mvp.BaseFragment;
import com.han.base.mvp.BasePresenter;
import com.han.demo.R;
import com.han.demo.ui.adapter.home.BannerItem;
import com.han.demo.ui.adapter.home.HomeAdapter;
import com.han.demo.ui.adapter.home.HomeItemInterface;
import com.han.demo.ui.adapter.home.TestAdapter;
import com.han.demo.ui.adapter.home.TitleItem;
import com.han.uikit.refresh.BGADotwinRefreshViewHolder;
import com.han.uikit.refresh.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * created by HanHongchang
 * 2018/6/19
 */
public class HomeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @Bind(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;


    private List<HomeItemInterface> mDataList = new ArrayList<>();
    private HomeAdapter mAdapter;

    private TestAdapter mTestAdapter;
    private List<String> mTestData = new ArrayList<>();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void initData() {
        super.initData();
        mTestData.add("Banner");
        mTestData.add("Title");
        mTestAdapter.notifyDataSetChanged();
    }

    // 刷新控件初始化
    private void initRefreshLayout() {
        BGADotwinRefreshViewHolder viewHolder = new BGADotwinRefreshViewHolder(getActivity(), false);
        mRefreshLayout.setRefreshViewHolder(viewHolder);
        mRefreshLayout.setDelegate(this);
    }

    // recyclerview 初始化
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        /*mAdapter = new HomeAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);*/

        mTestAdapter = new TestAdapter(mTestData);
        mRecyclerView.setAdapter(mTestAdapter);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
