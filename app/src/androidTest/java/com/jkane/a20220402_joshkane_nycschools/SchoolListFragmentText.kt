package com.jkane.a20220402_joshkane_nycschools

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SchoolListFragmentText {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<SchoolActivity> =
        ActivityTestRule(SchoolActivity::class.java)

    @Test
    fun verify_search_bar_is_displayed() {
        schoolList {
            checkSearchBar()
        }
    }

    @Test
    fun verify_a_school_in_list_is_displayed_properly() {
        schoolList {
            checkSchools()
        }
    }

    @Test
    fun verify_tapping_a_school_navigates_to_details() {
        schoolList {
            tapFirstSchool()
            Thread.sleep(5000)
        }
    }
}