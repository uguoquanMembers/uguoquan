package com.wb.ygq.ui.fm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
public class SpPhotoFragment extends BaseFragment implements RecyclerViewItemClickListener, OnRefreshListener, OnLoadMoreListener, OnCommentListener {

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

    //输入框
    private RelativeLayout rl_input;
    private EditText et_input;
    private TextView tv_button_send;

    private int commentPosition;

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
                            MyUtil.showLog("返回的数据===" + dataList.toString());
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
        tv_button_send = (TextView) view.findViewById(R.id.tv_button_send);
        rl_input = (RelativeLayout) view.findViewById(R.id.rl_input);
        et_input = (EditText) view.findViewById(R.id.et_input);
    }

    @Override
    public void initData() {
        adapter = new SpPhotoAdapter(mActivity, mActivity, this);
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
        mActivity.registerReceiver(Receiver, new IntentFilter(PubConst.BROADCAST_REFAUSH + PubConst.DASHANG));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(Receiver);
    }

    public BroadcastReceiver Receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            pageNum = 1;
            adapter = new SpPhotoAdapter(mActivity, mActivity, SpPhotoFragment.this);
            recycleview.setIAdapter(adapter);
            adapter.updateItems(dataList);
            //重新请求接口
        }
    };

    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
        recycleview.setOnRefreshListener(this);
        recycleview.setOnLoadMoreListener(this);
        recycleview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rl_input.getVisibility() == View.VISIBLE) {
                    rl_input.setVisibility(View.GONE);
                    et_input.setText("");
                    colseKeyBoard();
                }
                return false;
            }
        });
        tv_button_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_button_send:
                dataList.get(commentPosition).setComment(Integer.parseInt(dataList.get(commentPosition).getComment()) + 1 + "");
                adapter.notifyDataSetChanged();
                rl_input.setVisibility(View.GONE);
                et_input.setText("");
                colseKeyBoard();
                break;

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
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
        MyUtil.showLog("点击完毕===" + dataList.get(position).getId());
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

    /**
     * 关闭软键盘
     */
    private void colseKeyBoard() {
        View view = mActivity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void getComment(int position) {
        this.commentPosition = position;
        inPutComment();
    }

}
