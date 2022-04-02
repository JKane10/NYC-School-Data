package com.jkane.a04042020_joshkane_nycschools.network.api

import retrofit2.http.GET
import com.jkane.a04042020_joshkane_nycschools.network.models.NYCSchoolDataRow
import com.jkane.a04042020_joshkane_nycschools.network.models.NYCSchoolSATData
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Looked into the Soda Java SDK (https://github.com/socrata/soda-java).
 * If this was a full fledged project interacting with a SODA
 * API maybe I'd consider leveraging the SDK, but since I'm only reading pretty simple data
 * I decided to stick with retrofit due to familiarity and ease of use.
 */
interface NYCSchoolsAPI {
    @GET("resource/{resourceId}.json")
    suspend fun getResource(@Path("resourceId") id: String?): Response<List<NYCSchoolDataRow>>

    @GET("resource/{resourceId}.json")
    suspend fun getResourceFilteredByDBN(
        @Path("resourceId") id: String?,
        @Query("dbn") dbn: String?
    ): Response<List<NYCSchoolSATData>>
}