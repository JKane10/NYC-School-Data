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
        val boro: String?,
        val overview_paragraph: String?,
        val school10thSeats: String?,
        val academicopportunities1: String?,
        val academicopportunities2: String?,
        val academicopportunities3: String?,
        val academicopportunities4: String?,
        val academicopportunities5: String?,
        val ell_programs: String?,
        val language_classes: String?,
        val advancedplacementCourses: String?,
        val neighborhood: String?,
        val building_code: String?,
        val location: String?,
        val phone_number: String?,
        val fax_number: String?,
        val school_email: String?,
        val website: String?,
        val subway: String?,
        val bus: String?,
        val finalgrades: String?,
        val total_students: String?,
        val start_time: String?,
        val end_time: String?,
        val addtl_info1: String?,
        val extracurricular_activities: String?,
        val graduation_rate: String?,
        val attendance_rate: String?,
        val college_career_rate: String?,
        val primary_address_line_1: String?,
        val city: String?,
        val zip: String?,
        val state_code: String?,
        val latitude: String?,
        val longitude: String?,
        val community_board: String?,
        val council_district: String?,
        val census_tract: String?,
        val bin: String?,
        val bbl: String?,
        val nta: String?,
        val borough: String?
)

/**
 * Kotlin extensions function to handle the conversion of network objects to domain objects.
 */
fun NYCSchoolDataRow.toDomainModel() = NYCSchool(
        id = dbn ?: "Not available",
        name = schoolName ?: "Not available",
        borough = borough ?: "Not available",
        overview = overview_paragraph ?: "Not available",
        location = location ?: "Not available",
        phoneNumber = phone_number ?: "Not available",
        faxNumber = fax_number ?: "Not available",
        email = school_email ?: "Not available",
        website = website ?: "Not available",
        subways = subway?.split(", ") ?: emptyList(),
        buses = bus?.split(", ") ?: emptyList(),
        grades = finalgrades ?: "Not available",
        numOfStudents = total_students ?: "Not available",
        startTime = start_time ?: "Not available",
        endTime = end_time ?: "Not available",
        extracurricular = extracurricular_activities?.split(", ") ?: emptyList(),
        additionalInfo = addtl_info1 ?: "Not available"
)