package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/6
 * Author : 郭
 */
public class SpFriendListResponseBean extends BaseResponse {
    private List<FriendListBean> Data;

    public SpFriendListResponseBean() {
        super();
    }

    public SpFriendListResponseBean(List<FriendListBean> data) {
        Data = data;
    }

    public List<FriendListBean> getData() {
        return Data;
    }

    public void setData(List<FriendListBean> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "SpFriendListResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
