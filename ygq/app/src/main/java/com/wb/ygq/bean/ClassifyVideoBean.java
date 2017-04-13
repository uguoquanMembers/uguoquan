package com.wb.ygq.bean;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/13
 */
public class ClassifyVideoBean {
    private List<VideoBannerBean> carouselList;
    private List<VideoFMBean.DataBean.VideoListBean> videoList;

    public ClassifyVideoBean() {
        super();
    }

    public ClassifyVideoBean(List<VideoBannerBean> carouselList, List<VideoFMBean.DataBean.VideoListBean> videoList) {
        this.carouselList = carouselList;
        this.videoList = videoList;
    }

    public List<VideoBannerBean> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<VideoBannerBean> carouselList) {
        this.carouselList = carouselList;
    }

    public List<VideoFMBean.DataBean.VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoFMBean.DataBean.VideoListBean> videoList) {
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "ClassifyVideoBean{" +
                "carouselList=" + carouselList +
                ", videoList=" + videoList +
                '}';
    }
}
