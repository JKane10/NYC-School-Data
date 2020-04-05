package com.jkane.a04042020_joshkane_nycschools.network.repositories

import com.jkane.a04042020_joshkane_nycschools.BuildConfig
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI
import com.jkane.a04042020_joshkane_nycschools.network.models.toDomainModel
import io.reactivex.Observable
import javax.inject.Inject

class NYCSchoolRepositoryImpl @Inject constructor(
        private val api: NYCSchoolsAPI
) : NYCSchoolsRepository {
    override fun getSchoolList(): Observable<List<NYCSchool>> =
            api.getResource(BuildConfig.SCHOOL_DIRECTORY_2017).map { list ->
                list.map { school ->
                    school.toDomainModel()
                }
            }

    override fun getSchoolByDBN(dbn: String): Observable<NYCSchoolSATScores> =
            api.getResourceFilteredByDBN(BuildConfig.SAT_2012, dbn).map { list ->
                list.map { school ->
                    school.toDomainModel()
                }.first()
            }
}