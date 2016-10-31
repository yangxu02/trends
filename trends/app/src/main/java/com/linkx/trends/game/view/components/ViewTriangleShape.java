package com.linkx.trends.game.view.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ViewTriangleShape extends View {

    public final static int COLOR_HOT = 0xffe03730;
    public final static int COLOR_NORMAL = 0xff14b9c8;

    private final Path path = new Path();
    private final Paint p = new Paint();
    private int color = COLOR_HOT;

    public ViewTriangleShape(Context context) {
        super(context);
    }

    public ViewTriangleShape(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewTriangleShape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth() / 2;

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(0, w);
        path.lineTo(0, 0);
        path.close();

        p.setColor(color);
        canvas.drawPath(path, p);
    }
}
