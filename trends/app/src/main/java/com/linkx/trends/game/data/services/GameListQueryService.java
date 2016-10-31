package com.linkx.trends.game.data.services;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.linkx.trends.game.data.models.Model;
import com.linkx.trends.game.utils.AssetsUtil;
import com.linkx.trends.game.utils.IOUtil;
import com.linkx.trends.game.utils.QueryContextUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;

public class GameListQueryService {

    private Context appCtx;

    public GameListQueryService(Context appCtx) {
        this.appCtx = appCtx;
    }

    /**
     * should update from network when file not exist or file content is one day before
     *
     * @param tag: file tag
     * @return true if should update
     */
    private boolean shouldLoadFromNetwork(String tag) {
        String fileName = IOUtil.gameDataFileName(tag);
        File file = new File(fileName);
        if (!file.exists()) return true;

        long lastModified = file.lastModified();
        long now = System.currentTimeMillis();

        return (TimeUnit.MILLISECONDS.toDays(now - lastModified) >= 1);
    }

    private <T extends Model> List<T> asFileSource(String tag, Class<T> clazz) throws IOException, Model.MethodNotOverrideException {
        String fileName = IOUtil.gameDataFileName(tag);
        String content = "";
        if (shouldLoadFromNetwork(tag)) {
            content = IOUtil.readFromNetworkAndCache(QueryContextUtils.url(tag), fileName);
            if (Strings.isNullOrEmpty(content)) {
                content = AssetsUtil.getContent(appCtx, tag);
            }
        } else {
            content = Files.toString(new File(fileName), Charsets.UTF_8);
        }
        List<T> results = new ArrayList<>();
        if (Strings.isNullOrEmpty(content)) return results;
        results.add(Model.fromJson(content, clazz));
        return results;
    }

    public <T extends Model> Observable<List<T>> baseDetailObservable(String tag, Class<T> clazz) {
        return Observable.defer(() -> {
            try {
                List<T> dataList = asFileSource(tag, clazz);
                return Observable.just(dataList);
            } catch (IOException | Model.MethodNotOverrideException e) {
                throw OnErrorThrowable.from(e);
            }
        });
    }

    public <T extends Model> void queryFromUISilently(String tag, Class<T> clazz, Looper looper) {
        Subscriber<List<T>> subscriber = new Subscriber<List<T>>() {
            String tag = clazz.getSimpleName();

            @Override
            public void onCompleted() {
                Log.d(tag, "onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(tag, "onError()", e);
            }

            @Override
            public void onNext(List<T> ts) {
                Log.d(tag, "onNext():data=" + ((Model) ts).toJson());
            }
        };

        queryFromUI(tag, clazz, looper, subscriber);
    }

    public <T extends Model> void queryFromUI(String tag, Class<T> clazz, Looper looper, Subscriber<List<T>> subscriber) {
        queryFromUI(tag, clazz, looper).subscribe(subscriber);
    }

    private <T extends Model> Observable<List<T>> queryFromUI(String tag, Class<T> clazz, Looper looper) {
        return baseDetailObservable(tag, clazz)
            .subscribeOn(AndroidSchedulers.from(looper))
            .observeOn(AndroidSchedulers.mainThread());
    }

    public <T extends Model> void queryAndDisplay(String tag, Class<T> clazz, Looper looper,
                                                  ViewGroup container, Func1<T, List<View>> func) {
        queryFromUI(tag, clazz, looper,
            new Subscriber<List<T>>() {
                @Override
                public void onCompleted() {
                    Log.d("Query", "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Query", "onError", e);
                }

                @Override
                public void onNext(List<T> items) {
                    for (T item : items) {
                        for (View view : func.call(item)) {
                            container.addView(view);
                        }
                        Log.d("Query", "onNext:data=" + item.toJson());
                    }
                }
            });
    }

}
