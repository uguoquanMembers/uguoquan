package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.FragmentHolder;
import com.wb.ygq.bean.SZListBean;
import com.wb.ygq.ui.adapter.ViewpagerFragmentAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/2
 */
public class SzFragment extends BaseFragment {

    private View view;

    private TabLayout sz_tablayout;
    private ViewPager vp_sz;
    private List<FragmentHolder> holders;
    private ViewpagerFragmentAdapter adapter;
    /**
     * 显示Fragment索引  默认  0
     */
    private int defIdx = 0;
    private SZListBean mSZListBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_sz, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getBundleData();
        initView();
        getNetDatas();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        sz_tablayout = (TabLayout) view.findViewById(R.id.sz_tablayout);
        vp_sz = (ViewPager) view.findViewById(R.id.vp_sz);
    }

    @Override
    public void initData() {


    }

    @Override
    public void setListener() {

    }

    /**
     * 网络接口数据
     */
    public void getNetDatas() {
        OkHttpUtils.get()
                .url(HttpUrl.API.SZ_List)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(final Response response) throws IOException {
                        holders = new ArrayList<>();
                        String data = null;
                        data = response.body().string();

                        final String finalData = data;
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSZListBean = new Gson().fromJson(finalData, SZListBean.class);
                                if (mSZListBean.getData().size() <= 4) {
                                    sz_tablayout.setTabMode(TabLayout.MODE_FIXED);
                                } else {
                                    sz_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
                                for (SZListBean.DataBean dataBean : mSZListBean.getData()) {
                                    FragmentHolder holder = new FragmentHolder();
                                    holder.setTitle(dataBean.getTitle());
                                    holder.setFragment(SzListFragment.newInstance(holder.getTitle(), dataBean.getId()));
                                    holders.add(holder);
                                }
                                adapter = new ViewpagerFragmentAdapter(getFragmentManager(), holders);
                                vp_sz.setOffscreenPageLimit(3);
                                vp_sz.setAdapter(adapter);
                                sz_tablayout.setupWithViewPager(vp_sz);
                                if (defIdx >= holders.size()) defIdx = 0;
                                vp_sz.setCurrentItem(defIdx);
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
