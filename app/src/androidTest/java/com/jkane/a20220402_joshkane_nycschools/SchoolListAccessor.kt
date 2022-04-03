package com.jkane.a20220402_joshkane_nycschools

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.platform.app.InstrumentationRegistry
import com.jkane.a20220402_joshkane_nycschools.app.utils.StringUtils
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchool
import com.jkane.a20220402_joshkane_nycschools.ui.schoollist.SchoolListRecyclerAdapter

/**
 * Bit of a hack to access underlying view adapter to allow us to define our list content for
 * testing purposes. In production would go with a more robust solution or ui test variant files.
 */
class SchoolListAccessor(private val data: List<NYCSchool>) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        (view as? RecyclerView)?.let {
            val adapter = SchoolListRecyclerAdapter(
                data,
                StringUtils(InstrumentationRegistry.getInstrumentation().targetContext),
                null
            )
            it.adapter = adapter
            it.adapter?.notifyDataSetChanged()
        }
    }

}