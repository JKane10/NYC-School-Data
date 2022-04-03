package com.jkane.a04042020_joshkane_nycschools.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain object to drive a layer of separation between application and network.
 *
 * The repositories will handle the conversion of network objects to domain objects.
 * ViewModels will only operate on domain objects.
 */

@Parcelize
data class NYCSchool(
        val id: String? = null,
        val name: String? = null,
        val borough: String? = null,
        val overview: String? = null,
        val location: String? = null,
        val phoneNumber: String? = null,
        val email: String? = null,
        val website: String? = null,
        val subways: String? = null,
        val buses: String? = null,
        val grades: String? = null,
        val numOfStudents: String? = null,
        val startTime: String? = null,
        val endTime: String? = null,
        val extracurricular: String? = null,
        val additionalInfo: String? = null,
        val graduationRate: String? = null,
        val attendanceRate: String? = null,
        val collegeRate: String? = null
) : Parcelable