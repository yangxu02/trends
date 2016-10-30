package com.linkx.trends.game;

/**
 * Created by ulyx.yang@yeahmobi.com on 2016/10/26.
 */
public final class Consts {
    public static final Tab[] tabs = new Tab[]{
            new Tab("下载榜", "download"),
            new Tab("美国榜", "us"),
            new Tab("香港榜", "hk"),
            new Tab("台湾榜", "tw"),
            new Tab("日本榜", "jp"),
            new Tab("韩国榜", "kr"),
    };

    public final static class Tab {
        public String name;
        public String tag;

        Tab(String name, String tag) {
            this.name = name;
            this.tag = tag;
        }
    }

}
