package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/11
 */
public class CollentResponseBean extends BaseResponse {
    private List<CollentBean> Data;

    public CollentResponseBean() {
        super();
    }

    public CollentResponseBean(List<CollentBean> data) {
        Data = data;
    }

    public List<CollentBean> getData() {
        return Data;
    }

    public void setData(List<CollentBean> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "CollentResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
