package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.R;
import com.wb.ygq.ui.adapter.SpVideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;

/**
 * Description：私拍视频
 * Created on 2017/4/3
 */
public class SpVideoFragment extends BaseFragment {
    public static SpVideoFragment newInstance() {
        Bundle args = new Bundle();
        SpVideoFragment fragment = new SpVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view;

    private RecyclerView recycleview;
    private SpVideoAdapter adapter;

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

    }

    @Override
    public void setListener() {

    }
}
