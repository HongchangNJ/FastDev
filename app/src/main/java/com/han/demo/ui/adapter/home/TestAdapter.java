package com.han.demo.ui.adapter.home;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.han.demo.R;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestAdapter(@Nullable List data) {
        super(R.layout.item_home_title, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.title_tv, item);
    }
}
