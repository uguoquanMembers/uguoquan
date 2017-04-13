package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.FragmentHolder;
import com.wb.ygq.bean.SZListBean;
import com.wb.ygq.ui.adapter.ViewpagerFragmentAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.ui.fm.SzListFragment;
import com.wb.ygq.utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SZActivity extends BaseActivity {

    private TabLayout sz_tablayout;
    private ViewPager vp_sz;
    private List<FragmentHolder> holders;
    private ViewpagerFragmentAdapter adapter;
    /**
     * 显示Fragment索引  默认  0
     */
    private int defIdx = 0;
    private SZListBean mSZListBean;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz);
        initTitle();
        initView();
        getNetDatas();
        setListener();
    }

    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arror_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        sz_tablayout = (TabLayout) findViewById(R.id.sz_tablayout);
        vp_sz = (ViewPager) findViewById(R.id.vp_sz);
        defIdx = Integer.parseInt(getIntent().getBundleExtra(PubConst.DATA).getString("index"));
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
                        runOnUiThread(new Runnable() {
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
                                adapter = new ViewpagerFragmentAdapter(getSupportFragmentManager(), holders);
                                vp_sz.setOffscreenPageLimit(3);
                                vp_sz.setAdapter(adapter);
                                sz_tablayout.setupWithViewPager(vp_sz);
                                if (defIdx >= holders.size()) {
                                    vp_sz.setCurrentItem(defIdx);
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

}
