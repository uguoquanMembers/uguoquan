package com.wb.ygq.bean;

/**
 * Description：
 * Created on 2017/4/12
 * Author : 郭
 */
public class CollcetVideoBean {
    private String id;
    private String img;
    private String headpic;
    private String name;

    public CollcetVideoBean() {
        super();
    }

    public CollcetVideoBean(String id, String img, String headpic, String name) {
        this.id = id;
        this.img = img;
        this.headpic = headpic;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    @Override
    public String toString() {
        return "CollcetVideoBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", headpic='" + headpic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
