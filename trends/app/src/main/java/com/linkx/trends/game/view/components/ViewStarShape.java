package com.linkx.trends.game.view.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * http://balvinder788.blogspot.com/2015/01/star-shape-layout-designing-android.html
 */

public class ViewStarShape extends View {

    public final static int COLOR_HOT = 0xfff57f17;
    public final static int COLOR_NORMAL = 0xffa8ad9f;

    private final Path path = new Path();
    private final Paint p = new Paint();
    private int primaryColor = COLOR_HOT;
    private int secondaryColor = COLOR_NORMAL;
    private FillStyle fillStyle = FillStyle.Full;

    int width;
    int height;

    public enum FillStyle {
        Full,
        Half,
        None
    }

    public ViewStarShape(Context context) {
        super(context);
    }

    public ViewStarShape(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewStarShape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewStarShape setPrimaryColor(int color) {
        this.primaryColor = color;
        return this;
    }

    public ViewStarShape setSecondaryColor(int color) {
        this.secondaryColor = color;
        return this;
    }

    public ViewStarShape setSize(int width, int height) {
        this.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        this.width = width;
        this.height = height;
        return this;
    }

    public ViewStarShape setFillStyle(FillStyle fillStyle) {
        this.fillStyle = fillStyle;
        invalidate();
        return this;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (fillStyle) {
            case Full:
                path.reset();
                path.addPath(fullStar());
                p.setColor(primaryColor);
                canvas.drawPath(path, p);
                break;
            case None:
                path.reset();
                path.addPath(fullStar());
                p.setColor(secondaryColor);
                canvas.drawPath(path, p);
                break;
            case Half:
                path.reset();
                path.addPath(leftHalfStar());
                p.setColor(primaryColor);
                canvas.drawPath(path, p);

                path.reset();
                path.addPath(rightHalfStar());
                p.setColor(secondaryColor);
                canvas.drawPath(path, p);
                break;
        }
    }

    private Path fullStar() {
        Path path = new Path();
        float midX = getWidth()/2;
        float midY = getHeight()/2;
        float scale = midX / 300;
        path.moveTo(midX, midY);
        path.lineTo(midX+190*scale, midY+300*scale);
        path.lineTo(midX, midY+210*scale);
        path.lineTo(midX-190*scale, midY+300*scale);
        path.lineTo(midX-160*scale, midY+90*scale);
        path.lineTo(midX-300*scale, midY-70*scale);
        path.lineTo(midX-100*scale ,midY-110*scale);
        path.lineTo(midX, midY-300*scale);
        path.lineTo(midX+100*scale, midY-110*scale);
        path.lineTo(midX+300*scale, midY-70*scale);
        path.lineTo(midX+160*scale, midY+90*scale);
        path.lineTo(midX+190*scale, midY+300*scale);
        return path;
    }

    private Path leftHalfStar() {
        Path path = new Path();
        float midX = getWidth()/2;
        float midY = getHeight()/2;
        float scale = midX / 300;
        path.moveTo(midX, midY);
        path.lineTo(midX, midY-300*scale);
        path.lineTo(midX-100*scale ,midY-110*scale);
        path.lineTo(midX-300*scale ,midY-70*scale);
        path.lineTo(midX-160*scale, midY+90*scale);
        path.lineTo(midX-190*scale, midY+300*scale);
        path.lineTo(midX, midY+210*scale);
        path.moveTo(midX, midY);
        return path;
    }

    private Path rightHalfStar() {
        Path path = new Path();
        float midX = getWidth()/2;
        float midY = getHeight()/2;
        float scale = midX / 300;
        path.moveTo(midX, midY);
        path.lineTo(midX, midY-300*scale);
        path.lineTo(midX+100*scale ,midY-110*scale);
        path.lineTo(midX+300*scale ,midY-70*scale);
        path.lineTo(midX+160*scale, midY+90*scale);
        path.lineTo(midX+190*scale, midY+300*scale);
        path.lineTo(midX, midY+210*scale);
        path.moveTo(midX, midY);
        return path;
    }

}
