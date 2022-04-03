package com.jkane.a20220402_joshkane_nycschools.app.dagger;

import android.app.Application;

import com.jkane.a20220402_joshkane_nycschools.SchoolActivity;
import com.jkane.a20220402_joshkane_nycschools.app.utils.dagger.UtilModule;
import com.jkane.a20220402_joshkane_nycschools.network.dagger.NetworkModule;
import com.jkane.a20220402_joshkane_nycschools.ui.schoollist.SchoolListFragment;
import com.jkane.a20220402_joshkane_nycschools.ui.schooldetails.SchoolDetailsFragment;

import dagger.Component;

@Component(
        modules = {
                NetworkModule.class,
                UtilModule.class
        }
)
public interface AppComponent {
    void inject(Application app);

    void inject(SchoolActivity schoolActivity);

    void inject(SchoolListFragment schoolListFragment);

    void inject(SchoolDetailsFragment schoolDetailsFragment);
}
