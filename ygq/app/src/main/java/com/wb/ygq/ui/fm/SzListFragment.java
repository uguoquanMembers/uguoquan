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
import com.wb.ygq.ui.adapter.SzListAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Description：
 * Created on 2017/4/3
 * Author : 郭
 */
public class SzListFragment extends BaseFragment implements RecyclerViewItemClickListener {

    private String title;
    private String id;
    private int page;
    private View view;
    private RecyclerView recycle_sz;
    private SzListAdapter adapter;
    private SZMessage mSZMessage;

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
        recycle_sz = (RecyclerView) view.findViewById(R.id.recycle_sz);
        recycle_sz.setHasFixedSize(true);
        recycle_sz.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new SzListAdapter(mActivity);
        adapter.setItemClickListener(this);
        recycle_sz.setAdapter(adapter);
        getNetDatas();

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

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

    }

    /**
     * 获取网络数据
     */
    public void getNetDatas(){
        OkHttpUtils.get()
                .url(String.format(HttpUrl.API.SZ_MESSAGE,id,page))
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
                                mSZMessage=new Gson().fromJson(finalData,SZMessage.class);
                                adapter.updateItems(mSZMessage.getData());
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



}
