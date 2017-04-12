package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
public class VideoContentBean {

    /**
     * Msg : 200
     * Count : 3
     * Data : {"videoMessage":{"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","headpic":"http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg","name":"香川颖","count":"已播放14.6万次","url":"http://i.utop.cc/handao_img/app_img/video/mp4/shikan53.mp4","endtime":"60:33"},"commentList":[{"img":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1491547419&di=19b52e8c3d531216360539816bcf75aa&src=http://i1.sanwen8.cn/doc/1612/907-161216162R3-50.jpg","message":"1123123","name":"lyangye","time":"3分钟前"}],"orderVideo":[{"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","title":"尤果网-美女香川颖COS阿狸 玩弄尾巴特技 精气波再现","count":"已播放14.6万次"},{"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","title":"尤果网-美女香川颖COS阿狸 玩弄尾巴特技 精气波再现","count":"已播放14.6万次"}]}
     */

    private String Msg;
    private String Count;
    /**
     * videoMessage : {"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","headpic":"http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg","name":"香川颖","count":"已播放14.6万次","url":"http://i.utop.cc/handao_img/app_img/video/mp4/shikan53.mp4","endtime":"60:33"}
     * commentList : [{"img":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1491547419&di=19b52e8c3d531216360539816bcf75aa&src=http://i1.sanwen8.cn/doc/1612/907-161216162R3-50.jpg","message":"1123123","name":"lyangye","time":"3分钟前"}]
     * orderVideo : [{"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","title":"尤果网-美女香川颖COS阿狸 玩弄尾巴特技 精气波再现","count":"已播放14.6万次"},{"img":"http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg","title":"尤果网-美女香川颖COS阿狸 玩弄尾巴特技 精气波再现","count":"已播放14.6万次"}]
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
        /**
         * img : http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg
         * headpic : http://img.youguoquan.com/uploads/users/header/91a85b836a3b0b74fa4a9192d59e6697.jpg
         * name : 香川颖
         * count : 已播放14.6万次
         * url : http://i.utop.cc/handao_img/app_img/video/mp4/shikan53.mp4
         * endtime : 60:33
         */

        private VideoMessageBean videoMessage;
        /**
         * img : https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1491547419&di=19b52e8c3d531216360539816bcf75aa&src=http://i1.sanwen8.cn/doc/1612/907-161216162R3-50.jpg
         * message : 1123123
         * name : lyangye
         * time : 3分钟前
         */

        private List<CommentListBean> commentList;
        /**
         * img : http://img.youguoquan.com/uploads/magazine/content/b147130ae8fdbbc55c0a425ec5367a8f_magazine_web_m.jpg
         * title : 尤果网-美女香川颖COS阿狸 玩弄尾巴特技 精气波再现
         * count : 已播放14.6万次
         */

        private List<OrderVideoBean> orderVideo;

        public VideoMessageBean getVideoMessage() {
            return videoMessage;
        }

        public void setVideoMessage(VideoMessageBean videoMessage) {
            this.videoMessage = videoMessage;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public List<OrderVideoBean> getOrderVideo() {
            return orderVideo;
        }

        public void setOrderVideo(List<OrderVideoBean> orderVideo) {
            this.orderVideo = orderVideo;
        }

        public static class VideoMessageBean {
            private String img;
            private String headpic;
            private String name;
            private String count;
            private String url;
            private String endtime;

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

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }
        }

        public static class CommentListBean {
            private String img;
            private String message;
            private String name;
            private String time;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
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
        }

        public static class OrderVideoBean {
            private String img;
            private String title;
            private String count;
            private String id;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
