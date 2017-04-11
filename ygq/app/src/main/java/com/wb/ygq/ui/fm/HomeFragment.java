package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.HomeVideoBean;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.AppUtils;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.widget.CycleGalleryViewPager;
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
    private CycleGalleryViewPager viewpager_down;

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
        viewpager_down = (CycleGalleryViewPager) view.findViewById(R.id.viewpager);


    }

    /**
     * 处理vp
     */
    private void initDownVpData() {
        viewpager_down.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return vpList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_vp_home_down, container, false);
                ((TextView) view.findViewById(R.id.tv_vp_title)).setText(vpList.get(position).getTitle() + ">>");
                ImageView id = (ImageView) view.findViewById(R.id.ima_vp_con);
                Glide.with(mActivity).load(vpList.get(position).getImg()).crossFade().into(id);
                id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "电机的" + position, 1000).show();
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public float getPageWidth(int position) {
                return 0.8f;//建议值为0.6~1.0之间
            }
        });
        viewpager_down.setNarrowFactor(0.9f);
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
                                MyUtil.showLog("首页返回的数据===" + mHomeVideoBean);
                                banners = new ArrayList<>();
                                for (int i = 0; i < mHomeVideoBean.getData().getCarouselList().size(); i++) {
                                    IBannerBean bannerBean = new IBannerBean();
                                    bannerBean.setBannerImg(mHomeVideoBean.getData().getCarouselList().get(i).getImg());
                                    bannerBean.setBannerLinkId("001");
                                    bannerBean.setBannerType("005");
                                    banners.add(bannerBean);
                                }
                                initBanner();
                                //下面vp
                                vpList.clear();
                                List<HomeVideoBean.DataBean.IndexImgListBean> indexImgList = mHomeVideoBean.getData().getIndexImgList();
                                if (indexImgList != null && !indexImgList.isEmpty()) {
                                    vpList.addAll(indexImgList);
                                    vpList.addAll(indexImgList);
                                    vpList.addAll(indexImgList);
                                    vpList.addAll(indexImgList);
                                    vpList.addAll(indexImgList);
                                    initDownVpData();
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
            int height = (int) (width * 8f / 16f);
            AbsListView.LayoutParams rlp = new AbsListView.LayoutParams(width, height);
            layout_banner.setLayoutParams(rlp);
            layout_banner.setPadding(45, 0, 45, 0);
            viewPager.setPadding(0, 0, 0, 60);
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

        switch (bannerBean.getBannerType()) {
            case "005"://

                MyUtil.showLog("点击banner");
                break;

        }
    }
}
