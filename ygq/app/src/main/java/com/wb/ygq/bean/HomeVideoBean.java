package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HomeVideoBean {


    /**
     * Msg : 200
     * Count : 2
     * Data : {"carouselList":[{"img":"http://img.youguoquan.com/uploads/activity/banner/cec44941d28bb522ba86dad29c36cd98.jpg","url":"1","go":"26"},{"img":"http://img.youguoquan.com/uploads/activity/banner/67fa07298762c1abb19a92e8f15e806e.jpg","url":"1","go":"27"},{"img":"http://img.youguoquan.com/uploads/activity/banner/a311d280522dc6f8cc508d3a36f3fce7.jpg","url":"99","go":"18"},{"img":"http://img.youguoquan.com/uploads/activity/banner/15205568acdc5205de75f31e2b3702ed.jpg","url":"99","go":"19"}],"indexImgList":[{"img":"http://img.youguoquan.com/uploads/activity/banner/67fa07298762c1abb19a92e8f15e806e.jpg","url":"1","title":"lyangye","empty":"0","go":"1"},{"img":"http://img.youguoquan.com/uploads/activity/banner/67fa07298762c1abb19a92e8f15e806e.jpg","url":"99","title":"lyangye","empty":"0","go":"2"}]}
     */

    private String Msg;
    private String Count;
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
        /**
         * img : http://img.youguoquan.com/uploads/activity/banner/cec44941d28bb522ba86dad29c36cd98.jpg
         * url : 1
         * go : 26
         */

        private List<CarouselListBean> carouselList;
        /**
         * img : http://img.youguoquan.com/uploads/activity/banner/67fa07298762c1abb19a92e8f15e806e.jpg
         * url : 1
         * title : lyangye
         * empty : 0
         * go : 1
         */

        private List<IndexImgListBean> indexImgList;

        public List<CarouselListBean> getCarouselList() {
            return carouselList;
        }

        public void setCarouselList(List<CarouselListBean> carouselList) {
            this.carouselList = carouselList;
        }

        public List<IndexImgListBean> getIndexImgList() {
            return indexImgList;
        }

        public void setIndexImgList(List<IndexImgListBean> indexImgList) {
            this.indexImgList = indexImgList;
        }

        public static class CarouselListBean {
            private String img;
            private String url;//跳转分类
            private String go;//跳转id

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getGo() {
                return go;
            }

            public void setGo(String go) {
                this.go = go;
            }
        }

        public static class IndexImgListBean {
            private String img;
            private String url;
            private String title;
            private String empty;
            private String go;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            public String getGo() {
                return go;
            }

            public void setGo(String go) {
                this.go = go;
            }
        }
    }
}
