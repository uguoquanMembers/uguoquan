package com.wb.ygq.bean;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/11
 */
public class CollentBean {
    private List<String> img;

    public CollentBean() {
        super();
    }

    public CollentBean(List<String> img) {
        this.img = img;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "CollentBean{" +
                "img=" + img +
                '}';
    }
}
