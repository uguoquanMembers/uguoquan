package com.wb.ygq.bean;

import com.wb.ygq.ui.base.BaseResponse;

/**
 * Description：
 * Created on 2017/4/16
 */
public class AddCollenResponseBeant extends BaseResponse {
    private String message;

    public AddCollenResponseBeant() {
        super();
    }

    public AddCollenResponseBeant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AddCollenResponseBeant{" +
                "message='" + message + '\'' +
                "} " + super.toString();
    }
}
