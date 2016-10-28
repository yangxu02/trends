package com.linkx.trends.game.di.components;

import android.app.Activity;
import android.app.Application;
import com.linkx.trends.game.di.ActivityScope;
import com.linkx.trends.game.di.modules.ActivityModule;
import dagger.Component;

@ActivityScope
@Component(dependencies = Application.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
