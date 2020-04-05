package com.jkane.a04042020_joshkane_nycschools.network.api;

import com.jkane.a04042020_joshkane_nycschools.network.models.NYCSchoolDataRow;
import com.jkane.a04042020_joshkane_nycschools.network.models.NYCSchoolSATData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Looked into the Soda Java SDK (https://github.com/socrata/soda-java).
 * If this was a full fledged project interacting with a SODA
 * API maybe I'd consider leveraging the SDK, but since I'm only reading pretty simple data
 * I decided to stick with retrofit due to familiarity.
 */
public interface NYCSchoolsAPI {

    @GET("resource/{resourceId}.json")
    Observable<List<NYCSchoolDataRow>> getResource(@Path("resourceId") String id);

    @GET("resource/{resourceId}.json")
    Observable<List<NYCSchoolSATData>> getResourceFilteredByDBN(
            @Path("resourceId") String id,
            @Query("dbn") String dbn
    );
}
