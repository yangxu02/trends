package com.linkx.trends.game.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.common.base.Strings;
import com.linkx.trends.R;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.utils.TextUtil;
import com.squareup.picasso.Picasso;

public class ViewGameSnapshot extends FrameLayout {

    @Bind(R.id.rank)
    TextView rank;
    @Bind(R.id.rank_bg)
    ViewTriangleShape rank_bg;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.category)
    TextView category;
    @Bind(R.id.rating)
    TextView rating;
    @Bind(R.id.rating_star)
    ViewStarList ratingStar;

    public ViewGameSnapshot(Context context) {
        super(context);
        setup(null);
    }

    public ViewGameSnapshot(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public ViewGameSnapshot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_game_snapshot, this);
        ButterKnife.bind(this);
    }


    public ViewGameSnapshot setGameDetail(GameDetail detail) {
        rank.setText(detail.rank());
        title.getPaint().setFakeBoldText(true); // chinese fonts
        title.setText(detail.title());
        category.setText(detail.category());
        float rate = TextUtil.asFloat(detail.rating(), 3.0f);
        rating.setText("" + rate);
        ratingStar.setWeight(rate, 10);
        Picasso.with(getContext()).load(detail.icon()).into(icon);
        return this;
    }

    public void setRankBackgroundColor(int color) {
        rank_bg.setColor(color);
    }


}
