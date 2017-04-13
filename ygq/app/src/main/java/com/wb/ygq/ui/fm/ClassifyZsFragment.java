package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wb.ygq.R;
import com.wb.ygq.bean.IBannerBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.widget.autoscrollviewpager.AutoScrollViewPager;
import com.wb.ygq.widget.irecycleerview.IRecyclerView;
import com.wb.ygq.widget.irecycleerview.LoadMoreFooterView;
import com.wb.ygq.widget.irecycleerview.OnLoadMoreListener;
import com.wb.ygq.widget.irecycleerview.OnRefreshListener;

import java.util.List;

/**
 * Description：//试看
 * Created on 2017/4/13
 */
public class ClassifyZsFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {
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

    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {
        MyUtil.showLog("dianji ");
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
//        OkHttpUtils.get()
//                .url(String.format(HttpUrl.API.CLASSIFY_VIDEO, pageNum))
//                .build()
//                .execute
    }

}
