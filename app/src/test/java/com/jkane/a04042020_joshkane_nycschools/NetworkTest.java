package com.jkane.a04042020_joshkane_nycschools;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores;
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolRepositoryImpl;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkTest {

    private final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Rule
    public final InstantTaskExecutorRule getRule() {
        return this.rule;
    }

    public NYCSchoolsRepository repo;

    @Before
    public void Setup() {
        repo = new NYCSchoolRepositoryImpl(
                new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(NYCSchoolsAPI.class)
        );
    }

    @Test
    public void verify_school_list_call_is_successful() {
        TestObserver<List<NYCSchool>> observer = new TestObserver<>();
        TestScheduler scheduler = new TestScheduler();
        repo.getSchoolList()
                .subscribeOn(scheduler)
                .doOnNext(nycSchools -> {
                    assert true;
                })
                .doOnError(throwable -> {
                    assert (false);
                }).subscribe(observer);
        scheduler.advanceTimeBy(10, TimeUnit.SECONDS);
    }

    @Test
    public void verify_school_sat_scores_call_is_successful() {
        TestObserver<NYCSchoolSATScores> observer = new TestObserver<>();
        TestScheduler scheduler = new TestScheduler();
        repo.getSchoolByDBN("21K728")
                .subscribeOn(scheduler)
                .doOnNext(nycSchoolSATScores -> {
                    assert true;
                })
                .doOnError(throwable -> {
                    assert (false);
                }).subscribe(observer);
        scheduler.advanceTimeBy(10, TimeUnit.SECONDS);
    }
}
