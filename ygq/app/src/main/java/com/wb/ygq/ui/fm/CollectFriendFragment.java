package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.R;
import com.wb.ygq.ui.adapter.CollectPhotoAdapter;
import com.wb.ygq.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/11
 * Author : 郭
 */
public class CollectFriendFragment extends BaseFragment {
    private View view;

    private RecyclerView recycleview;

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
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
