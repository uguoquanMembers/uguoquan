package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.bean.CollcetVideoBean;
import com.wb.ygq.bean.SpVideoBean;
import com.wb.ygq.bean.SpVideoResponseBean;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.act.VideoPlayActivity;
import com.wb.ygq.ui.adapter.CollectVideoAdapter;
import com.wb.ygq.ui.adapter.SpVideoAdapter;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;
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
 * Description：私拍视频
 * Created on 2017/4/3
 */
public class SpVideoFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {
    public static SpVideoFragment newInstance() {
        Bundle args = new Bundle();
        SpVideoFragment fragment = new SpVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view;
    private int pageNum = 1;
    private IRecyclerView recycleview;
    private VideoAdapter adapter;
    /**
     * 存入集合
     */
    private List<VideoFMBean.DataBean.VideoListBean> dataList = new ArrayList();
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
        recycleview = (IRecyclerView) view.findViewById(R.id.recycle_video);
    }

    @Override
    public void initData() {
        adapter = new VideoAdapter(mActivity);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 1));
        adapter.setItemClickListener(this);
        loadMoreFooterView = (LoadMoreFooterView) recycleview.getLoadMoreFooterView();
        recycleview.setIAdapter(adapter);
        recycleview.post(new Runnable() {
            @Override
            public void run() {
                recycleview.setRefreshing(true);
            }
        });
    }

    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
        recycleview.setOnRefreshListener(this);
        recycleview.setOnLoadMoreListener(this);
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
            requestDataList();
            MyUtil.showLog("上拉加载" + pageNum);
        }
    }

    /**
     * 请求网络数据
     */
    private void requestDataList() {
        OkHttpUtils.get().url(HttpUrl.API.SP_VIDEO).addParams("page", String.valueOf(pageNum)).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {
                final SpVideoResponseBean bean = new Gson().fromJson(response.body().string(), SpVideoResponseBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean != null) {
                            List<VideoFMBean.DataBean.VideoListBean> videoList = bean.getData().getVideoList();
                            if (pageNum == 1) dataList.clear();
                            recycleview.setRefreshing(false);
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
                recycleview.setRefreshing(false);
                ToastUtil.showToast("数据加载错误，请稍后重试");
            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

}
