package com.jkane.a20220402_joshkane_nycschools.network.repositories

import com.jkane.a20220402_joshkane_nycschools.network.api.GooglePlacesAPI
import io.reactivex.Single
import javax.inject.Inject

class GooglePlacesRepoImpl @Inject constructor(
    val api: GooglePlacesAPI
) : GooglePlacesRepository {

    override fun getImageUrlFromSchoolAddress(address: String?): Single<String?> {
        return Single.just("")
    }
}