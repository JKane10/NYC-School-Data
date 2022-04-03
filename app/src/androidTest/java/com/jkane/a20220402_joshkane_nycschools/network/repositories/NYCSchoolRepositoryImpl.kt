package com.jkane.a20220402_joshkane_nycschools.network.repositories

import com.jkane.a20220402_joshkane_nycschools.models.NYCSchool
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchoolSATScores
import com.jkane.a20220402_joshkane_nycschools.network.api.NYCSchoolsAPI
import javax.inject.Inject


/**
 * Having UI tests rely on network requests can cause flaky UI tests due to network instability.
 *
 * This adds a androidTest variant of the repository that just spits back a hard coded list of
 * schools for testing purposes.
 */
class NYCSchoolRepositoryImpl @Inject constructor(
    private val api: NYCSchoolsAPI
) : NYCSchoolsRepository {
    override suspend fun getSchoolList(): List<NYCSchool> = listOf(
        NYCSchool(
            name = "Test School 1",
            website = "website 1",
            phoneNumber = "phone 1",
            email = "email 1",
            location = "location 1"
        ),
        NYCSchool(
            name = "Test School 2",
            website = "website 2",
            phoneNumber = "phone 2",
            email = "email 2",
            location = "location 2"
        ),
        NYCSchool(
            name = "Test School 3",
            website = "website 3",
            phoneNumber = "phone 3",
            email = "email 3",
            location = "location 3"
        ),
    )

    override suspend fun getSATScoresByDBN(dbn: String): NYCSchoolSATScores = NYCSchoolSATScores()
}