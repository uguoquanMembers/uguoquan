package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.bean.SpFriendListResponseBean;
import com.wb.ygq.ui.adapter.SpPhotoAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：个人朋友圈
 * Created on 2017/4/12
 */
public class PersonalActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private SpPhotoAdapter adapter;
    private List<FriendListBean> dataList = new ArrayList<>();
    /**
     * 上页传入id
     */
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_toolbar_recycler);
        getBundleData();
        initTitle();
        initView();
        initView();
        setListener();
        requestPersonalListData();
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
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void initData() {
        adapter = new SpPhotoAdapter(this);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateItems(dataList);
        recycler.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    /**
     * 请求数据
     */
    private void requestPersonalListData() {
        OkHttpUtils.get().url(HttpUrl.API.PERSONAL_FRIEND).addParams("id", userId).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                final SpFriendListResponseBean responseBean = new Gson().fromJson(response.body().string(), SpFriendListResponseBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseBean != null) {
                            MyUtil.showLog("下载的记录==" + responseBean);
                            List<FriendListBean> recordList = responseBean.getData();
                            if (recordList != null && !recordList.isEmpty()) {
                                dataList.addAll(recordList);
                                adapter.updateItems(dataList);
                            } else {
                                ToastUtil.showToast("暂无朋友圈信息");
                            }
                        }
                    }
                });
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtil.showToast("网络错误，请稍后重试");
            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getIntent().getBundleExtra(PubConst.DATA);
        if (bundle != null) {
            userId = bundle.getString("uid");
        }
    }
}
