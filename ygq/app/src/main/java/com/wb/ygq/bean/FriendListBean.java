package com.wb.ygq.bean;

import java.util.List;

/**
 * Description：
 * Created on 2017/4/6
 */
public class FriendListBean {
    private String id;
    private String headpic;
    private String name;
    private String time;
    private String title;
    private String empty;
    private String reward;
    private String fabulous;
    private List<String> img;

    public FriendListBean() {
        super();
    }

    public FriendListBean(String id, String headpic, String name, String time, String title, String empty, String reward, String fabulous, List<String> img) {
        this.id = id;
        this.headpic = headpic;
        this.name = name;
        this.time = time;
        this.title = title;
        this.empty = empty;
        this.reward = reward;
        this.fabulous = fabulous;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "FriendListBean{" +
                "id='" + id + '\'' +
                ", headpic='" + headpic + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", empty='" + empty + '\'' +
                ", reward='" + reward + '\'' +
                ", fabulous='" + fabulous + '\'' +
                ", img=" + img +
                '}';
    }
}
