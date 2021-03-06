package com.wb.ygq.ui.fm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.HomeVideoBean;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.ui.act.PicInfoActivity;
import com.wb.ygq.ui.act.VideoPlayActivity;
import com.wb.ygq.ui.adapter.LayoutAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.AppUtils;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.widget.autoscrollviewpager.AutoScrollViewPager;
import com.wb.ygq.widget.autoscrollviewpager.CircleIndicator;
import com.wb.ygq.widget.autoscrollviewpager.ImagePagerAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private View view;
    private List<IBannerBean> banners;
    private HomeVideoBean mHomeVideoBean;

    RelativeLayout layout_banner;


    private AutoScrollViewPager viewPager;

    /**
     * banner 是否循环播放 默认 true 当只有一条banner时为false
     */
    private boolean isInfiniteLoop = true;
    /**
     * 添加轮播
     */
    private LinearLayout ll_home_addbanner;
    /**
     * 存储下面vp的list
     */
    private List<HomeVideoBean.DataBean.IndexImgListBean> vpList = new ArrayList<>();
//    private CycleGalleryViewPager viewpager_down;
    private LoopRecyclerViewPager mRecyclerViewPager;
    private LayoutAdapter mLayoutAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_home, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.registerReceiver(Receiver, new IntentFilter(PubConst.BROADCAST_REFAUSH + PubConst.ZUANSHI));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(Receiver);
    }

    public BroadcastReceiver Receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //重新请求接口
            initData();
        }
    };

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        ll_home_addbanner = (LinearLayout) view.findViewById(R.id.ll_home_addbanner);
//        viewpager = (LoopRecyclerViewPager) view.findViewById(R.id.viewpager);

        /**
         * 一下为画廊vp
         */
        mRecyclerViewPager = (LoopRecyclerViewPager)view.findViewById(R.id.viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerViewPager.setTriggerOffset(0.15f);
        mRecyclerViewPager.setFlingFactor(0.25f);
        mRecyclerViewPager.setLayoutManager(layout);
//        mRecyclerViewPager.setAdapter(new LayoutAdapter(mActivity, mRecyclerViewPager,10));
        mRecyclerViewPager.setHasFixedSize(true);
        mRecyclerViewPager.setLongClickable(true);

        mRecyclerViewPager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerViewPager.getChildCount();
                int width = mRecyclerViewPager.getChildAt(0).getWidth();
                int padding = (mRecyclerViewPager.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerViewPager.getChildCount() < 3) {
                    if (mRecyclerViewPager.getChildAt(1) != null) {
                        View v1 = mRecyclerViewPager.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerViewPager.getChildAt(0) != null) {
                        View v0 = mRecyclerViewPager.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerViewPager.getChildAt(2) != null) {
                        View v2 = mRecyclerViewPager.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });

    }

    @Override
    public void initData() {

        OkHttpUtils.get().url(HttpUrl.API.SHOUYE).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {
                String data = null;
                data = response.body().string();

                final String finalData = data;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHomeVideoBean = new Gson().fromJson(finalData, HomeVideoBean.class);
                        banners = new ArrayList<>();

                        for (int i = 0; i < mHomeVideoBean.getData().getCarouselList().size(); i++) {
                            IBannerBean bannerBean = new IBannerBean();
                            bannerBean.setBannerImg(mHomeVideoBean.getData().getCarouselList().get(i).getImg());
                            bannerBean.setBannerLinkId(mHomeVideoBean.getData().getCarouselList().get(i).getGo());
                            bannerBean.setBannerType(mHomeVideoBean.getData().getCarouselList().get(i).getUrl());
                            banners.add(bannerBean);
                        }
                        ll_home_addbanner.removeAllViews();
                        initBanner();
                        //下面vp
                        vpList.clear();
                        List<HomeVideoBean.DataBean.IndexImgListBean> indexImgList = mHomeVideoBean.getData().getIndexImgList();
                        if (indexImgList != null && !indexImgList.isEmpty()) {
                            vpList.addAll(indexImgList);
//                            initDownVpData();
                            mLayoutAdapter=new LayoutAdapter(mActivity,mRecyclerViewPager,vpList.size());
                            mRecyclerViewPager.setAdapter(mLayoutAdapter);
                            mLayoutAdapter.updateDatas(vpList);
                        }
                    }
                });

                return null;
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    @Override
    public void setListener() {

    }

    /**
     * 添加轮播
     */
    private void initBanner() {
        if (banners != null && banners.size() > 0) {
            View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_banner_viewpager_small, null);
            layout_banner = (RelativeLayout) header.findViewById(R.id.layout_banner);
            viewPager = (AutoScrollViewPager) layout_banner.findViewById(R.id.viewPager);
            int width = AppUtils.getScreenWidth(mActivity);
            int height = (int) (width * 7f / 16f);
            AbsListView.LayoutParams rlp = new AbsListView.LayoutParams(width, height);
            layout_banner.setLayoutParams(rlp);
            layout_banner.setPadding(45, 0, 45, 0);
//            viewPager.setPadding(0, 0, 0, 60);
            isInfiniteLoop = banners.size() > 1;

            viewPager.setAdapter(new ImagePagerAdapter<>(1, mActivity, banners, new ImagePagerAdapter.onBannerItemClickListenter<IBannerBean>() {
                @Override
                public void onItemClick(IBannerBean bannerBean) {
                    doBannerClick(bannerBean);
                }
            }).setInfiniteLoop(isInfiniteLoop));

            if (isInfiniteLoop) {
//                viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
                viewPager.setInterval(3000);
                viewPager.startAutoScroll();
            }
            viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % banners.size());

            final LinearLayout pageDotGroup = (LinearLayout) layout_banner.findViewById(R.id.viewGroup);

            layout_banner.setVisibility(View.VISIBLE);

            CircleIndicator indicator = new CircleIndicator(mActivity);
            indicator.setViewPager(viewPager);
            indicator.setPageDotGroup(pageDotGroup);
            indicator.setItemsCount(banners.size());
            indicator.init();
            ll_home_addbanner.addView(layout_banner);
        }
    }

    /**
     * 处理banner 点击事件
     *
     * @param bannerBean
     */
    private void doBannerClick(IBannerBean bannerBean) {

        if ("99".equals(bannerBean.getBannerType())) {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", bannerBean.getBannerLinkId());
            SharedUtil.setString("VideoId", bannerBean.getBannerLinkId());
            skip(VideoPlayActivity.class, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", bannerBean.getBannerLinkId());
            skip(PicInfoActivity.class, bundle, false);
        }
    }
}
