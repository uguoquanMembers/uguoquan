package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.adapter.ImagePagerAdapter;
import com.wb.ygq.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PicInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_picinfo;
    private TextView tv_title;

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
