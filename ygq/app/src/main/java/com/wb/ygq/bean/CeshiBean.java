package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/3
 * Author : 郭
 */
public class CeshiBean {
    private int id;
    private String name;
    private String ima;
    private int num;

    public CeshiBean() {
        super();
    }

    public CeshiBean(int id, String name, String ima, int num) {
        this.id = id;
        this.name = name;
        this.ima = ima;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CeshiBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ima='" + ima + '\'' +
                ", num=" + num +
                '}';
    }
}
