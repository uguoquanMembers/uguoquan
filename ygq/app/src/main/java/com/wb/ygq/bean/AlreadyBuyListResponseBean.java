package com.wb.ygq.bean;


import com.wb.ygq.ui.base.BaseResponse;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/14
 */
public class AlreadyBuyListResponseBean extends BaseResponse {
    private List<AlreadyBuyBean> Data;

    public AlreadyBuyListResponseBean() {
        super();
    }

    public AlreadyBuyListResponseBean(List<AlreadyBuyBean> data) {
        Data = data;
    }

    public List<AlreadyBuyBean> getData() {
        return Data;
    }

    public void setData(List<AlreadyBuyBean> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "AlreadyBuyListResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
