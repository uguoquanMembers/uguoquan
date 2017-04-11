package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class ImgListBean {


    /**
     * Msg : 200
     * Count : 6
     * Data : {"headpic":"http://img.youguoquan.com/uploads/users/header/3b40f19f581443299cf71b633a278046_header_l.jpg","orderimg":[{"ischarge":1,"url":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg"},{"ischarge":1,"url":"http://img.youguoquan.com/uploads/magazine/content/af4d6b124adc3537520163332ecadc9a_magazine_web_m.jpg"},{"ischarge":2,"url":"http://img.youguoquan.com/uploads/magazine/content/f74a2a9161f5730836550ff05e197bdb_magazine_web_m.jpg"},{"ischarge":2,"url":"http://img.youguoquan.com/uploads/magazine/content/beb76c539dfc72638b658aea4121d01c_magazine_web_m.jpg"}],"charge":"2","comment":"0","fabulous":"0","collection":"0"}
     */

    private String Msg;
    private String Count;
    /**
     * headpic : http://img.youguoquan.com/uploads/users/header/3b40f19f581443299cf71b633a278046_header_l.jpg
     * orderimg : [{"ischarge":1,"url":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg"},{"ischarge":1,"url":"http://img.youguoquan.com/uploads/magazine/content/af4d6b124adc3537520163332ecadc9a_magazine_web_m.jpg"},{"ischarge":2,"url":"http://img.youguoquan.com/uploads/magazine/content/f74a2a9161f5730836550ff05e197bdb_magazine_web_m.jpg"},{"ischarge":2,"url":"http://img.youguoquan.com/uploads/magazine/content/beb76c539dfc72638b658aea4121d01c_magazine_web_m.jpg"}]
     * charge : 2
     * comment : 0
     * fabulous : 0
     * collection : 0
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
        private String comment;
        private String fabulous;
        private String collection;
        /**
         * ischarge : 1
         * url : http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg
         */

        private List<OrderimgBean> orderimg;

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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getFabulous() {
            return fabulous;
        }

        public void setFabulous(String fabulous) {
            this.fabulous = fabulous;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public List<OrderimgBean> getOrderimg() {
            return orderimg;
        }

        public void setOrderimg(List<OrderimgBean> orderimg) {
            this.orderimg = orderimg;
        }

        public static class OrderimgBean {
            private int ischarge;
            private String url;

            public int getIscharge() {
                return ischarge;
            }

            public void setIscharge(int ischarge) {
                this.ischarge = ischarge;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
