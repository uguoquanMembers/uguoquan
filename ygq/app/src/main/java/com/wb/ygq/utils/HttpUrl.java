package com.wb.ygq.utils;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HttpUrl {
    public static class API {
        /**
         * 首页
         */
        public static final String SHOUYE = "http://youguo.fzjydqg.com/index.php/Api/Index/index";

        /**
         * 朋友圈
         */
        public static final String FRIEND_QUN = "http://youguo.fzjydqg.com/index.php/Api/Friend/getFriendList";

        /**
         * 私照接口
         */
        public static final String SZ_List = "http://youguo.fzjydqg.com/index.php/Api/img/typeList";

        /**
         * 私照内每个tab页数据接口
         */
        public static final String SZ_MESSAGE = "http://youguo.fzjydqg.com/index.php/Api/img/imgFromType/type/%s/page/%d";

        /**
         * 视频tab接口
         */
        public static final String VIDEO_FM = "http://youguo.fzjydqg.com/index.php/Api/video/getvideoList/page/%d";

        /**
         * 获取图片列表
         */
        public static final String GET_IMG_LIST = "http://youguo.fzjydqg.com/index.php/Api/img/showImg/id/%s";

        /**
         * 视频详情
         */
        public static final String VIDEO_CONTENT = "http://youguo.fzjydqg.com/index.php/Api/video/getvideo/id/%s";
        /**
         * 登录
         */
        public static final String LOGIN = "http://youguo.fzjydqg.com/index.php/Api/User/login";
        /**
         * 收藏列表
         */
        public static final String COLLECT_LIST = "http://youguo.fzjydqg.com/index.php/Api/Order/showcollection";
        /**
         * 删除收藏
         */
        public static final String DELETE_COLLECT_LIST = "http://youguo.fzjydqg.com/index.php/Api/Order/delCollection";
        /**
         * 个人朋友圈
         */
        public static final String PERSONAL_FRIEND = "http://youguo.fzjydqg.com/index.php/Api/Friend/getFriendListFromUser";
        /**
         * 私拍视频
         */
        public static final String SP_VIDEO = "http://youguo.fzjydqg.com/index.php/Api/Friend/getDirectSeeding";
        /**
         * 分类视频
         */
        public static final String CLASSIFY_VIDEO = "http://youguo.fzjydqg.com/index.php/Api/video/getVideoFromType";
        /**
         * 已购买的
         */
        public static final String ALREADY_BUY = "http://youguo.fzjydqg.com/index.php/Api/Order/buy";
        /**
         * 头像
         */
        public static final String HEAD = "http://shtml.asia-cloud.com/ZZSY/list_test1.png";

    }
}
