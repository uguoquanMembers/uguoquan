package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

/**
 * Description：
 * Created on 2017/4/13
 */
public class ClassifyVideoResponseBean extends BaseResponse {
    private ClassifyVideoBean Data;

    public ClassifyVideoResponseBean() {
        super();
    }

    public ClassifyVideoResponseBean(ClassifyVideoBean data) {
        Data = data;
    }

    public ClassifyVideoBean getData() {
        return Data;
    }

    public void setData(ClassifyVideoBean data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "ClassifyVideoResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
