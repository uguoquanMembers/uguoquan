package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

/**
 * Description：
 * Created on 2017/4/11
 */
public class CollentResponseBean extends BaseResponse {
    private CollentBean Data;

    public CollentResponseBean() {
        super();
    }

    public CollentResponseBean(CollentBean data) {
        Data = data;
    }

    public CollentBean getData() {
        return Data;
    }

    public void setData(CollentBean data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "CollentResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
