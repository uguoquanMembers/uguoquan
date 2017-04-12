package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class VideoFMBean {


    /**
     * Msg : 200
     * Count : 3
     * Data : {"carouselList":[{"img":"http://img.youguoquan.com/uploads/activity/banner/cec44941d28bb522ba86dad29c36cd98.jpg","go":"18"},{"img":"http://img.youguoquan.com/uploads/activity/banner/a311d280522dc6f8cc508d3a36f3fce7.jpg","go":"19"},{"img":"http://img.youguoquan.com/uploads/activity/banner/15205568acdc5205de75f31e2b3702ed.jpg","go":"21"},{"img":"http://img.youguoquan.com/uploads/activity/banner/67fa07298762c1abb19a92e8f15e806e.jpg","go":"20"},{"img":"http://t2.onvshen.com:8080/gallery/20468/21835/s/003.jpg","go":"27"}],"pictureList":[{"img":"http://img.youguoquan.com/uploads/magazine/content/c5cfbb3182d27329f657a2817a93f8e6_magazine_web_m.jpg","url":"www.baidu.com"}],"videoList":[{"id":"18","img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","headpic":"http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg","name":"香川颖","label":"2","count":"已播放14.6万次"},{"id":"21","img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","headpic":"http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg","name":"香川颖","label":"2","count":"已播放14.6万次"},{"id":"22","img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","headpic":"http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg","name":"香川颖","label":"2","count":"已播放14.6万次"},{"id":"31","img":"http://t2.onvshen.com:8080/gallery/20468/21835/s/001.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"lyangye","label":"2","count":"已播放14.6万次"},{"id":"30","img":"http://t2.onvshen.com:8080/gallery/20468/21835/s/001.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"lyangye","label":"2","count":"已播放14.6万次"}]}
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
         * go : 18
         */

        private List<CarouselListBean> carouselList;
        /**
         * img : http://img.youguoquan.com/uploads/magazine/content/c5cfbb3182d27329f657a2817a93f8e6_magazine_web_m.jpg
         * url : www.baidu.com
         */

        private List<PictureListBean> pictureList;
        /**
         * id : 18
         * img : http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg
         * headpic : http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg
         * name : 香川颖
         * label : 2
         * count : 已播放14.6万次
         */

        private List<VideoListBean> videoList;

        public List<CarouselListBean> getCarouselList() {
            return carouselList;
        }

        public void setCarouselList(List<CarouselListBean> carouselList) {
            this.carouselList = carouselList;
        }

        public List<PictureListBean> getPictureList() {
            return pictureList;
        }

        public void setPictureList(List<PictureListBean> pictureList) {
            this.pictureList = pictureList;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        public static class CarouselListBean {
            private String img;
            private String go;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getGo() {
                return go;
            }

            public void setGo(String go) {
                this.go = go;
            }
        }

        public static class PictureListBean {
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
        }

        public static class VideoListBean {
            private String id;
            private String img;
            private String headpic;
            private String name;
            private String label;
            private String count;

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

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
