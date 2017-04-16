package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wb.ygq.R;
import com.wb.ygq.ui.adapter.ImagePagerAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;

import java.util.List;

public class BigPicActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_pic;
    private TextView tv_title;

    private List<String> picPath;
    private int curPosition;
    private ImagePagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        initView();
        initData();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        vp_pic = (ViewPager) findViewById(R.id.vp_pic);
        tv_title = (TextView) findViewById(R.id.tv_title);

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(PubConst.DATA);
        curPosition = bundle.getInt("position");
        picPath = bundle.getStringArrayList("picPath");
        adapter = new ImagePagerAdapter(this, picPath);
        vp_pic.setAdapter(adapter);
        vp_pic.setCurrentItem(curPosition);
        vp_pic.addOnPageChangeListener(this);
        tv_title.setText((curPosition+1)+"/"+picPath.size());
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_title.setText((position+1)+"/"+picPath.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
