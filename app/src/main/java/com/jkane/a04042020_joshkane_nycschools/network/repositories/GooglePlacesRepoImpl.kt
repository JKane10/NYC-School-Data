package com.jkane.a04042020_joshkane_nycschools.network.repositories

import com.jkane.a04042020_joshkane_nycschools.BuildConfig
import com.jkane.a04042020_joshkane_nycschools.network.api.GooglePlacesAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GooglePlacesRepoImpl @Inject constructor(
    val api: GooglePlacesAPI
) : GooglePlacesRepository {

    override fun getImageUrlFromSchoolAddress(address: String): Single<String?> {
        return api.getPlaceFromText("\"" + address + "\"")
            .subscribeOn(Schedulers.io())
            .flatMap { googlePlaceResponse ->
                if (googlePlaceResponse.candidates.isNotEmpty() &&
                    googlePlaceResponse.candidates.first().photos != null &&
                    googlePlaceResponse.candidates.first().photos.isNotEmpty()
                ) {
                    val photoReference =
                        googlePlaceResponse.candidates.first().photos.first().photoReference
                    val photoUrl = "${BuildConfig.PLACES_BASE_URL}photo?" +
                            "key=${BuildConfig.GOOGLE_PLACES_API_KEY}&" +
                            "maxheight=500&" +
                            "maxwidth=500&" +
                            "photoreference=$photoReference"
                    Single.just(photoUrl)
                } else {
                    Single.just("")
                }
            }
    }
}