package com.linkx.trends.game.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.linkx.trends.R;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.data.models.GameDetailList;
import com.linkx.trends.game.data.models.Orientation;
import com.linkx.trends.game.data.services.GameListQueryService;
import com.linkx.trends.game.view.components.ViewGameDetail;
import com.linkx.trends.game.view.components.ViewGameDetailVertical;
import java.util.ArrayList;
import java.util.List;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class FragmentGameList extends Fragment {

    private static String KEY_FRAGMENT_CONTENT_TYPE = "_kfct";

    public static FragmentGameList newInstance(String type) {
        FragmentGameList fragment = new FragmentGameList();
        Bundle stats = new Bundle();
        stats.putString(KEY_FRAGMENT_CONTENT_TYPE, type);
        Log.w("Trends", "key=" + KEY_FRAGMENT_CONTENT_TYPE + ",type=" + type);
        // pass
        fragment.setArguments(stats);
        return fragment;
    }

    @Bind(R.id.game_list)
    ViewGroup gameList;

    String type;
    private Looper backgroundLooper;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(KEY_FRAGMENT_CONTENT_TYPE);
        Log.w("Trends", "key=" + KEY_FRAGMENT_CONTENT_TYPE + ",type=" + type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void setupViews() {
        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundLooper = backgroundThread.getLooper();
        setupContainers();
    }

    private void setupContainers() {
        Context context = getActivity();
        GameListQueryService service = new GameListQueryService();
        gameList.removeAllViews();
        service.queryAndDisplay(type, GameDetailList.class, backgroundLooper,
            gameList, gameDetailList -> {
                List<View> viewList = new ArrayList<View>();
            for (GameDetail gameDetail : gameDetailList.details()) {
                ViewGameDetail v = Orientation.landscape.equals(gameDetail.clip_orientation()) ? new ViewGameDetail(context) : new ViewGameDetailVertical(context);
                viewList.add(v.setGameDetail(gameDetail));
            }
                return viewList;
        });

    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("GameList-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}
