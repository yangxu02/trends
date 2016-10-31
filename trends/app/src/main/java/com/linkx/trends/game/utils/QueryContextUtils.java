package com.linkx.trends.game.utils;

/**
 * Created by ulyx.yang@yeahmobi.com on 2016/10/26.
 */
public class QueryContextUtils {
    // TODO
    public static String url(String type) {
        return "http://admin.yeahmagic.com.s3.amazonaws.com/" + type;
    }

    public static String appUrl(String id) {
        return "https://www.taptap.com/app/" + id;
    }
}
