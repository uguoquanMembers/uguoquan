package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.ClassifyVideoResponseBean;
import com.wb.ygq.bean.VideoFMBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
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
 * Description：//试看
 * Created on 2017/4/13
 */
public class ClassifyZsFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {
    private IRecyclerView recyclerView;
    private View view;
    private VideoAdapter adapter;
    private LoadMoreFooterView loadMoreFooterView;
    private int pageNum = 1;
    /**
     * 存储视频数据
     */
    private List<VideoFMBean.DataBean.VideoListBean> dataList = new ArrayList<>();

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
        OkHttpUtils.get().url(HttpUrl.API.CLASSIFY_VIDEO).addParams("page", String.valueOf(pageNum)).addParams("type", "3").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {
                final ClassifyVideoResponseBean responseBean = new Gson().fromJson(response.body().string(), ClassifyVideoResponseBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean != null) {
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

}
