package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.AlreadyBuyBean;
import com.wb.ygq.bean.AlreadyBuyListResponseBean;
import com.wb.ygq.ui.adapter.AlreadyBuyAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：购买记录
 * Created on 2017/4/8
 */
public class AlreadyBuyActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView recycler;
    /**
     * 存储数据
     */
    private List<AlreadyBuyBean> dataList = new ArrayList<>();
    private AlreadyBuyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_alreadybuy);
        initTitle();
        initView();
        initData();
        setListener();
        requestDataList();
    }


    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_alreadybuy);
        toolbar.setNavigationIcon(R.drawable.back_black);
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
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        adapter = new AlreadyBuyAdapter(this);
        adapter.updateItems(dataList);
        recycler.setAdapter(adapter);

    }

    @Override
    public void setListener() {

    }

    /**
     * 请求数据
     */
    private void requestDataList() {
        String uid = SharedUtil.getString(PubConst.KEY_UID, "");
        if (TextUtils.isEmpty(uid)) {
            ToastUtil.showToast("您还没有登录");
            return;
        } else {
            // TODO 暂时写死1  其他没有数据
            OkHttpUtils.get().url(HttpUrl.API.ALREADY_BUY).addParams("uid", "1").build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final AlreadyBuyListResponseBean bean = new Gson().fromJson(response.body().string(), AlreadyBuyListResponseBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bean != null) {
                                List<AlreadyBuyBean> recordList = bean.getData();
                                MyUtil.showLog("返回的数据==" + recordList);
                                if (recordList != null && !recordList.isEmpty()) {
                                    dataList.addAll(recordList);
                                    adapter.updateItems(dataList);
                                }
                            }
                        }
                    });

                    return null;
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtil.showToast("数据加载失败");
                }

                @Override
                public void onResponse(Object response) {

                }
            });
        }
    }

}
