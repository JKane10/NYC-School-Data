package com.jkane.a04042020_joshkane_nycschools.network.repositories

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores

interface NYCSchoolsRepository {
    suspend fun getSchoolList(): List<NYCSchool>
    suspend fun getSATScoresByDBN(dbn: String): NYCSchoolSATScores
}