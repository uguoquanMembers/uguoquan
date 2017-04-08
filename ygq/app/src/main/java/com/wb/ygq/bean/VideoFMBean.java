package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class VideoFMBean {

    /**
     * Msg : 200
     * Count : 3
     * Data : {"carouselList":[{"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","url":"http://www.baidu.com"}],"pictureList":[{"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","url":"http://www.baidu.com"},{"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","url":"http://www.baidu.com"}],"videoList":[{"id":"7","img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"测试一下1","label":"2","count":"已播放14.6万次"},{"id":"6","img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"测试一下1","label":"2","count":"已播放14.6万次"},{"id":"5","img":"http://t2.onvshen.com:8080/gallery/20468/21835/s/001.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"骚狐狸","label":"裸聊","count":"已播放14.6万次"}]}
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
         * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
         * url : http://www.baidu.com
         */

        private List<CarouselListBean> carouselList;
        /**
         * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
         * url : http://www.baidu.com
         */

        private List<PictureListBean> pictureList;
        /**
         * id : 7
         * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
         * headpic : http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg
         * name : 测试一下1
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
