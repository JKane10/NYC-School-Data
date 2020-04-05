package com.jkane.a04042020_joshkane_nycschools.app.dagger;

import android.app.Application;

import com.jkane.a04042020_joshkane_nycschools.MainActivity;
import com.jkane.a04042020_joshkane_nycschools.app.utils.dagger.UtilModule;
import com.jkane.a04042020_joshkane_nycschools.network.dagger.NetworkModule;
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListFragment;
import com.jkane.a04042020_joshkane_nycschools.ui.schooldetails.SchoolDetailsFragment;

import dagger.Component;

@Component(
        modules = {
                NetworkModule.class,
                UtilModule.class
        }
)
public interface AppComponent {
    void inject(Application app);

    void inject(MainActivity mainActivity);

    void inject(SchoolListFragment schoolListFragment);

    void inject(SchoolDetailsFragment schoolDetailsFragment);
}
