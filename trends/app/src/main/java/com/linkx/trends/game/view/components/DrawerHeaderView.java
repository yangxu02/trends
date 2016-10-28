package com.linkx.trends.game.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.linkx.trends.R;

public class DrawerHeaderView extends FrameLayout {
    public DrawerHeaderView(Context context) {
        super(context);
        setup();
    }

    public DrawerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.navigation_header, this);
    }
}
