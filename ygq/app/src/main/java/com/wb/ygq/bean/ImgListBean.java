package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class ImgListBean {

    /**
     * Msg : 200
     * Count : 3
     * Data : {"headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","orderimg":["http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg"],"charge":"2"}
     */

    private String Msg;
    private String Count;
    /**
     * headpic : http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg
     * orderimg : ["http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg"]
     * charge : 2
     */

    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String headpic;
        private String charge;
        private List<String> orderimg;

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public List<String> getOrderimg() {
            return orderimg;
        }

        public void setOrderimg(List<String> orderimg) {
            this.orderimg = orderimg;
        }
    }
}
