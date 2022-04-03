package com.jkane.a04042020_joshkane_nycschools.network.api;

import com.jkane.a04042020_joshkane_nycschools.network.models.GooglePlaceResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Google Places API to look up a place by name.
 * <p>
 * Used for querying a school by it's name and then used by glide to load in associated images.
 *
 * Note: This approach would be incredibly expensive in production at scale. You'd potentially want
 * to cache results or explore other implementations
 */
public interface GooglePlacesAPI {

    @GET("findplacefromtext/json")
    Single<GooglePlaceResponse> getPlaceFromText(
            @Query(value = "input", encoded = true) String input
    );
}
