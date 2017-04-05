package com.wb.ygq.ui.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.callback.RecyclerViewItemClickListener;
import com.wb.ygq.ui.adapter.VideoAdapter;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.utils.MyUtil;
import com.wb.ygq.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Created on 2017/4/2
 */
public class VideoFragment extends BaseFragment implements RecyclerViewItemClickListener {

    private View view;
    private RecyclerView recycle_video;
    private VideoAdapter adapter;
    /**
     * 存储数据
     */
    private List<CeshiBean> dataList = new ArrayList<>();
    private ImageView ima_videohead_left;
    private ImageView ima_videohead_right;
    private LinearLayout ll_video_addvp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_video, null);
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
        recycle_video = (RecyclerView) view.findViewById(R.id.recycle_video);
    }

    @Override
    public void initData() {
        getceshiData();
        adapter = new VideoAdapter(mActivity);
        View headView = getHeadView();
        if (headView != null) {
            adapter.setHeadView(headView);
        }
        recycle_video.setHasFixedSize(true);
        recycle_video.setLayoutManager(new GridLayoutManager(mActivity,1));
        adapter.setItemClickListener(this);
        recycle_video.setAdapter(adapter);
        adapter.updateItems(dataList);
    }

    @Override
    public void setListener() {
        ima_videohead_left.setOnClickListener(this);
        ima_videohead_right.setOnClickListener(this);
    }

    /**
     * 头布局
     *
     * @return
     */
    public View getHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.headview_video_fm, null);
        ll_video_addvp = (LinearLayout) headView.findViewById(R.id.ll_video_addvp);
        MyUtil.showLog("屏幕的宽度为===" + ll_video_addvp.getWidth());
        ima_videohead_left = (ImageView) headView.findViewById(R.id.ima_videohead_left);
        ima_videohead_right = (ImageView) headView.findViewById(R.id.ima_videohead_right);
        return headView;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ima_videohead_left://统一跳转
            case R.id.ima_videohead_right:
                ToastUtil.showToast("吐司啦啦啦");
                break;

            default:
                break;
        }
    }

    /**
     * iten点击事件
     *
     * @param view
     * @param o
     * @param position
     * @param eventType
     */
    @Override
    public void onItemClick(View view, Object o, int position, int eventType) {

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
            cb.setIma("http://shtml.asia-cloud.com/ZZSY/list_test1.png");
            cb.setName("卧槽sb崔" + i);
            cb.setNum(13 + i);
            dataList.add(cb);
        }

    }
}
