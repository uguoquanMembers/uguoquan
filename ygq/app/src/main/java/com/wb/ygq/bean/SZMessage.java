package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SZMessage {


    /**
     * Msg : 200
     * Count : 2
     * Data : [{"id":"10","img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"测试一下1"},{"id":"9","img":"http://t2.onvshen.com:8080/gallery/20468/21835/s/001.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"小妖精"}]
     */

    private String Msg;
    private String Count;
    /**
     * id : 10
     * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
     * headpic : http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg
     * name : 测试一下1
     */

    private List<DataBean> Data;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String Count) {
        this.Count = Count;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String id;
        private String img;
        private String headpic;
        private String name;

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
    }
}
