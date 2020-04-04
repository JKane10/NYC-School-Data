package com.jkane.a04042020_joshkane_nycschools.app.dagger;

import android.app.Application;

import com.jkane.a04042020_joshkane_nycschools.MainActivity;
import com.jkane.a04042020_joshkane_nycschools.network.dagger.NetworkModule;
import com.jkane.a04042020_joshkane_nycschools.ui.main.MainFragment;

import dagger.Component;

@Component(
        modules = {
                NetworkModule.class
        }
)
public interface AppComponent {
    void inject(Application app);

    void inject(MainActivity mainActivity);

    void inject(MainFragment mainFragment);
}
