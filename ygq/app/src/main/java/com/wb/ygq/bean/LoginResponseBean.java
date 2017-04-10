package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

/**
 * Description：
 * Created on 2017/4/10
 */
public class LoginResponseBean extends BaseResponse {
    private LoginData Data;

    public LoginResponseBean() {
        super();
    }

    public LoginResponseBean(LoginData data) {
        Data = data;
    }

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "LoginResponseBean{" +
                "Data=" + Data +
                "} " + super.toString();
    }
}
