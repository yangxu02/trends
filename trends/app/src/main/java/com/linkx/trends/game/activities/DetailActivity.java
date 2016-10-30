package com.linkx.trends.game.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.common.base.Strings;
import com.linkx.trends.R;
import com.linkx.trends.game.Consts;
import com.linkx.trends.game.data.models.GameDetail;
import com.linkx.trends.game.data.models.Orientation;
import com.linkx.trends.game.utils.QueryContextUtils;
import com.linkx.trends.game.utils.SysUtils;
import com.linkx.trends.game.view.Transition;
import com.linkx.trends.game.view.adapters.PagerAdapter;
import com.linkx.trends.game.view.components.ViewGameDetail;
import com.linkx.trends.game.view.components.ViewGameDetailVertical;


public class DetailActivity extends BaseActivity {

    private final static String SER_EXTRA_KEY_DETAIL = "SER_DETAIL";
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    //    @Bind(R.id.navigation_view)
//    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.detail)
    ViewGroup detailRootView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private GameDetail gameDetail;

    public static void launch(Activity activity, GameDetail gameDetail, Transition transition) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(SER_EXTRA_KEY_DETAIL, gameDetail.toJson());
        Transition.putTransition(intent, transition);
        activity.startActivity(intent);
        transition.overrideOpenTransition(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        gameDetail = GameDetail.fromJson(getIntent().getStringExtra(SER_EXTRA_KEY_DETAIL), GameDetail.class);
        setupActionBar();
        setupViews();
    }

    @Override
    public void finish() {
        super.finish();
        Transition.getTransition(getIntent()).overrideCloseTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        toolbarTitle.setText(gameDetail.title());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void setupViews() {

//        View detailView;
//        detailView = new ViewGameDetail(this).setGameDetail(gameDetail);
//        if (Orientation.portrait.equals(gameDetail.clip_orientation())) {
//            detailView = new ViewGameDetailVertical(this).setGameDetail(gameDetail);
//        } else {
//            detailView = new ViewGameDetail(this).setGameDetail(gameDetail);
//        }

        WebView detailView = new WebView(this);
        detailView.setWebViewClient(new WebViewClientWithProgress(progressBar));
//        detailView.getSettings().setJavaScriptEnabled(true);
        detailRootView.removeAllViews();
        detailRootView.addView(detailView);
//        if (!Strings.isNullOrEmpty(gameDetail.gp_link()) && SysUtils.hasGooglePlayInstalled(getApplicationContext())) {
//            detailView.loadUrl(gameDetail.gp_link());
//        } else {
//            detailView.loadUrl(gameDetail.taptap_link());
//        }
        detailView.loadUrl(gameDetail.taptap_link());
    }

}

class WebViewClientWithProgress extends WebViewClient {
    private ProgressBar progressBar;

    public WebViewClientWithProgress(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(10);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            int progress = 10;
            @Override
            public void run() {
                if (progress > 90) return;
                progress += 10;
                progressBar.setProgress(progress);
                handler.postDelayed(this, 100);
            }
        }, 100);

    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.GONE);
    }

}
