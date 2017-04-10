package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HomeVideoBean {

    /**
     * Msg : 200
     * Count : 2
     * Data : {"carouselList":[{"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","url":"http://www.baidu.com"}],"indexImgList":[{"img":"","url":"","title":"限时免费"}]}
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

    @Override
    public String toString() {
        return "HomeVideoBean{" +
                "Msg='" + Msg + '\'' +
                ", Count='" + Count + '\'' +
                ", Data=" + Data +
                '}';
    }

    public static class DataBean {
        /**
         * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
         * url : http://www.baidu.com
         */

        private List<CarouselListBean> carouselList;
        /**
         * img :
         * url :
         * title : 限时免费
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "carouselList=" + carouselList +
                    ", indexImgList=" + indexImgList +
                    '}';
        }

        public static class CarouselListBean {
            private String img;
            private String url;

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

            @Override
            public String toString() {
                return "CarouselListBean{" +
                        "img='" + img + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        public static class IndexImgListBean {
            private String img;
            private String url;
            private String title;

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

            @Override
            public String toString() {
                return "IndexImgListBean{" +
                        "img='" + img + '\'' +
                        ", url='" + url + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }
    }
}
