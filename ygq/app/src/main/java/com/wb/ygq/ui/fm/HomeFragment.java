package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.wb.ygq.bean.HomeVideoBean;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.AppUtils;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.HttpUrl;
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
    public void initTitle() {

    }

    @Override
    public void initView() {
        ll_home_addbanner = (LinearLayout) view.findViewById(R.id.ll_home_addbanner);

    }

    @Override
    public void initData() {

        OkHttpUtils.get()
                .url(HttpUrl.API.SHOUYE)
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
                                mHomeVideoBean = new Gson().fromJson(finalData, HomeVideoBean.class);
                                banners = new ArrayList<>();
                                for (int i = 0; i < 5; i++) {
                                    IBannerBean bannerBean = new IBannerBean();
                                    bannerBean.setBannerImg("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
                                    bannerBean.setBannerLinkId("001");
                                    bannerBean.setBannerType("005");
                                    banners.add(bannerBean);
                                }
                                initBanner();
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
            View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_banner_viewpager, null);
            layout_banner = (RelativeLayout) header.findViewById(R.id.layout_banner);
            viewPager = (AutoScrollViewPager) layout_banner.findViewById(R.id.viewPager);
            int width = AppUtils.getScreenWidth(mActivity);
            int height = (int) (width * 9f / 16f);
            AbsListView.LayoutParams rlp = new AbsListView.LayoutParams(width, height);
            layout_banner.setLayoutParams(rlp);

            isInfiniteLoop = banners.size() > 1;

            viewPager.setAdapter(new ImagePagerAdapter<>(mActivity, banners, new ImagePagerAdapter.onBannerItemClickListenter<IBannerBean>() {
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

        switch (bannerBean.getBannerType()) {
            case "005"://

                MyUtil.showLog("点击banner");
                break;

        }
    }
}
