package com.jkane.a04042020_joshkane_nycschools.network.repositories

import com.jkane.a04042020_joshkane_nycschools.BuildConfig
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI
import com.jkane.a04042020_joshkane_nycschools.network.models.toDomainModel
import javax.inject.Inject

class NYCSchoolRepositoryImpl @Inject constructor(
    private val api: NYCSchoolsAPI
) : NYCSchoolsRepository {
    override suspend fun getSchoolList(): List<NYCSchool> =
        api.getResource(BuildConfig.SCHOOL_DIRECTORY_2017).body()!!.map { school ->
            school.toDomainModel()
        }
//    {
//        val response = api.getResource(BuildConfig.SCHOOL_DIRECTORY_2017)
//        if (!response.isSuccessful && response.body().isNullOrEmpty()) {
//            throw Exception()
//        }
//        return response.body()!!.map { school ->
//            school.toDomainModel()
//        }
//    }

    override suspend fun getSATScoresByDBN(dbn: String): NYCSchoolSATScores =
        api.getResourceFilteredByDBN(BuildConfig.SAT_2012, dbn).body()!!.first().toDomainModel()

//    {
//        val response = api.getResourceFilteredByDBN(BuildConfig.SAT_2012, dbn)
//        if (!response.isSuccessful && response.body().isNullOrEmpty()) {
//            throw Exception()
//        } else {
//            return response.body()!!.first().toDomainModel()
//        }
//    }
}