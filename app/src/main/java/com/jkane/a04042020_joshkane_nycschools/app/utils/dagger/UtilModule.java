package com.jkane.a04042020_joshkane_nycschools.app.utils.dagger;

import android.content.Context;

import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {
    private Context mContext;

    public UtilModule(Context context) {
        mContext = context;
    }

    @Provides
    public StringUtils providesStringUtils() {
        return new StringUtils(mContext);
    }
}
