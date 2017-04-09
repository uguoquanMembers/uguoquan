package com.wb.ygq.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
public class VideoContentBean {

    /**
     * Msg : 200
     * Count : 2
     * Data : {"videoMessage":{"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"测试一下1","count":"已播放14.6万次","url":"http:/www.abc.com/abc.mp4","orderimg":["http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg"]},"commentList":[{"img":"http://resa.kafeisec.cn/t/1.jpg","message":" SVIP怎么升级？","name":"小骚货","time":"1分钟前"},{"img":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","message":"测试评论内容","name":"测试用户名","time":"1分钟前"}]}
     */

    private String Msg;
    private String Count;
    /**
     * videoMessage : {"img":"http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","headpic":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","name":"测试一下1","count":"已播放14.6万次","url":"http:/www.abc.com/abc.mp4","orderimg":["http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg"]}
     * commentList : [{"img":"http://resa.kafeisec.cn/t/1.jpg","message":" SVIP怎么升级？","name":"小骚货","time":"1分钟前"},{"img":"http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg","message":"测试评论内容","name":"测试用户名","time":"1分钟前"}]
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
         * img : http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
         * headpic : http://img3.redocn.com/tupian/20150318/zisehuawenyuanxingbiankuang_4021350.jpg
         * name : 测试一下1
         * count : 已播放14.6万次
         * url : http:/www.abc.com/abc.mp4
         * orderimg : ["http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg","http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg"]
         */

        private VideoMessageBean videoMessage;
        /**
         * img : http://resa.kafeisec.cn/t/1.jpg
         * message :  SVIP怎么升级？
         * name : 小骚货
         * time : 1分钟前
         */

        private List<CommentListBean> commentList;

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

        public static class VideoMessageBean {
            private String img;
            private String headpic;
            private String name;
            private String count;
            private String url;
            private List<String> orderimg;

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

            public List<String> getOrderimg() {
                return orderimg;
            }

            public void setOrderimg(List<String> orderimg) {
                this.orderimg = orderimg;
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
    }
}
