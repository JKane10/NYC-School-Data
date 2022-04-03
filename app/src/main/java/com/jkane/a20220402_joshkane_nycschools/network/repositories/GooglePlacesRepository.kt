package com.jkane.a20220402_joshkane_nycschools.network.repositories

import io.reactivex.Single

interface GooglePlacesRepository {
    fun getImageUrlFromSchoolAddress(address: String?): Single<String?>
}