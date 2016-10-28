package com.linkx.trends.game;

/**
 * Created by ulyx.yang@yeahmobi.com on 2016/10/26.
 */
public final class Consts {
     public final static class Tab {
        public String name;
        public String tag;

        Tab(String name, String tag) {
            this.name = name;
            this.tag = tag;
        }
    }

    public static final Tab[] tabs = new Tab[] {
        new Tab("美国", "us"),
    };

}
