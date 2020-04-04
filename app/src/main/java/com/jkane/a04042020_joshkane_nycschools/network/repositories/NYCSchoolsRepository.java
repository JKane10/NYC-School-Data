package com.jkane.a04042020_joshkane_nycschools.network.repositories;

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;

import java.util.List;

import io.reactivex.Observable;

public interface NYCSchoolsRepository {
    public Observable<List<NYCSchool>> getSchoolList();
    public Observable<List<NYCSchool>> getSchoolByDBN(String dbn);
}
