package com.linkx.trends.game.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.linkx.trends.R;
import com.linkx.trends.game.data.models.GameDetail;
import com.squareup.picasso.Picasso;

public class ViewGameDetailVertical extends ViewGameDetail {
    public ViewGameDetailVertical(Context context) {
        super(context);
        setup(null);
    }

    public ViewGameDetailVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public ViewGameDetailVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_game_detail_vertical, this);
        ButterKnife.bind(this);
    }

}
