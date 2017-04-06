package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.AppUtils;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.PublicUtil;
import com.wb.ygq.utils.ToastUtil;
import com.wb.ygq.widget.autoscrollviewpager.AutoScrollViewPager;
import com.wb.ygq.widget.autoscrollviewpager.CircleIndicator;
import com.wb.ygq.widget.autoscrollviewpager.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/2
 */
public class VideoFragment extends BaseFragment implements RecyclerViewItemClickListener {

    private View view;
    private RecyclerView recycle_video;
    private VideoAdapter adapter;
    /**
     * 存储数据
     */
    private List<CeshiBean> dataList = new ArrayList<>();
    private ImageView ima_videohead_left;
    private ImageView ima_videohead_right;
    private LinearLayout ll_video_addvp;
    private RelativeLayout layout_banner;


    private AutoScrollViewPager viewPager;

    /**
     * banner 是否循环播放 默认 true 当只有一条banner时为false
     */
    private boolean isInfiniteLoop = true;
    private List<IBannerBean> banners;

    private SwipeToLoadLayout swipe_refresh;

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
        recycle_video = (RecyclerView) view.findViewById(R.id.recycle_video);
        swipe_refresh = (SwipeToLoadLayout) view.findViewById(R.id.swipe_refresh);
    }

    @Override
    public void initData() {
        getceshiData();
        adapter = new VideoAdapter(mActivity);
        View headView = getHeadView();
        if (headView != null) {
            adapter.setHeadView(headView);
        }
        recycle_video.setHasFixedSize(true);
        recycle_video.setLayoutManager(new GridLayoutManager(mActivity, 1));
        adapter.setItemClickListener(this);
        recycle_video.setAdapter(adapter);
        adapter.updateItems(dataList);

//处理轮播
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

    @Override
    public void setListener() {
        ima_videohead_left.setOnClickListener(this);
        ima_videohead_right.setOnClickListener(this);
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
        MyUtil.showLog("屏幕的宽度为===" + ll_video_addvp.getWidth());
        ima_videohead_left = (ImageView) headView.findViewById(R.id.ima_videohead_left);
        ima_videohead_right = (ImageView) headView.findViewById(R.id.ima_videohead_right);
        //设置为屏幕宽度的三分之1
        PublicUtil.setImaSize(mActivity,ima_videohead_left,3,1);
        PublicUtil.setImaSize(mActivity,ima_videohead_right,3,1);
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
        MyUtil.showLog("点击的item===" + position);

    }

    /**
     * 测试数据
     *
     * @return
     */
    public void getceshiData() {
        for (int i = 0; i < 9; i++) {
            CeshiBean cb = new CeshiBean();
            cb.setId(1);
            cb.setIma("http://shtml.asia-cloud.com/ZZSY/list_test1.png");
            cb.setName("卧槽sb崔" + i);
            cb.setNum(13 + i);
            dataList.add(cb);
        }

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
}
