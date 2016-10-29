package com.linkx.trends.game.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.linkx.trends.R;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.utils.TextUtil;
import com.squareup.picasso.Picasso;

public class ViewGameDetailVertical extends FrameLayout implements ViewBaseGameDetail {
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
    @Bind(R.id.rating_star)
    ViewStarList ratingStar;
    @Bind(R.id.clip)
    ImageView clip;

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

    @Override
    public View setGameDetail(GameDetail detail) {
        title.setText(detail.title());
        desc.setText(detail.desc());
        category.setText(detail.category());
        float rate = TextUtil.asFloat(detail.rating(), 3.0f);
        rating.setText("" + rate);
        ratingStar.setWeight(rate, 10);
        Picasso.with(getContext()).load(detail.icon()).into(icon);
        Picasso.with(getContext()).load(detail.clip()).into(clip);
        return this;
    }

}
