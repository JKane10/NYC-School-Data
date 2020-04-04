package com.jkane.a04042020_joshkane_nycschools.network.repositories

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.network.api.NYCSchoolsAPI
import com.jkane.a04042020_joshkane_nycschools.network.models.toDomainModel
import io.reactivex.Observable
import javax.inject.Inject

class NYCSchoolRepositoryImpl @Inject constructor(
        private val api: NYCSchoolsAPI
) : NYCSchoolsRepository {
    override fun getSchoolList(): Observable<List<NYCSchool>> =
            // TODO move this to a buildconfig value
            api.getResource("s3k6-pzi2").map { list ->
                list.map { school ->
                    school.toDomainModel()
                }
            }

    override fun getSchoolByDBN(dbn: String): Observable<List<NYCSchool>> =
            // TODO move this to a buildconfig value
            api.getResourceFilteredByDBN("f9bf-2cp4", dbn).map { list ->
                list.map { school ->
                    school.toDomainModel()
                }
            }
}