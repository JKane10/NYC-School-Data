package com.jkane.a04042020_joshkane_nycschools.network.models

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores

data class NYCSchoolSATData(
        val dbn: String?,
        val school_name: String?,
        val num_of_sat_test_takers: String?,
        val sat_critical_reading_avg_score: String?,
        val sat_math_avg_score: String?,
        val sat_writing_avg_score: String?
)

/**
 * Kotlin extensions function to handle the conversion of network objects to domain objects.
 */
fun NYCSchoolSATData.toDomainModel() = NYCSchoolSATScores(
        id = dbn,
        name = school_name,
        numberOfTest = num_of_sat_test_takers,
        readingAvg = sat_critical_reading_avg_score,
        mathAvg = sat_math_avg_score,
        writingAvg = sat_writing_avg_score
)