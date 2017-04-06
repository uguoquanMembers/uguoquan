package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.adapter.ImagePagerAdapter;
import com.wb.ygq.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PicInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_picinfo;
    private TextView tv_title;
    private RelativeLayout rl_top_layout;
    private LinearLayout ll_bottom_layout;

    private List<String> picPathes;
    private int currentPage;
    private ImagePagerAdapter adapter;


    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        vp_picinfo= (ViewPager) findViewById(R.id.vp_picinfo);
        tv_title= (TextView) findViewById(R.id.tv_title);
        rl_top_layout= (RelativeLayout) findViewById(R.id.rl_top_layout);
        ll_bottom_layout= (LinearLayout) findViewById(R.id.ll_bottom_layout);
    }

    @Override
    public void initData() {
        picPathes=getIntent().getStringArrayListExtra("pathList");
        currentPage=getIntent().getIntExtra("position",0);
        if (picPathes==null||picPathes.size()<1){
            picPathes=new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                picPathes.add("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
            }
        }
        adapter=new ImagePagerAdapter(this,picPathes);
        vp_picinfo.setAdapter(adapter);
        vp_picinfo.setCurrentItem(currentPage);
        vp_picinfo.setOnPageChangeListener(this);
        tv_title.setText((currentPage+1)+"/"+picPathes.size());
    }

    public void isShowTitle(boolean flag){
        if (flag){
            rl_top_layout.setVisibility(View.VISIBLE);
            ll_bottom_layout.setVisibility(View.VISIBLE);
        }else {
            rl_top_layout.setVisibility(View.GONE);
            ll_bottom_layout.setVisibility(View.GONE);
        }

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_info);
        initView();
        initData();
        initTitle();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage=position;
        tv_title.setText((currentPage+1)+"/"+picPathes.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
