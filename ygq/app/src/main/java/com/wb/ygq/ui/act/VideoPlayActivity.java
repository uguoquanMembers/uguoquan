package com.wb.ygq.ui.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoPlayAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.PublicUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayActivity extends BaseActivity implements RecyclerViewItemClickListener {
    /**
     * 评论
     */
    private RecyclerView recycle_comment;

    private VideoPlayAdapter adapter;
    private TextView tv_input;
    /**
     * 存储更多内容
     */
    private List<CeshiBean> llList = new ArrayList<>();
    private List<CeshiBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        recycle_comment = (RecyclerView) findViewById(R.id.recycle_comment);
        tv_input = (TextView) findViewById(R.id.tv_input);
    }

    @Override
    public void initData() {
        getceshiData();
        adapter = new VideoPlayAdapter(this);
        adapter.setHeadView(getHeadView());
        recycle_comment.setHasFixedSize(true);
        recycle_comment.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemClickListener(this);
        recycle_comment.setAdapter(adapter);
        adapter.updateItems(dataList);

    }


    @Override
    public void setListener() {
        tv_input.setOnClickListener(this);
        adapter.setItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_input:

                break;

            default:
                break;
        }
    }

    /**
     * 头布局
     *
     * @return
     */
    public View getHeadView() {
        View v = LayoutInflater.from(this).inflate(R.layout.headview_videopalyer, null);
        LinearLayout ll_moreaddview = (LinearLayout) v.findViewById(R.id.ll_moreaddview);
        TextView tv_title_num = (TextView) v.findViewById(R.id.tv_title_num);
        for (int i = 0; i < llList.size(); i++) {
            ll_moreaddview.addView(foramtSlideView(i));

        }
        String str = "\n我是傻逼崔";
        tv_title_num.setText(PublicUtil.formatTextView(this, "title", str, R.style.textstyle_14_666666, R.style.textstyle_10_5DBCF4, str.length()));
        return v;
    }

    /**
     * 天假左右滑动数据
     *
     * @param i
     * @return
     */
    private View foramtSlideView(int i) {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_ima_twotext, null);
        ImageView image_head = (ImageView) v.findViewById(R.id.image_head);
        TextView text_title = (TextView) v.findViewById(R.id.text_title);
        TextView text_content = (TextView) v.findViewById(R.id.text_content);
        Glide.with(this).load(llList.get(i).getIma()).into(image_head);
        text_content.setText(llList.get(i).getName());
        text_title.setText(llList.get(i).getName());
        return v;
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
            llList.add(cb);
            dataList.add(cb);
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
        MyUtil.showLog("点击====" + position);
    }
}
