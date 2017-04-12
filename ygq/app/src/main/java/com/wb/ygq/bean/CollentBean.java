package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/11
 */
public class CollentBean {
    private String img;
    private String id;

    public CollentBean() {
        super();
    }

    public CollentBean(String img, String id) {
        this.img = img;
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CollentBean{" +
                "img='" + img + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
