package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.R;
import com.wb.ygq.bean.FragmentHolder;
import com.wb.ygq.ui.adapter.ViewpagerFragmentAdapter;
import com.wb.ygq.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/2
 */
public class SzFragment extends BaseFragment {

    private View view;

    private TabLayout sz_tablayout;
    private ViewPager vp_sz;
    private List<FragmentHolder> holders;
    private ViewpagerFragmentAdapter adapter;
    /**
     * 显示Fragment索引  默认  0
     */
    private int defIdx = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_sz, null);
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
        sz_tablayout  = (TabLayout) view.findViewById(R.id.sz_tablayout);
        vp_sz = (ViewPager) view.findViewById(R.id.vp_sz);
    }

    @Override
    public void initData() {
        holders = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            FragmentHolder holder = new FragmentHolder();
            holder.setTitle("TAB"+i);
            holder.setFragment(SzListFragment.newInstance(holder.getTitle(),1));
            holders.add(holder);
        }
        adapter = new ViewpagerFragmentAdapter(getFragmentManager(), holders);
        vp_sz.setOffscreenPageLimit(3);
        vp_sz.setAdapter(adapter);
        sz_tablayout.setupWithViewPager(vp_sz);
        if (defIdx >= holders.size()) defIdx = 0;
        vp_sz.setCurrentItem(defIdx);
    }

    @Override
    public void setListener() {

    }

    public void getBundleData() {

        Bundle data = mActivity.getIntent().getBundleExtra("data");
        if (data != null) {
            defIdx = data.getInt("typeIdx", 0);
        }
    }
}
