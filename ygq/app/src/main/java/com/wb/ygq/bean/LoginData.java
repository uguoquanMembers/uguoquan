package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/10
 */
public class LoginData {
    private String isUpdate;
    private String type;
    private String download;
    private String userid;

    public LoginData() {
        super();
    }

    public LoginData(String isUpdate, String type, String download, String userid) {
        this.isUpdate = isUpdate;
        this.type = type;
        this.download = download;
        this.userid = userid;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "isUpdate='" + isUpdate + '\'' +
                ", type='" + type + '\'' +
                ", download='" + download + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
