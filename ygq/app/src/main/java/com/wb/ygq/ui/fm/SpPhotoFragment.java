package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.SpPhotoAdapter;
import com.wb.ygq.ui.base.BaseFragment;

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
    private List<CeshiBean> dataList = new ArrayList();

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
        getceshiData();
        adapter = new SpPhotoAdapter(mActivity);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter.setItemClickListener(this);
        recycleview.setAdapter(adapter);
        adapter.updateItems(dataList);
    }

    @Override
    public void setListener() {

    }

    /**
     * 测试数据
     *
     * @return
     */
    public void getceshiData() {
        for (int i = 0; i < 9; i++) {
            CeshiBean cb = new CeshiBean();
            cb.setId(1);
            cb.setIma("http://shtml.asia-cloud.com/ZZSY/list_test3.png");
            cb.setName("卧槽" + i);
            cb.setNum(13 + i);
            dataList.add(cb);
        }

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

    }
}
