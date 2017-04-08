package com.wb.ygq.utils;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HttpUrl {
    public static class API{
        /**
         * 首页
         */
        public static final String SHOUYE="http://youguo.fzjydqg.com/index.php/Api/Index/index";

        /**
         * 朋友圈
         */
        public static final String FRIEND_QUN="http://youguo.fzjydqg.com/index.php/Api/Friend/getFriendList";

        /**
         * 私照接口
         */
        public static final String SZ_List="http://youguo.fzjydqg.com/index.php/Api/img/typeList";

        /**
         * 私照内每个tab页数据接口
         */
        public static final String SZ_MESSAGE="http://youguo.fzjydqg.com/index.php/Api/img/imgFromType/type/%s/page/%d";

    }
}
