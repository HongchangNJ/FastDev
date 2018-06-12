package com.han.uikit.refresh;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.han.uikit.R;

/**
 * Created by hongchang on 2017/11/14.
 */

public class BGADotwinRefreshViewHolder extends BGARefreshViewHolder {

    private ImageView mPullDownImageView;
    private ImageView mRefreshingImageView;



    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGADotwinRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = LayoutInflater.from(mContext).inflate(R.layout.view_refresh_header_dotwin, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);

            mPullDownImageView = (ImageView) mRefreshHeaderView.findViewById(R.id.refresh_circle);
            mRefreshingImageView = (ImageView) mRefreshHeaderView.findViewById(R.id.refreshing_imageview);
        }

        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

    }

    @Override
    public void changeToIdle() {
        mPullDownImageView.setVisibility(View.VISIBLE);
        mRefreshingImageView.setVisibility(View.INVISIBLE);
        mRefreshingImageView.setImageDrawable(null);
    }

    @Override
    public void changeToPullDown() {
        /*mPullDownImageView.setVisibility(View.VISIBLE);
        mRefreshingImageView.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public void changeToReleaseRefresh() {
/*        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 0.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        AnimationSet animationSet = new AnimationSet(mContext, null);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(1000);
        animationSet.setFillAfter(false);

        mPullDownImageView.startAnimation(animationSet);*/
    }

    @Override
    public void changeToRefreshing() {
        mPullDownImageView.setVisibility(View.INVISIBLE);
        mRefreshingImageView.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .asGif()
                .load(R.mipmap.refreshing_image)
                .into(mRefreshingImageView);
    }

    @Override
    public void onEndRefreshing() {
        //mPullDownImageView.setImageResource(R.mipmap.refresh_image);
    }
}
