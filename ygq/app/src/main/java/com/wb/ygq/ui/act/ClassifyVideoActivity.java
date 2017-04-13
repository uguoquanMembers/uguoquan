package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.fm.ClassifyBjFragment;
import com.wb.ygq.ui.fm.ClassifySkFragment;
import com.wb.ygq.ui.fm.ClassifyZsFragment;
import com.wb.ygq.ui.fm.CollectFriendFragment;
import com.wb.ygq.ui.fm.CollectPhotoFragment;
import com.wb.ygq.ui.fm.CollectVideoFragment;

/**
 * Description： 全部分类
 * Created on 2017/4/13
 */
public class ClassifyVideoActivity extends BaseActivity {
    private TabLayout sp_tablayout;
    private ViewPager sp_viewpager;
    private Toolbar toolbar;
    private View view;
    private TextView rl_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_sp);
        initTitle();
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_title = (TextView) findViewById(R.id.rl_title);
        rl_title.setText("视频分类");
    }

    @Override
    public void initView() {
        sp_viewpager = (ViewPager) findViewById(R.id.sp_viewpager);
        sp_tablayout = (TabLayout) findViewById(R.id.sp_tablayout);
        view = findViewById(R.id.view);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        sp_tablayout.addTab(sp_tablayout.newTab().setText("试看"));
        sp_tablayout.addTab(sp_tablayout.newTab().setText("白金"));
        sp_tablayout.addTab(sp_tablayout.newTab().setText("钻石"));
        sp_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), sp_tablayout.getTabCount());
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
                    return new ClassifySkFragment();
                case 1:
                    return new ClassifyBjFragment();
                case 2:
                    return new ClassifyZsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
