package com.jkane.a04042020_joshkane_nycschools.network.dagger;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.jkane.a04042020_joshkane_nycschools.BuildConfig;
import com.jkane.a04042020_joshkane_nycschools.network.api.GooglePlacesAPI;
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolRepositoryImpl;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    @Provides
    public NYCSchoolsAPI providesCityOfNewYorkAPI() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(mContext))
                .build();

        GsonConverterFactory gsonFactory = GsonConverterFactory.create(
                new GsonBuilder().setFieldNamingPolicy(
                        // maps json key of format 'some_field_name' to java value 'someFieldName'
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                ).create());

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(gsonFactory)
                .client(client)
                .build()
                .create(NYCSchoolsAPI.class);
    }

    @Provides
    GooglePlacesAPI providesGooglePlacesAPI() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new GoogleMapsInterceptor())
                .addInterceptor(new ChuckInterceptor(mContext))
                .build();

        GsonConverterFactory gsonFactory = GsonConverterFactory.create(
                new GsonBuilder().setFieldNamingPolicy(
                        // maps json key of format 'some_field_name' to java value 'someFieldName'
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                ).create());

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_BASE_URL)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(GooglePlacesAPI.class);
    }

    /**
     * Intercepts request and adds required parameters to every API call.
     * Since this usage is strictly for photos, I've included fields:photos as the default.
     */
    class GoogleMapsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalUrl = original.url();

            HttpUrl url = originalUrl.newBuilder()
                    .addQueryParameter("key", BuildConfig.GOOGLE_PLACES_API_KEY)
                    .addQueryParameter("fields", "photos")
                    .addQueryParameter("inputtype", "textquery")
                    .build();

            Request.Builder requestBuilder = original.newBuilder().url(url);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
