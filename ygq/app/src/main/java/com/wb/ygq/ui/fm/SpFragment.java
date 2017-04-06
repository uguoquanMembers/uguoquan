package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseFragment;

/**
 * Description： 私拍
 * Created on 2017/4/2
 */
public class SpFragment extends BaseFragment {

    private View view;

    private TabLayout sp_tablayout;
    private ViewPager sp_viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_sp, null);
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
        sp_viewpager = (ViewPager) view.findViewById(R.id.sp_viewpager);
        sp_tablayout = (TabLayout) view.findViewById(R.id.sp_tablayout);
    }

    @Override
    public void initData() {
        sp_tablayout.addTab(sp_tablayout.newTab().setText("自拍"));
        sp_tablayout.addTab(sp_tablayout.newTab().setText("直播"));
        sp_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), sp_tablayout.getTabCount());
        sp_viewpager.setAdapter(adapter);
        sp_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sp_tablayout));
        sp_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                sp_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void setListener() {

    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private int tabCount;

        public PagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SpPhotoFragment();
                case 1:

                    return new SpVideoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
