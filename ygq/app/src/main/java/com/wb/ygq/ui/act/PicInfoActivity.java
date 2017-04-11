package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.ImgListBean;
import com.wb.ygq.ui.adapter.ImagePagerAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PicInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_picinfo;
    private TextView tv_title,tv_comment_count,tv_collect_count,tv_praise_count;
    private RelativeLayout rl_top_layout;
    private LinearLayout ll_bottom_layout;

    private List<String> picPathes;
    private int currentPage;
    private String id;
    private ImagePagerAdapter adapter;
    private ImgListBean mImgListBean;
    private float x;
    private float moveX;


    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        vp_picinfo = (ViewPager) findViewById(R.id.vp_picinfo);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_top_layout = (RelativeLayout) findViewById(R.id.rl_top_layout);
        ll_bottom_layout = (LinearLayout) findViewById(R.id.ll_bottom_layout);
        tv_comment_count= (TextView) findViewById(R.id.tv_comment_count);
        tv_collect_count= (TextView) findViewById(R.id.tv_collect_count);
        tv_praise_count= (TextView) findViewById(R.id.tv_praise_count);

//        vp_picinfo.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (currentPage == 0) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            x = event.getX();
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            moveX = event.getX();
//                            if (moveX - x < 0) {
//                                Toast.makeText(PicInfoActivity.this, "该交钱啦", Toast.LENGTH_SHORT).show();
//                            }
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            break;
//
//                    }
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(PubConst.DATA);
        id = bundle.getString("id");
        picPathes=new ArrayList<>();
        getNetDatas();

    }

    /**
     * 获取网络数据
     */
    public void getNetDatas() {
        OkHttpUtils.get()
                .url(String.format(HttpUrl.API.GET_IMG_LIST, id))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(final Response response) throws IOException {

                        String data = null;
                        data = response.body().string();
                        final String finalData = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImgListBean = new Gson().fromJson(finalData, ImgListBean.class);
                                for (int i = 0; i < mImgListBean.getData().getOrderimg().size(); i++) {
                                    picPathes.add(mImgListBean.getData().getOrderimg().get(i).getUrl());
                                }
                                adapter = new ImagePagerAdapter(PicInfoActivity.this, picPathes);
                                vp_picinfo.setAdapter(adapter);
                                vp_picinfo.setCurrentItem(currentPage);
                                vp_picinfo.setOnPageChangeListener(PicInfoActivity.this);
                                tv_title.setText((currentPage + 1) + "/" + picPathes.size());
                            }
                        });

                        return null;
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });
    }

    public void isShowTitle(boolean flag) {
        if (flag) {
            rl_top_layout.setVisibility(View.VISIBLE);
            ll_bottom_layout.setVisibility(View.VISIBLE);
        } else {
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
        currentPage = position;
        tv_title.setText((currentPage + 1) + "/" + picPathes.size());
//        tv_collect_count.setText(mImgListBean.getData().getCharge();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
