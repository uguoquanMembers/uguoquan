package com.wb.ygq.bean;

/**
 * Created by Sivin on 2017/4/1.
 */

public class IBannerBean extends IBanner {


    private String bannerLinkId;

    private String bannerType;


    public IBannerBean() {
        super();
    }

    public IBannerBean(String bannerLinkId, String bannerType) {
        this.bannerLinkId = bannerLinkId;
        this.bannerType = bannerType;
    }

    public String getBannerLinkId() {
        return bannerLinkId;
    }

    public void setBannerLinkId(String bannerLinkId) {
        this.bannerLinkId = bannerLinkId;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    @Override
    public String toString() {
        return "IBannerBean{" +
                "bannerLinkId='" + bannerLinkId + '\'' +
                ", bannerType='" + bannerType + '\'' +
                "} " + super.toString();
    }
}
