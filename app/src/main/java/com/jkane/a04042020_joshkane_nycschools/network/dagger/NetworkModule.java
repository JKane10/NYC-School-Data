package com.jkane.a04042020_joshkane_nycschools.network.dagger;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.jkane.a04042020_joshkane_nycschools.BuildConfig;
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolRepositoryImpl;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;
import com.readystatesoftware.chuck.ChuckInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private Context mContext;

    public NetworkModule(Context context) {
        mContext = context;
    }

    @Provides
    public NYCSchoolsRepository providesNYCSchoolsRepository(NYCSchoolRepositoryImpl repo) {
        return repo;
    }

    // TODO Add OKHttp simple caching for offline support.
    @Provides
    public NYCSchoolsAPI providesCityOfNewYorkAPI() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(mContext)).build();

        GsonConverterFactory gsonFactory = GsonConverterFactory.create(
                new GsonBuilder().setFieldNamingPolicy(
                        // maps json key of format'some_field_name' to java value 'someFieldName'
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                ).create());

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(NYCSchoolsAPI.class);
    }
}
