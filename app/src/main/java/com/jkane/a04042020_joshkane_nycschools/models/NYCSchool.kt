package com.jkane.a04042020_joshkane_nycschools.models

import com.jkane.a04042020_joshkane_nycschools.network.models.NYCSchoolDataRow

/**
 * Domain object to drive a layer of separation between application and network.
 *
 * The repositories will handle the conversion of network objects to domain objects.
 * ViewModels will only operate on domain objects.
 */

data class NYCSchool(
        val id: String,
        val name: String,
        val borough: String,
        val overview: String,
        val location: String,
        val phoneNumber: String,
        val faxNumber: String,
        val email: String,
        val website: String,
        val subways: List<String>,
        val buses: List<String>,
        val grades: String,
        val numOfStudents: String,
        val startTime: String,
        val endTime: String,
        val extracurricular: List<String>,
        val additionalInfo: String
)