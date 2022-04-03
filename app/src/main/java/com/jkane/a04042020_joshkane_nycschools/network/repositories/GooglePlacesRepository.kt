package com.jkane.a04042020_joshkane_nycschools.network.repositories

import io.reactivex.Single

interface GooglePlacesRepository {
    fun getImageUrlFromSchoolAddress(address: String): Single<String?>
}