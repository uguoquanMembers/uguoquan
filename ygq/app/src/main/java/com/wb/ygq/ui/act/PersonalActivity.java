package com.wb.ygq.ui.act;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.FriendListBean;
import com.wb.ygq.bean.SpFriendListResponseBean;
import com.wb.ygq.callback.OnCommentListener;
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
public class PersonalActivity extends BaseActivity implements OnCommentListener {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private SpPhotoAdapter adapter;
    private List<FriendListBean> dataList = new ArrayList<>();
    /**
     * 上页传入id
     */
    private String userId;

    //输入框
    private RelativeLayout rl_input;
    private EditText et_input;
    private TextView tv_button_send;

    private int commentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_toolbar_recycler);
        getBundleData();
        initTitle();
        initView();
        initData();
        setListener();
        requestPersonalListData();
    }

    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        tv_button_send = (TextView) findViewById(R.id.tv_button_send);
        rl_input = (RelativeLayout) findViewById(R.id.rl_input);
        et_input = (EditText) findViewById(R.id.et_input);
    }

    @Override
    public void initData() {
        adapter = new SpPhotoAdapter(this, this,this);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateItems(dataList);
        recycler.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        tv_button_send.setOnClickListener(this);
        recycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rl_input.getVisibility()==View.VISIBLE){
                    rl_input.setVisibility(View.GONE);
                    et_input.setText("");
                    colseKeyBoard();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_button_send:
                dataList.get(commentPosition).setComment(Integer.parseInt(dataList.get(commentPosition).getComment())+1+"");
                adapter.notifyDataSetChanged();
                rl_input.setVisibility(View.GONE);
                et_input.setText("");
                colseKeyBoard();
                break;
        }
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
                            List<FriendListBean> recordList = responseBean.getData();
                            if (recordList != null && !recordList.isEmpty()) {
                                dataList.addAll(recordList);
                                MyUtil.showLog("adapter===" + adapter);
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

    /**
     * 输入评论
     */
    public void inPutComment() {
        rl_input.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.requestFocus();
        et_input.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 关闭软键盘
     */
    private void colseKeyBoard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void getComment(int position) {
        this.commentPosition = position;
        inPutComment();
    }
}
