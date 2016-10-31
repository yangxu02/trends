package com.linkx.trends.game.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.common.base.Strings;
import com.linkx.trends.R;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.data.models.GameDetailList;
import com.linkx.trends.game.data.services.GameListQueryService;
import com.linkx.trends.game.view.adapters.GameSnapshotListAdapter;
import com.linkx.trends.game.view.components.DividerItemDecoration;
import java.util.List;
import rx.Subscriber;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class FragmentGameList extends Fragment {

    private static String KEY_FRAGMENT_CONTENT_TYPE = "_kfct";
    @Bind(R.id.scroll)
    RecyclerView gameSnapshotList;
    @Bind(R.id.circle_progress)
    CircleProgress circleProgress;

    //    @Bind(R.id.game_list)
//    ViewGroup gameList;
    String type;
    private GameSnapshotListAdapter gameSnapshotListAdapter;
    private Looper backgroundLooper;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static FragmentGameList newInstance(String type) {
        FragmentGameList fragment = new FragmentGameList();
        Bundle stats = new Bundle();
        stats.putString(KEY_FRAGMENT_CONTENT_TYPE, type);
        Log.d("Trends", "key=" + KEY_FRAGMENT_CONTENT_TYPE + ",type=" + type);
        // pass
        fragment.setArguments(stats);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(KEY_FRAGMENT_CONTENT_TYPE);
        Log.d("Trends", "key=" + KEY_FRAGMENT_CONTENT_TYPE + ",type=" + type);
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

        handler.postDelayed(new Runnable() {
            int progress = 20;

            @Override
            public void run() {
                if (progress >= 80) return;
                progress += 20;
                circleProgress.setProgress(progress);
                handler.postDelayed(this, 150);
            }
        }, 150);

        GameListQueryService service = new GameListQueryService(getActivity().getApplication());
        gameSnapshotList.setLayoutManager(new LinearLayoutManager(getActivity()));
        gameSnapshotList.setHasFixedSize(false);
        gameSnapshotListAdapter = new GameSnapshotListAdapter();
        gameSnapshotList.addItemDecoration(new DividerItemDecoration(getActivity()));
        service.queryFromUI(type, GameDetailList.class, backgroundLooper, new Subscriber<List<GameDetailList>>() {
            @Override
            public void onCompleted() {
                Log.d("Trends", "onCompleted");
                circleProgress.setVisibility(View.GONE);
                gameSnapshotList.setAdapter(gameSnapshotListAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Trends", "onError");
            }

            @Override
            public void onNext(List<GameDetailList> gameDetailLists) {
                for (GameDetailList item : gameDetailLists) {
                    for (GameDetail detail : item.details()) {
                        if (Strings.isNullOrEmpty(detail.bundle())) continue;
                        gameSnapshotListAdapter.add(detail);
                    }
                    Log.d("Trends", "onNext:data=" + item.toJson());
                }
            }
        });

        /*
        gameList.removeAllViews();
        service.queryAndDisplay(type, GameDetailList.class, backgroundLooper,
                gameList, gameDetailList -> {
                    List<View> viewList = new ArrayList<View>();
                    int i = 0;
                    for (GameDetail gameDetail : gameDetailList.details()) {
//                ViewGameDetail v = Orientation.landscape.equals(gameDetail.clip_orientation()) ? new ViewGameDetail(context) : new ViewGameDetailVertical(context);
                        if (i >= 10) break;
                        ViewGameSnapshot v = new ViewGameSnapshot(context);
                        if (i >= 3) {
                            v.setRankBackgroundColor(ViewTriangleShape.COLOR_NORMAL); // light blue
                        }
                        v.setGameDetail(gameDetail);
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DetailActivity.launch(getActivity(), gameDetail, Transition.PUSH_RIGHT_TO_LEFT);
                            }
                        });
                        viewList.add(v);
                        ++i;
                    }
                    return viewList;
                });
                */


    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("GameList-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}
