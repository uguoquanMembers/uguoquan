package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/12
 * Author : 郭
 */
public class CollectVideoResponseBean extends BaseResponse {
    private List<CollcetVideoBean> Data;

    public CollectVideoResponseBean() {
        super();
    }

    public CollectVideoResponseBean(List<CollcetVideoBean> data) {
        Data = data;
    }

    public List<CollcetVideoBean> getData() {
        return Data;
    }

    public void setData(List<CollcetVideoBean> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "CollectVideoResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
