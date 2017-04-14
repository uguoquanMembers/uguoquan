package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.ClassifyVideoResponseBean;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.bean.VideoBannerBean;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.act.VideoPlayActivity;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.AppUtils;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.SharedUtil;
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
 * Description：//试看
 * Created on 2017/4/13
 */
public class ClassifySkFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {
    private IRecyclerView recyclerView;
    private View view;
    private VideoAdapter adapter;
    private LoadMoreFooterView loadMoreFooterView;
    /**
     * 加载轮播图
     */
    private LinearLayout ll_single;
    private int pageNum = 1;

    private AutoScrollViewPager viewPager;

    /**
     * banner 是否循环播放 默认 true 当只有一条banner时为false
     */
    private boolean isInfiniteLoop = true;
    private List<IBannerBean> banners;
    /**
     * 存储视频数据
     */
    private List<VideoFMBean.DataBean.VideoListBean> dataList = new ArrayList<>();
    RelativeLayout layout_banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_video, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        recyclerView = (IRecyclerView) view.findViewById(R.id.recycle_video);
    }

    @Override
    public void initData() {
        adapter = new VideoAdapter(mActivity);
        View headView = getHeadView();
        if (headView != null) {
            recyclerView.addHeaderView(headView);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 1));
        loadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();
        recyclerView.setIAdapter(adapter);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);
            }
        });
    }

    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
    }

    public View getHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.layout_single_ll, null);
        ll_single = (LinearLayout) headView.findViewById(R.id.ll_single);
        return headView;
    }

    /**
     * item点击事件
     *
     * @param view
     * @param o
     * @param position
     * @param eventType
     */
    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {
        SharedUtil.setString("VideoId", dataList.get(position).getId());
        skip(VideoPlayActivity.class, false);
    }

    @Override
    public void onRefresh() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        pageNum = 1;
        requestDataList();
    }


    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
//            pageNum++;
            requestDataList();
            MyUtil.showLog("上拉加载" + pageNum);
        }
    }

    /**
     * 请求网络数据
     */
    private void requestDataList() {
        OkHttpUtils.get().url(HttpUrl.API.CLASSIFY_VIDEO).addParams("page", String.valueOf(pageNum)).addParams("type", "1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {
                final ClassifyVideoResponseBean responseBean = new Gson().fromJson(response.body().string(), ClassifyVideoResponseBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean != null) {
                            banners = new ArrayList<>();
                            List<VideoBannerBean> bannerList = responseBean.getData().getCarouselList();
                            if (bannerList != null && !bannerList.isEmpty()) {
                                for (int i = 0; i < bannerList.size(); i++) {
                                    IBannerBean bannerBean = new IBannerBean();
                                    bannerBean.setBannerImg(bannerList.get(i).getImg());
                                    bannerBean.setBannerLinkId(bannerList.get(i).getGo());
//                                    bannerBean.setBannerType(mHomeVideoBean.getData().getCarouselList().get(i).getUrl());
                                    banners.add(bannerBean);
                                }
                                initBanner();
                            }
                            List<VideoFMBean.DataBean.VideoListBean> videoList = responseBean.getData().getVideoList();
                            if (pageNum == 1) dataList.clear();
                            recyclerView.setRefreshing(false);
                            if (videoList != null && !videoList.isEmpty()) {
                                pageNum++;
                                dataList.addAll(videoList);
                                adapter.updateItems(dataList);
                                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                            } else {
                                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                            }
                        }
                    }
                });
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {
                recyclerView.setRefreshing(false);
                ToastUtil.showToast("数据加载错误，请稍后重试");
            }

            @Override
            public void onResponse(Object response) {

            }
        });
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
            int height = (int) (width * 8f / 16f);
            AbsListView.LayoutParams rlp = new AbsListView.LayoutParams(width, height);
            layout_banner.setLayoutParams(rlp);
            isInfiniteLoop = banners.size() > 1;

            viewPager.setAdapter(new ImagePagerAdapter<>(0,mActivity, banners, new ImagePagerAdapter.onBannerItemClickListenter<IBannerBean>() {
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
            ll_single.addView(layout_banner);
        }
    }

    /**
     * 处理banner 点击事件
     *
     * @param bannerBean
     */
    private void doBannerClick(IBannerBean bannerBean) {

//        if ("99".equals(bannerBean.getBannerType())) {
////            Bundle bundle = new Bundle();
////            bundle.putString("id", bannerBean.getBannerLinkId());
            SharedUtil.setString("VideoId", bannerBean.getBannerLinkId());
            skip(VideoPlayActivity.class, false);
//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", bannerBean.getBannerLinkId());
//            skip(PicInfoActivity.class, bundle, false);
//        }
    }
}
