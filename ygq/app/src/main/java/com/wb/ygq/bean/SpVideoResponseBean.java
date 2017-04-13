package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

/**
 * Description：
 * Created on 2017/4/13
 */
public class SpVideoResponseBean extends BaseResponse {
    private SpVideoBean Data;

    public SpVideoResponseBean() {
        super();
    }

    public SpVideoResponseBean(SpVideoBean data) {
        Data = data;
    }

    public SpVideoBean getData() {
        return Data;
    }

    public void setData(SpVideoBean data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "SpVideoResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
