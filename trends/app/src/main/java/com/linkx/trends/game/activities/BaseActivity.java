package com.linkx.trends.game.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.linkx.trends.game.AndroidApplication;
import com.linkx.trends.game.di.components.ApplicationComponent;
import com.linkx.trends.game.di.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
