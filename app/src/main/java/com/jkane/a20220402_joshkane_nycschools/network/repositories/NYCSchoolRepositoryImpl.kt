package com.jkane.a20220402_joshkane_nycschools.network.repositories

import com.jkane.a20220402_joshkane_nycschools.BuildConfig
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchool
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchoolSATScores
import com.jkane.a20220402_joshkane_nycschools.network.api.NYCSchoolsAPI
import com.jkane.a20220402_joshkane_nycschools.network.models.toDomainModel
import javax.inject.Inject

class NYCSchoolRepositoryImpl @Inject constructor(
    private val api: NYCSchoolsAPI
) : NYCSchoolsRepository {
    override suspend fun getSchoolList(): List<NYCSchool> =
        api.getResource(BuildConfig.SCHOOL_DIRECTORY_2017).body()?.map { school ->
            school.toDomainModel()
        } ?: emptyList()

    override suspend fun getSATScoresByDBN(dbn: String): NYCSchoolSATScores =
        api.getResourceFilteredByDBN(BuildConfig.SAT_2012, dbn).body()?.first()?.toDomainModel()
            ?: NYCSchoolSATScores()
}