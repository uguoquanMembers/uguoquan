package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.SzListAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/3
 * Author : 郭
 */
public class SzListFragment extends BaseFragment implements RecyclerViewItemClickListener {

    private String title;
    private int pos;
    private View view;
    private RecyclerView recycle_sz;
    private SzListAdapter adapter;
    /**
     *
     */
    private List<CeshiBean> dataList = new ArrayList<>();

    public static SzListFragment newInstance(String title, int pos) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("pos", pos);
        SzListFragment fragment = new SzListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_sz_child, null);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        getceshiData();
        recycle_sz = (RecyclerView) view.findViewById(R.id.recycle_sz);
        MyUtil.showLog("recycleview是否为空==="+recycle_sz);
        recycle_sz.setHasFixedSize(true);
        recycle_sz.setLayoutManager(new GridLayoutManager(mActivity, 2));
        adapter = new SzListAdapter(mActivity);
        adapter.setItemClickListener(this);
        recycle_sz.setAdapter(adapter);
        adapter.updateItems(dataList);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    /**
     * 得到bundle数据
     */
    public void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            pos = bundle.getInt("pos");
        }

    }

    /**
     * item点击事件
     *
     * @param view
     * @param o
     * @param position
     * @param eventType
     */
    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {

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
}
