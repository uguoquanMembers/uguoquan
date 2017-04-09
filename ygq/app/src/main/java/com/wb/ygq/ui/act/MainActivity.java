package com.wb.ygq.ui.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.ui.fm.HomeFragment;
import com.wb.ygq.ui.fm.MineFragment;
import com.wb.ygq.ui.fm.SearchFragment;
import com.wb.ygq.ui.fm.SpFragment;
import com.wb.ygq.ui.fm.SzFragment;
import com.wb.ygq.ui.fm.VideoFragment;
import com.wb.ygq.ui.utils.DialogUtil;
import com.wb.ygq.ui.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public static MainActivity instance;

    private static long firstExitTime = 0;

    private RadioGroup rg;

    /**
     * 主页
     */
    private HomeFragment homeFragment;
    private static final String HOMEFRAGMENT_TAG = "homeFragment";
//    首页  私照  视频  私拍  搜索    我的
    /**
     * 第二页fragment
     */
    private SzFragment szFragment;
    private static final String SZFRAGMENT_TAG = "SZFragment_TAG";
    /**
     * 第三页fragment
     */
    private VideoFragment videoFragment;
    private static final String VIDEOFRAGMENT_TAG = "VideoFragment_TAG";
    /**
     * 第四页fragment
     */
    private SpFragment spFragment;
    private static final String SPFRAGMENT_TAG = "SPFragment_TAG";
    /**
     * 第五页fragment
     */
    private SearchFragment searchFragment;
    private static final String SEARCHFRAGMENT_TAG = "SearchFragment_TAG";

    /**
     * 我的fragment
     */
    private MineFragment mineFragment;
    private static final String MINEFRAGMENT_TAG = "mineFragment";

    /**
     * 当前选择
     */
    private int curIndex = 0;

    /**
     * 是否有显示的fragment
     */
    private String showFragment = "";

    public int unReadNum = 0;

    private RadioButton[] radioViews;
    /**
     * 标题
     */
    private RelativeLayout rl_title;

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    /**
     * 抽屉按钮
     */
    private TextView tv_vip, tv_buy, tv_kf, tv_versions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {// 判断是恢复状态情况，还是重新建立情况  首页  私照  视频  私拍  搜索    我的
            FragmentManager fragmentManager = getSupportFragmentManager();
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(HOMEFRAGMENT_TAG);
            szFragment = (SzFragment) fragmentManager.findFragmentByTag(SZFRAGMENT_TAG);
            videoFragment = (VideoFragment) fragmentManager.findFragmentByTag(VIDEOFRAGMENT_TAG);
            spFragment = (SpFragment) fragmentManager.findFragmentByTag(SPFRAGMENT_TAG);
            searchFragment = (SearchFragment) fragmentManager.findFragmentByTag(SEARCHFRAGMENT_TAG);
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag(MINEFRAGMENT_TAG);
            showFragment = savedInstanceState.getString("showFragment");
        }
        initTitle();
        initView();
        initData();
        int sex = SharedUtil.getInt(PubConst.KEY_SEX, 0);
        if (sex == 0) //没存 弹出男女
        {
            DialogUtil.showSex(this, "取消", "确定");
        }
        setListener();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void initTitle() {
//        setAn(true);
    }

    public void initView() {
        rg = (RadioGroup) this.findViewById(R.id.tabs_rg);
        RadioButton tab_home = (RadioButton) findViewById(R.id.tab_home);
        RadioButton tab_sz = (RadioButton) findViewById(R.id.tab_sz);
        RadioButton tab_video = (RadioButton) findViewById(R.id.tab_video);
        RadioButton tab_sp = (RadioButton) findViewById(R.id.tab_sp);
        RadioButton tab_search = (RadioButton) findViewById(R.id.tab_search);
        RadioButton tab_wd = (RadioButton) findViewById(R.id.tab_wd);
        radioViews = new RadioButton[]{tab_home, tab_sz, tab_video, tab_sp, tab_search, tab_wd};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);

        tv_vip = (TextView) findViewById(R.id.tv_vip);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_kf = (TextView) findViewById(R.id.tv_kf);
        tv_versions = (TextView) findViewById(R.id.tv_versions);

        initToolbar();
        //默认标题
        setToolTitle(0);
    }

    /**
     * 设置标题
     */
    private void initToolbar() {
        //实现了监听的开关 ，最后2个参数可以写0
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();//同步drawerLayout
        //给drawerlayout添加监听
        drawerLayout.addDrawerListener(toggle);
    }

    //drawerlayout实现侧拉还要添加下面这行代码
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initData() {
        instance = this;
        if (homeFragment == null) homeFragment = new HomeFragment();// 主页
        if (szFragment == null) szFragment = new SzFragment();
        if (videoFragment == null) videoFragment = new VideoFragment();
        if (spFragment == null) spFragment = new SpFragment();
        if (searchFragment == null) searchFragment = new SearchFragment();
        if (mineFragment == null) mineFragment = new MineFragment();// 我的
        fragmentList.add(homeFragment);
        fragmentList.add(szFragment);
        fragmentList.add(videoFragment);
        fragmentList.add(spFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(mineFragment);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (showFragment != null && !"".equals(showFragment)) {// 是恢复的，判断是否是已经添加过fragment
            for (Fragment f : fragmentList) {
                ft.hide(f);
            }
            if (showFragment.equals(HOMEFRAGMENT_TAG)) {
                ft.show(homeFragment).commit();
                return;
            }
            if (showFragment.equals(SZFRAGMENT_TAG)) {
                ft.show(szFragment).commit();
                return;
            }
            if (showFragment.equals(VIDEOFRAGMENT_TAG)) {
                ft.show(videoFragment).commit();
                return;
            }
            if (showFragment.equals(SPFRAGMENT_TAG)) {
                ft.show(spFragment).commit();
                return;
            }
            if (showFragment.equals(SEARCHFRAGMENT_TAG)) {
                ft.show(searchFragment).commit();
                return;
            }
            if (showFragment.equals(MINEFRAGMENT_TAG)) {
                ft.show(mineFragment).commit();
                return;
            }
        } else {// 是新建的
            if (!homeFragment.isAdded())
                ft.add(R.id.realtabcontent, homeFragment, HOMEFRAGMENT_TAG).commit();
        }
    }

    @Override
    public void setListener() {
        rg.setOnCheckedChangeListener(this);
        //如果是下面这种，就不需要设置toggle.onOptionsItemSelected(item);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        tv_vip.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        tv_kf.setOnClickListener(this);
        tv_versions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_vip: //加入vip
                skip(RechargeActivity.class, false);
                break;
            case R.id.tv_buy:// 已购买的
                skip(AlreadyBuyActivity.class, false);
//                skip(VideoPlayActivity.class,false);
                break;
            case R.id.tv_kf://客服
//                skip(PicInfoActivity.class, false);
                break;
            case R.id.tv_versions://版本

                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        fragmentList.get(curIndex).onPause();
        ft.hide(fragmentList.get(curIndex));
        BaseFragment fragment;
        String fragmentTag;
        switch (checkedId) {
            case R.id.tab_home:// 首页选择
                curIndex = 0;
                fragment = homeFragment;
                fragmentTag = HOMEFRAGMENT_TAG;
                setTextColor(0);
                break;
            case R.id.tab_sz:
                curIndex = 1;
                fragment = szFragment;
                fragmentTag = SZFRAGMENT_TAG;
                setTextColor(1);
                break;
            case R.id.tab_video:
                curIndex = 2;
                fragment = videoFragment;
                fragmentTag = VIDEOFRAGMENT_TAG;
                setTextColor(2);
                break;
            case R.id.tab_sp:
                curIndex = 3;
                fragment = spFragment;
                fragmentTag = SPFRAGMENT_TAG;
                setTextColor(3);
                break;
            case R.id.tab_search:

                curIndex = 4;
                fragment = searchFragment;
                fragmentTag = SEARCHFRAGMENT_TAG;
                setTextColor(4);
                break;
            case R.id.tab_wd:// 选择我的
                curIndex = 5;
                fragment = mineFragment;
                fragmentTag = MINEFRAGMENT_TAG;
                setTextColor(5);
                break;

            default:
                curIndex = 0;
                fragment = homeFragment;
                fragmentTag = HOMEFRAGMENT_TAG;
                setTextColor(0);
                break;
        }
        if (fragment.isAdded()) {
            fragment.onResume();
        } else {
            ft.add(R.id.realtabcontent, fragment, fragmentTag);
        }
        ft.show(fragment);
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        // 存储当前显示的fragment的tag 首页  私照  视频  私拍  搜索    我的
        if (homeFragment.isVisible()) {
            outState.putString("showFragment", HOMEFRAGMENT_TAG);
            return;
        }
        if (szFragment.isVisible()) {
            outState.putString("showFragment", SZFRAGMENT_TAG);
            return;
        }
        if (videoFragment.isVisible()) {
            outState.putString("showFragment", VIDEOFRAGMENT_TAG);
            return;
        }
        if (spFragment.isVisible()) {
            outState.putString("showFragment", SPFRAGMENT_TAG);
            return;
        }
        if (searchFragment.isVisible()) {
            outState.putString("showFragment", SEARCHFRAGMENT_TAG);
            return;
        }
        if (mineFragment.isVisible()) {
            outState.putString("showFragment", MINEFRAGMENT_TAG);
            return;
        }
    }


    /**
     * 设置tab颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        //表题
        setToolTitle(textColor);
        //tab设置颜色
        for (int i = 0; i < radioViews.length; i++) {
            if (i == textColor) {
                radioViews[i].setTextColor(getResources().getColor(R.color.color_press));
            } else {
                radioViews[i].setTextColor(getResources().getColor(R.color.color_666666));
            }
        }
    }

    /**
     * 标题颜色
     *
     * @param textColor
     */
    public void setToolTitle(int textColor) {
        rl_title.removeAllViews();
        TextView tv = new TextView(this);
        tv.setText(radioViews[textColor].getText());
        tv.setTextSize(16);
        tv.setTextColor(getResources().getColor(R.color.color_333333));
        rl_title.addView(tv);
    }
}
