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
import com.wb.ygq.bean.CollentBean;
import com.wb.ygq.bean.CollentResponseBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.callback.RecyclerViewItemLongClickListener;
import com.wb.ygq.ui.adapter.CollectPhotoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
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
 * Description：收藏的照片
 * Created on 2017/4/11
 */
public class CollectPhotoFragment extends BaseFragment implements RecyclerViewItemClickListener, RecyclerViewItemLongClickListener {

    private View view;

    private RecyclerView recycleview;

    private CollectPhotoAdapter adapter;
    /**
     * 存储 列表数据
     */
    private List<String> dataList = new ArrayList<>();


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

    /**
     * 请求列表接口
     */
    private void requestCollectListData() {
        String userId = SharedUtil.getString(PubConst.KEY_UID, "");
        MyUtil.showLog("用户id====" + userId);
        if (TextUtils.isEmpty(userId)) {
            ToastUtil.showToast("您还没有登录");
        } else {
            OkHttpUtils.get().url(HttpUrl.API.COLLECT_LIST).addParams("uid", userId).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final CollentResponseBean responseBean = new Gson().fromJson(response.body().string(), CollentResponseBean.class);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyUtil.showLog("请求成功====" + responseBean);
                            //假数据
                            String s = "http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg";
                            dataList.add(s);
                            dataList.add(s);
                            dataList.add(s);
                            dataList.add(s);
                            dataList.add(s);
                            adapter.updateItems(dataList);
                            if (responseBean != null) {
                                CollentBean data = responseBean.getData();
                                if (data != null) {
                                    dataList.addAll(data.getImg());
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

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycleview = (RecyclerView) view.findViewById(R.id.recycleview);
        adapter = new CollectPhotoAdapter(mActivity);
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
     * item点击
     *
     * @param view
     * @param o
     * @param position
     * @param eventType
     */
    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {
        MyUtil.showLog("点击事件===" + position);
    }

    /**
     * 长按删除
     *
     * @param view
     * @param o
     * @param position
     */
    @Override
    public void OnItemLongClick(View view, Object o, int position) {
        MyUtil.showLog("长按点击事件===" + position);
    }
}
