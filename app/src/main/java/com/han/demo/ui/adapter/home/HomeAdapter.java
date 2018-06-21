package com.han.demo.ui.adapter.home;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.han.demo.R;

import java.util.List;

/**
 * created by HanHongchang
 * 2018/6/19
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeItemInterface, HomeAdapter.HomeViewHolder> {
    public HomeAdapter(@Nullable List data) {
        super(data);
        addItemType(HomeItemInterface.ITEM_TITLE, R.layout.item_home_title);
        addItemType(HomeItemInterface.ITEM_HEADER, R.layout.item_home_header);
        addItemType(HomeItemInterface.ITEM_DEVICE,R.layout.item_cloud_device);
        addItemType(HomeItemInterface.ITEM_SERVICE,R.layout.item_cloud_service);
        addItemType(HomeItemInterface.ITEM_FOOTER,R.layout.item_home_footer);
    }

    @Override
    protected HomeViewHolder createBaseViewHolder(View itemView) {
        return new HomeViewHolder(itemView);
    }

    @Override
    protected void convert(HomeViewHolder helper, HomeItemInterface item) {

    }


    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

    }

    public static class HomeViewHolder extends BaseViewHolder {

        public HomeViewHolder(View view) {
            super(view);
        }
    }
}
