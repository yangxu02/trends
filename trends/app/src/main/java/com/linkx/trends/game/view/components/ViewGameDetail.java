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

public class ViewGameDetail extends FrameLayout {
    @Bind(R.id.rank)
    TextView rank;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.category)
    TextView category;
    @Bind(R.id.rating)
    TextView rating;
    @Bind(R.id.clip)
    ImageView clip;

    public ViewGameDetail(Context context) {
        super(context);
        setup(null);
    }

    public ViewGameDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public ViewGameDetail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_game_detail_horizontal, this);
        ButterKnife.bind(this);
    }

    public ViewGameDetail setGameDetail(GameDetail detail) {
        // TODO
        rank.setText(detail.rank());
        title.setText(detail.title());
        desc.setText(detail.desc());
        category.setText(detail.category());
        rating.setText(detail.rating());
        Picasso.with(getContext()).load(detail.icon()).into(icon);
        Picasso.with(getContext()).load(detail.clip()).into(clip);
        return this;
    }

}
