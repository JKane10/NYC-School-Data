package com.jkane.a20220402_joshkane_nycschools

fun schoolList(func: SchoolListRobot.() -> Unit) = SchoolListRobot().apply { func() }

class SchoolListRobot : BaseTestRobot() {

    fun checkSearchBar() = isHintDisplayed("Search")

    fun checkSchools() {
        if (!BuildConfig.COMPOSE_VIEW) {
            for (i in 1 until 4) {
                isTextDisplayed("Test School $i")
                isTextDisplayed("website $i")
                isTextDisplayed("phone $i")
                isTextDisplayed("email $i")
                isTextDisplayed("location $i")
            }
        }
    }

    fun tapFirstSchool() {
        if (!BuildConfig.COMPOSE_VIEW) {
            clickViewWithText("Test School 1")
            Thread.sleep(1000)
            isViewDisplayed(R.id.contact_info_header)
        }
    }
}