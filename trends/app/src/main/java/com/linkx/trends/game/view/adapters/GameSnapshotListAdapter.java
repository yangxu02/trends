package com.linkx.trends.game.view.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.linkx.trends.game.activities.DetailActivity;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.utils.SysUtils;
import com.linkx.trends.game.view.Transition;
import com.linkx.trends.game.view.components.ViewGameSnapshot;
import com.linkx.trends.game.view.components.ViewTriangleShape;
import java.util.ArrayList;
import java.util.List;

public class GameSnapshotListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GameDetail> details = new ArrayList<>();

    public GameSnapshotListAdapter() {
    }

    public GameSnapshotListAdapter(List<GameDetail> details) {
        this.details.addAll(details);
    }

    public void add(List<GameDetail> details) {
        this.details.addAll(details);
    }

    public void add(GameDetail detail) {
        this.details.add(detail);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public static ItemViewHolder create(ViewGroup parent) {
            View itemView = new ViewGameSnapshot(parent.getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(lp);
            return new ItemViewHolder(itemView);
        }

        public void bind(GameDetail detail, int position) {
            ViewGameSnapshot snapshot = (ViewGameSnapshot) itemView;
            if (position >= 3) {
                snapshot.setRankBackgroundColor(ViewTriangleShape.COLOR_NORMAL);
            }
            snapshot.setGameDetail(detail);
            snapshot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    try {
                        SysUtils.openWithMarket(activity, detail.bundle());
                    } catch (Exception e) {
                        DetailActivity.launch((Activity) v.getContext(), detail, Transition.PUSH_RIGHT_TO_LEFT);
                    }
                }
            });
        }
    }

}

