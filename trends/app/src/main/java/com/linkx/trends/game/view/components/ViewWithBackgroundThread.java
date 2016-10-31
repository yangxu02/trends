package com.linkx.trends.game.view.components;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by ulyx.yang on 2016/9/16.
 */
public class ViewWithBackgroundThread extends FrameLayout {

    protected Looper backgroundLooper;

    public ViewWithBackgroundThread(Context context) {
        super(context);
        setupLooper();
    }

    public ViewWithBackgroundThread(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLooper();
    }

    public ViewWithBackgroundThread(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLooper();
    }

    public String name() {
        return "BaseThreadedView";
    }

    private void setupLooper() {
        BackgroundThread backgroundThread = new BackgroundThread(name());
        backgroundThread.start();
        this.backgroundLooper = backgroundThread.getLooper();
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread(String name) {
            super(name, THREAD_PRIORITY_BACKGROUND);
        }
    }
}
