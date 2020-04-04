package com.jkane.a04042020_joshkane_nycschools.app;

import android.app.Application;

import com.jkane.a04042020_joshkane_nycschools.app.dagger.AppComponent;
import com.jkane.a04042020_joshkane_nycschools.app.dagger.DaggerAppComponent;
import com.jkane.a04042020_joshkane_nycschools.network.dagger.NetworkModule;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .networkModule(new NetworkModule(getApplicationContext()))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
