package com.jkane.a04042020_joshkane_nycschools.network.api;

import com.jkane.a04042020_joshkane_nycschools.network.models.GooglePlaceResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Google Places API to look up a place by name.
 * <p>
 * Used for querying a school by it's name and then used by glide to load in associated images.
 */
public interface GooglePlacesAPI {

    @GET("findplacefromtext/json")
    Observable<GooglePlaceResponse> getPlaceFromText(
            @Query(value = "input", encoded = true) String input
    );
}
