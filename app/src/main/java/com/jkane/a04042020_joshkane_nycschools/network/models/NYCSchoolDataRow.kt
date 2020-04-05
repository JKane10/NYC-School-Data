package com.jkane.a04042020_joshkane_nycschools.network.models

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool

/**
 * Big fan of the simplicity of the Kotlin data class.
 *
 * Generated with a JSON to POJO tool.
 * Due to how open ended this project is, I figured I'd start with everything for now and narrow
 * down as I go.
 */
data class NYCSchoolDataRow(
        val dbn: String?,
        val schoolName: String?,
        val overviewParagraph: String?,
        val location: String?,
        val phoneNumber: String?,
        val schoolEmail: String?,
        val website: String?,
        val subway: String?,
        val bus: String?,
        val finalgrades: String?,
        val totalStudents: String?,
        val startTime: String?,
        val endTime: String?,
        val addtlInfo1: String?,
        val extracurricularActivities: String?,
        val graduationRate: String?,
        val attendanceRate: String?,
        val collegeCareerRate: String?,
        val borough: String?
)

/**
 * Kotlin extensions function to handle the conversion of network objects to domain objects.
 */
fun NYCSchoolDataRow.toDomainModel() = NYCSchool(
        id = dbn,
        name = schoolName,
        borough = borough,
        overview = overviewParagraph,
        location = location,
        phoneNumber = phoneNumber,
        email = schoolEmail,
        website = website,
        subways = subway,
        buses = bus,
        grades = finalgrades,
        numOfStudents = totalStudents,
        startTime = startTime,
        endTime = endTime,
        extracurricular = extracurricularActivities,
        additionalInfo = addtlInfo1,
        graduationRate = graduationRate,
        attendanceRate = attendanceRate,
        collegeRate = collegeCareerRate
)