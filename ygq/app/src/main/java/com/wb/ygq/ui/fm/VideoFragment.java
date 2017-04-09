package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.AppUtils;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.ToastUtil;
import com.wb.ygq.widget.autoscrollviewpager.AutoScrollViewPager;
import com.wb.ygq.widget.autoscrollviewpager.CircleIndicator;
import com.wb.ygq.widget.autoscrollviewpager.ImagePagerAdapter;
import com.wb.ygq.widget.irecycleerview.IRecyclerView;
import com.wb.ygq.widget.irecycleerview.LoadMoreFooterView;
import com.wb.ygq.widget.irecycleerview.OnLoadMoreListener;
import com.wb.ygq.widget.irecycleerview.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/2
 */
public class VideoFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private View view;
    private IRecyclerView recycle_video;
    private VideoAdapter adapter;
    /**
     * 存储数据
     */
    private List<VideoFMBean.DataBean.VideoListBean> dataList = new ArrayList<>();
    private ImageView ima_videohead_left;
    private ImageView ima_videohead_right;
    private LinearLayout ll_video_addvp;
    private RelativeLayout layout_banner;

    private VideoFMBean mVideoFMBean;
    private int pageNum = 1;

    private AutoScrollViewPager viewPager;

    /**
     * banner 是否循环播放 默认 true 当只有一条banner时为false
     */
    private boolean isInfiniteLoop = true;
    private List<IBannerBean> banners;

    private LoadMoreFooterView loadMoreFooterView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_video, null);
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
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycle_video = (IRecyclerView) view.findViewById(R.id.recycle_video);
    }

    @Override
    public void initData() {
        adapter = new VideoAdapter(mActivity);
        View headView = getHeadView();
        if (headView != null) {
            recycle_video.addHeaderView(headView);
        }
        recycle_video.setHasFixedSize(true);
        recycle_video.setLayoutManager(new GridLayoutManager(mActivity, 1));
        adapter.setItemClickListener(this);
        loadMoreFooterView = (LoadMoreFooterView) recycle_video.getLoadMoreFooterView();
        recycle_video.setIAdapter(adapter);
        recycle_video.post(new Runnable() {
            @Override
            public void run() {
                recycle_video.setRefreshing(true);
            }
        });
    }

    @Override
    public void setListener() {
        ima_videohead_left.setOnClickListener(this);
        ima_videohead_right.setOnClickListener(this);
        recycle_video.setOnRefreshListener(this);
        recycle_video.setOnLoadMoreListener(this);
        adapter.setItemClickListener(this);
    }

    /**
     * 头布局
     *
     * @return
     */
    public View getHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.headview_video_fm, null);
        ll_video_addvp = (LinearLayout) headView.findViewById(R.id.ll_video_addvp);
        ima_videohead_left = (ImageView) headView.findViewById(R.id.ima_videohead_left);
        ima_videohead_right = (ImageView) headView.findViewById(R.id.ima_videohead_right);

        return headView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ima_videohead_left://统一跳转
            case R.id.ima_videohead_right:
                ToastUtil.showToast("吐司啦啦啦");
                break;

            default:
                break;
        }
    }

    /**
     * iten点击事件
     *
     * @param view
     * @param o
     * @param position
     * @param eventType
     */
    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {

    }

    public void getNetDatas() {
        OkHttpUtils.get()
                .url(String.format(HttpUrl.API.VIDEO_FM, pageNum))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(final Response response) throws IOException {

                        String data = null;
                        data = response.body().string();

                        final String finalData = data;
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVideoFMBean = new Gson().fromJson(finalData, VideoFMBean.class);
                                MyUtil.showLog("数据为1111111111===" + mVideoFMBean.toString());
                                //处理轮播
                                banners = new ArrayList<>();
                                for (int i = 0; i < mVideoFMBean.getData().getCarouselList().size(); i++) {
                                    IBannerBean bannerBean = new IBannerBean();
                                    bannerBean.setBannerImg(mVideoFMBean.getData().getCarouselList().get(i).getImg());
                                    bannerBean.setBannerLinkId("001");
                                    bannerBean.setBannerType("005");
                                    banners.add(bannerBean);
                                }
                                if (ll_video_addvp.getChildCount() == 0) {
                                    initBanner();
                                }
                                //轮播下两张图片
                                Glide.with(mActivity).load(mVideoFMBean.getData().getPictureList().get(0).getImg())
                                        .into(ima_videohead_left);
                                Glide.with(mActivity).load(mVideoFMBean.getData().getPictureList().get(1).getImg())
                                        .into(ima_videohead_right);
                                List<VideoFMBean.DataBean.VideoListBean> videoList = mVideoFMBean.getData().getVideoList();
                                if (pageNum == 1) dataList.clear();
                                recycle_video.setRefreshing(false);
                                if (videoList != null && !videoList.isEmpty()) {
                                    pageNum++;
                                    dataList.addAll(videoList);
                                    adapter.updateItems(dataList);
                                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                                } else {
                                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                                }
                            }
                        });
                        return null;
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        recycle_video.setRefreshing(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Object response) {
//                        MyUtil.showLog("数据为222222222222==="+response.toString());
                    }
                });
    }

    /**
     * 添加轮播
     */
    private void initBanner() {
        if (banners != null && banners.size() > 0) {
            View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_banner_viewpager, null);
            layout_banner = (RelativeLayout) header.findViewById(R.id.layout_banner);
            viewPager = (AutoScrollViewPager) layout_banner.findViewById(R.id.viewPager);
            int width = AppUtils.getScreenWidth(mActivity);
            int height = (int) (width * 9f / 16f);
            AbsListView.LayoutParams rlp = new AbsListView.LayoutParams(width, height);
            layout_banner.setLayoutParams(rlp);

            isInfiniteLoop = banners.size() > 1;

            viewPager.setAdapter(new ImagePagerAdapter<>(0, mActivity, banners, new ImagePagerAdapter.onBannerItemClickListenter<IBannerBean>() {
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
            ll_video_addvp.addView(layout_banner);
        }
    }

    /**
     * 处理banner 点击事件
     *
     * @param bannerBean
     */
    private void doBannerClick(IBannerBean bannerBean) {

        switch (bannerBean.getBannerType()) {
            case "005"://

                MyUtil.showLog("点击banner");
                break;

        }
    }

    @Override
    public void onRefresh() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        pageNum = 1;
        getNetDatas();
    }

    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            pageNum++;
            getNetDatas();
            MyUtil.showLog("上拉加载" + pageNum);
        }
    }
}
