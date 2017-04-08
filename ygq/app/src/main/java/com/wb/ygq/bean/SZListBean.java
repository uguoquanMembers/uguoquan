package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SZListBean {

    /**
     * Msg : 200
     * Count : 2
     * Data : [{"id":"1","title":"最新"},{"id":"2","title":"最热"}]
     */

    private String Msg;
    private String Count;
    /**
     * id : 1
     * title : 最新
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
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
