package com.wb.ygq.ui.act;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.ImgListBean;
import com.wb.ygq.ui.adapter.ImagePagerAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;
import com.wb.ygq.utils.VipDialog;
import com.wb.ygq.widget.HorizontalProgressBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PicInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_picinfo;
    private TextView tv_comment_count, tv_collect_count, tv_praise_count, tv_comment;
    private RelativeLayout rl_top_layout, rl_container;
    private LinearLayout ll_bottom_layout, ll_container;
    private HorizontalProgressBar pb_bar1, pb_bar2;

    private List<String> picPathes;
    private List<String> freePicPath;
    private int currentPage;
    private int freeMax;
    private String id;
    private ImagePagerAdapter adapter;
    private ImgListBean mImgListBean;
    private float x;
    private float moveX;
    private String vipRange;
    private boolean flag;

    //弹幕相关
    private View view;
    private int removeCount = 0;
    private boolean isStop;
    private List<ImgListBean.DataBean.CommentListBean> datas;
    private List<ImgListBean.DataBean.CommentListBean> allData;
    private int initCount;
    //输入框
    private RelativeLayout rl_input;
    private EditText et_input;
    private TextView tv_button_send;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (initCount < allData.size()) {
                view = LayoutInflater.from(PicInfoActivity.this).inflate(R.layout.item_content, null);
                TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
                tv_msg.setText(allData.get(initCount).getMessage());
                ImageView iv_head = (ImageView) view.findViewById(R.id.iv_head);
                view.setPadding(0, 10, 10, 10);
                if (!TextUtils.isEmpty(allData.get(initCount).getImg())) {
                    Glide.with(PicInfoActivity.this).load(allData.get(initCount).getImg()).into(iv_head);
                }
                ll_container.addView(view);
                initCount++;
            } else {
                view = LayoutInflater.from(PicInfoActivity.this).inflate(R.layout.item_blank_msg, null);
                view.setPadding(0, 10, 10, 10);
                ll_container.addView(view);
            }

            if (rl_container.getHeight() < ll_container.getChildCount() * 90) {
                ll_container.removeViewAt(0);
                removeCount++;
            }
            if (removeCount <= initCount) {
                mHandler.sendEmptyMessageDelayed(1, 1500);
            } else {
                isStop = true;
                ll_container.removeAllViews();
            }
        }
    };

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        vipRange = SharedUtil.getString("vip", "");

        vp_picinfo = (ViewPager) findViewById(R.id.vp_picinfo);
        vp_picinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x = event.getX();

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    moveX = event.getX();
                    if ("".equals(vipRange) && (currentPage + 1) == freeMax) {
                        if (x - moveX > 100) {
                            VipDialog.showVipDialog(PicInfoActivity.this,true);
                        }
                    }
                    if (x - moveX < 20 && x - moveX >= 0) {
                        if (flag) {
                            isShowTitle(true);
                            flag = false;
                        } else {
                            isShowTitle(false);
                            flag = true;
                        }
                    }

                }
                return false;
            }
        });
//        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_top_layout = (RelativeLayout) findViewById(R.id.rl_top_layout);
        ll_bottom_layout = (LinearLayout) findViewById(R.id.ll_bottom_layout);
        tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
        tv_collect_count = (TextView) findViewById(R.id.tv_collect_count);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        tv_praise_count = (TextView) findViewById(R.id.tv_praise_count);
        pb_bar1 = (HorizontalProgressBar) findViewById(R.id.pb_bar1);
        pb_bar2 = (HorizontalProgressBar) findViewById(R.id.pb_bar2);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container);

        tv_button_send = (TextView) findViewById(R.id.tv_button_send);
        rl_input = (RelativeLayout) findViewById(R.id.rl_input);
        et_input = (EditText) findViewById(R.id.et_input);


        allData = new ArrayList<>();
        datas = new ArrayList<>();
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getBundleExtra(PubConst.DATA);
        id = bundle.getString("id");
        picPathes = new ArrayList<>();
        freePicPath = new ArrayList<>();
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
                                    if (2 == mImgListBean.getData().getOrderimg().get(i).getIscharge()) {
                                        freePicPath.add(mImgListBean.getData().getOrderimg().get(i).getUrl());
                                    }
                                }
                                freeMax = freePicPath.size();
                                pb_bar1.setText("1", picPathes.size() < 10 ? "0" + picPathes.size() : picPathes.size() + "");
                                pb_bar2.setText("1", freePicPath.size() < 10 ? "0" + freePicPath.size() : freePicPath.size() + "");
                                if ("".equals(vipRange)) {
                                    pb_bar2.setMax(freePicPath.size() - 1);
                                    adapter = new ImagePagerAdapter(PicInfoActivity.this, freePicPath);
                                } else {
                                    pb_bar2.setVisibility(View.GONE);
                                    pb_bar1.setMax(picPathes.size() - 1);
                                    adapter = new ImagePagerAdapter(PicInfoActivity.this, picPathes);
                                }
                                vp_picinfo.setAdapter(adapter);
                                vp_picinfo.setCurrentItem(currentPage);
                                vp_picinfo.setOnPageChangeListener(PicInfoActivity.this);
//                                tv_title.setText((currentPage + 1) + "/" + picPathes.size());
                                //弹幕相关
                                datas.addAll(mImgListBean.getData().getCommentList());
                                allData.addAll(datas);
                                mHandler.sendEmptyMessage(1);
                                tv_comment_count.setText(mImgListBean.getData().getComment());
                                tv_praise_count.setText(mImgListBean.getData().getFabulous());
                                tv_collect_count.setText(mImgListBean.getData().getCollection());
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
        tv_comment.setOnClickListener(this);
        tv_button_send.setOnClickListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_info);
        initView();
        initData();
        initTitle();
        setListener();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        if ("".equals(vipRange)) {
            pb_bar2.setProgress(position);
//            pb_bar1.setProgress(position + 1);
        } else {
            pb_bar1.setProgress(position);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_comment:
                inPutComment();
                break;
            case R.id.tv_button_send://发送按钮
                String etString = et_input.getText().toString().trim();
                if (TextUtils.isEmpty(etString)) {
                    ToastUtil.showToast("请输入您的评论内容");
                    return;
                } else {
                    saveTOList(etString);
                }
                break;

        }
    }

    /**
     * 输入评论
     */
    private void inPutComment() {
        rl_input.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.requestFocus();
        et_input.setText("");
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 存到集合
     *
     * @param etString
     */
    private void saveTOList(String etString) {
        ImgListBean.DataBean.CommentListBean cb = new ImgListBean.DataBean.CommentListBean();
        cb.setImg(HttpUrl.API.HEAD);
        cb.setMessage(etString);
        cb.setName("我");
        cb.setTime("刚刚");
        if (isStop) {
            allData.clear();
            allData.add(cb);
            datas.add(cb);
            initCount = 0;
            removeCount = 0;
            mHandler.sendEmptyMessage(1);
            isStop = false;
        } else {
            allData.add(cb);
        }
        String s = tv_comment_count.getText().toString().toString();
        tv_comment_count.setText((Integer.valueOf(s) + 1) + "");
        rl_input.setVisibility(View.GONE);
        et_input.setText("");
        colseKeyBoard();


    }

    /**
     * 关闭软键盘
     */
    private void colseKeyBoard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
