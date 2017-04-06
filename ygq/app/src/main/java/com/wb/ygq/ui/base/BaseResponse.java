package com.wb.ygq.ui.base;

/**
 * Description：
 * Created on 2017/4/6
 */
public class BaseResponse {
    private String Msg;
    private String Count;

    public BaseResponse() {
        super();
    }

    public BaseResponse(String msg, String count) {
        Msg = msg;
        Count = count;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "Msg='" + Msg + '\'' +
                ", Count='" + Count + '\'' +
                '}';
    }
}
