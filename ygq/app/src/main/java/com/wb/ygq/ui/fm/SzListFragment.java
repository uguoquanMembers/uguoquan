package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.SZMessage;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.act.MainActivity;
import com.wb.ygq.ui.act.PicInfoActivity;
import com.wb.ygq.ui.adapter.SzListAdapter;
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
 * Description：
 * Created on 2017/4/3
 * Author : 郭
 */
public class SzListFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private String title;
    private String id;
    private int page;
    private View view;
    private IRecyclerView recycle_sz;
    private SzListAdapter adapter;
    private SZMessage mSZMessage;
    private LoadMoreFooterView loadMoreFooterView;

    private List<SZMessage.DataBean> dataList = new ArrayList<>();

    public static SzListFragment newInstance(String title, String id) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("id", id);
        SzListFragment fragment = new SzListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_sz_child, null);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycle_sz = (IRecyclerView) view.findViewById(R.id.recycle_sz);
        recycle_sz.setHasFixedSize(true);
        recycle_sz.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new SzListAdapter(mActivity);
        loadMoreFooterView = (LoadMoreFooterView) recycle_sz.getLoadMoreFooterView();
        recycle_sz.setIAdapter(adapter);
        recycle_sz.post(new Runnable() {
            @Override
            public void run() {
                recycle_sz.setRefreshing(true);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        recycle_sz.setOnRefreshListener(this);
        recycle_sz.setOnLoadMoreListener(this);
        adapter.setItemClickListener(this);
        final int[] key_x = {0};
        recycle_sz.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                MyUtil.showLog("dx=====" + dx + "dy======" + dy);
                key_x[0] = key_x[0] + dy;
                if (key_x[0] > 0) {

                } else {

                }
                MainActivity.setToolBarH(key_x[0]/20);
            }
        });
    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            id = bundle.getString("id");
        }

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
        SZMessage.DataBean dataBean = (SZMessage.DataBean) o;
        Bundle bundle = new Bundle();
        bundle.putString("id", dataBean.getId());
        skip(PicInfoActivity.class, bundle, false);
    }

    /**
     * 获取网络数据
     */
    public void getNetDatas() {
        OkHttpUtils.get().url(String.format(HttpUrl.API.SZ_MESSAGE, id, page)).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {

                String data = null;
                data = response.body().string();

                final String finalData = data;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSZMessage = new Gson().fromJson(finalData, SZMessage.class);
                        List<SZMessage.DataBean> recordList = mSZMessage.getData();
                        recycle_sz.setRefreshing(false);
                        if (recordList != null && !recordList.isEmpty()) {
                            page++;
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
    public void onRefresh() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        page = 1;
        getNetDatas();
    }

    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            getNetDatas();
        }
    }
}
