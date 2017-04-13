package com.wb.ygq.bean;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/13
 */
public class SpVideoBean {
    private List<VideoFMBean.DataBean.VideoListBean> videoList;

    public SpVideoBean() {
        super();
    }

    public SpVideoBean(List<VideoFMBean.DataBean.VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public List<VideoFMBean.DataBean.VideoListBean> getVideoList() {
        return videoList;
    }

    @Override
    public String toString() {
        return "SpVideoBean{" +
                "videoList=" + videoList +
                '}';
    }

    public void setVideoList(List<VideoFMBean.DataBean.VideoListBean> videoList) {
        this.videoList = videoList;

    }
}
