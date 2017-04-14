package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/14
 */
public class AlreadyBuyBean {
    private int transaction_fee;
    private String created_at;
    private String vid;
    private String type;
    private String name;

    public AlreadyBuyBean() {
        super();
    }

    public AlreadyBuyBean(int transaction_fee, String created_at, String vid, String type, String name) {
        this.transaction_fee = transaction_fee;
        this.created_at = created_at;
        this.vid = vid;
        this.type = type;
        this.name = name;
    }

    public int getTransaction_fee() {
        return transaction_fee;
    }

    public void setTransaction_fee(int transaction_fee) {
        this.transaction_fee = transaction_fee;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlreadyBuyBean{" +
                "transaction_fee=" + transaction_fee +
                ", created_at='" + created_at + '\'' +
                ", vid='" + vid + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
