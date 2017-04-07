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
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.bean.SpFriendListResponseBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.SpPhotoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/3
 */
public class SpPhotoFragment extends BaseFragment implements RecyclerViewItemClickListener {

    public static SpPhotoFragment newInstance() {

        Bundle args = new Bundle();

        SpPhotoFragment fragment = new SpPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view;

    private RecyclerView recycleview;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_single_recyclerview, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        setListener();
        requestDataList(pageNum);
    }

    /**
     * 请求数据
     *
     * @param pageNum
     */
    private void requestDataList(int pageNum) {
        OkHttpUtils.get().url(HttpUrl.API.FRIEND_QUN).addParams("page", String.valueOf(pageNum)).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws IOException {

                final String body = response.body().string();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseBean = new Gson().fromJson(body, SpFriendListResponseBean.class);
                        List<FriendListBean> recordList = responseBean.getData();
                        if (recordList != null && !recordList.isEmpty()) {
                            dataList.addAll(recordList);
                            adapter.updateItems(dataList);
                        }
                        MyUtil.showLog("返回的数据是===" + responseBean);
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
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycleview = (RecyclerView) view.findViewById(R.id.recycleview);
    }

    @Override
    public void initData() {
        adapter = new SpPhotoAdapter(mActivity);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(mActivity,1));
        adapter.setItemClickListener(this);
        recycleview.setAdapter(adapter);
        adapter.updateItems(dataList);
    }

    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
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
        MyUtil.showLog("点击的iem=="+position);
    }
}
