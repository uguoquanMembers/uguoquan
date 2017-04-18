package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.CollcetVideoBean;
import com.wb.ygq.bean.CollectVideoResponseBean;
import com.wb.ygq.bean.CommResponseBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.callback.RecyclerViewItemLongClickListener;
import com.wb.ygq.ui.act.VideoPlayActivity;
import com.wb.ygq.ui.adapter.CollectVideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.ConfirmDialog;
import com.wb.ygq.utils.DialogUtil;
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
 * Description：
 * Created on 2017/4/11
 */
public class CollectVideoFragment extends BaseFragment implements RecyclerViewItemClickListener, RecyclerViewItemLongClickListener {
    private View view;

    private RecyclerView recycleview;

    private CollectVideoAdapter adapter;
    /**
     * 存储 列表数据
     */
    private List<CollcetVideoBean> dataList = new ArrayList<>();
    private String userId;


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
        requestCollectListData();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycleview = (RecyclerView) view.findViewById(R.id.recycleview);
        adapter = new CollectVideoAdapter(mActivity);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter.updateItems(dataList);
        recycleview.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);
    }

    /**
     * 请求列表接口
     */
    private void requestCollectListData() {
        userId = SharedUtil.getString(PubConst.KEY_UID, "");
        MyUtil.showLog("用户id====" + userId);
        if (TextUtils.isEmpty(userId)) {
            ToastUtil.showToast("您还没有登录");
        } else {
            OkHttpUtils.get().url(HttpUrl.API.COLLECT_LIST).addParams("uid", userId).addParams("type", "2").build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final CollectVideoResponseBean responseBean = new Gson().fromJson(response.body().string(), CollectVideoResponseBean.class);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyUtil.showLog("请求成功====" + responseBean);
                            if (responseBean != null) {
                                List<CollcetVideoBean> data = responseBean.getData();
                                if (data != null) {
                                    dataList.addAll(data);
                                    adapter.updateItems(dataList);
                                }
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

    /**
     * 请求删除接口
     *
     * @param position
     */
    private void requestDeleteListData(final int position) {
        MyUtil.showLog("position====" + position);
        OkHttpUtils.get().url(HttpUrl.API.DELETE_COLLECT_LIST).addParams("uid", userId).addParams("vid", dataList.get(position).getId()).addParams("type", "2").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                CommResponseBean commResponseBean = new Gson().fromJson(response.body().string(), CommResponseBean.class);
                MyUtil.showLog("请求陈宫====" + commResponseBean.getCount());
                if (TextUtils.equals(commResponseBean.getCount(), "1")) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast("删除成功");
                            adapter.removeItem(position);
                        }
                    });
                }
                return null;
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtil.showToast("删除失败,请稍后重试");
            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {
        SharedUtil.setString("VideoId", dataList.get(position).getId());
        skip(VideoPlayActivity.class, false);
    }

    @Override
    public void OnItemLongClick(View view, Object o, final int position) {
        DialogUtil.showReminder(mActivity, "提示：", "确定删除此视频？", "取消", "删除", new ConfirmDialog() {
            @Override
            public void onOKClick(Bundle data) {
                //点击确定 请求删除接口
                requestDeleteListData(position);
            }


            @Override
            public void onCancleClick() {

            }
        });
    }
}
