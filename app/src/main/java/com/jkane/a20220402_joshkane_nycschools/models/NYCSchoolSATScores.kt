package com.jkane.a20220402_joshkane_nycschools.models

/**
 * Domain object to drive a layer of separation between application and network.
 *
 * The repositories will handle the conversion of network objects to domain objects.
 * ViewModels will only operate on domain objects.
 */
class NYCSchoolSATScores(
        val id: String? = null,
        val name: String? = null,
        val numberOfTest: String? = null,
        val readingAvg: String? = null,
        val mathAvg: String? = null,
        val writingAvg: String? = null
)