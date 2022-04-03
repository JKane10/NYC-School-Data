package com.jkane.a20220402_joshkane_nycschools.network.repositories

import com.jkane.a20220402_joshkane_nycschools.network.api.GooglePlacesAPI
import io.reactivex.Single
import javax.inject.Inject

/**
 * Having UI tests rely on network requests can cause flaky UI tests due to network instability.
 *
 * This adds a androidTest variant of the repository that just spits back an empty string for
 * testing purposes.
 */
class GooglePlacesRepoImpl @Inject constructor(
    val api: GooglePlacesAPI
) : GooglePlacesRepository {

    override fun getImageUrlFromSchoolAddress(address: String?): Single<String?> {
        return Single.just("")
    }
}