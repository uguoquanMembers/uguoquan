package com.wb.ygq.ui.fm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.bean.SpFriendListResponseBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.SpPhotoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.constant.PubConst;
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
 * Description：
 * Created on 2017/4/3
 */
public class SpPhotoFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {

    public static SpPhotoFragment newInstance() {

        Bundle args = new Bundle();

        SpPhotoFragment fragment = new SpPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view;

    private IRecyclerView recycleview;

    private SpPhotoAdapter adapter;
    /**
     * 存入集合
     */
    private List<FriendListBean> dataList = new ArrayList<>();


    private int pageNum = 1;

    /**
     * 网络请求bean
     */
    private SpFriendListResponseBean responseBean;
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
//        requestDataList(pageNum);
    }

    /**
     * 请求数据
     */
    private void requestDataList() {
        OkHttpUtils.get().url(HttpUrl.API.FRIEND_QUN).addParams("page", String.valueOf(pageNum)).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {
                final String body = response.body().string();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseBean = new Gson().fromJson(body, SpFriendListResponseBean.class);
                        List<FriendListBean> recordList = responseBean.getData();
                        if (pageNum == 1) dataList.clear();
                        recycleview.setRefreshing(false);
                        if (recordList != null && !recordList.isEmpty()) {
                            pageNum++;
                            dataList.addAll(recordList);
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
                ToastUtil.showToast("数据加载错误，请稍后重试");
            }

            @Override
            public void onResponse(Object response) {

            }
        });
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
        adapter = new SpPhotoAdapter(mActivity, mActivity);
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
    public void onResume() {
        super.onResume();
        mActivity.registerReceiver(Receiver, new IntentFilter(PubConst.BROADCAST_REFAUSH + "111"));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(Receiver);
    }

    public BroadcastReceiver Receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtil.showToast("sp刷新页面+接受的广播");
            pageNum = 1;
            requestDataList();
            //重新请求接口
        }
    };
    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
        recycleview.setOnRefreshListener(this);
        recycleview.setOnLoadMoreListener(this);
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
        MyUtil.showLog("点击的iem==" + position);
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
}
