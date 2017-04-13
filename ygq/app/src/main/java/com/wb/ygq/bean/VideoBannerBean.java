package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/13
 */
public class VideoBannerBean {
    private String img;
    private String go;

    public VideoBannerBean() {
        super();
    }

    public VideoBannerBean(String img, String go) {
        this.img = img;
        this.go = go;
    }

    public String getImg() {

        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    @Override
    public String toString() {
        return "VideoBannerBean{" +
                "img='" + img + '\'' +
                ", go='" + go + '\'' +
                '}';
    }
}
