package com.jkane.a04042020_joshkane_nycschools.ui.schoollist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkane.a04042020_joshkane_nycschools.R
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.network.observers.NetworkObserver
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Contains logic for maintaining and filtering schools data and acts as view state.
 * These values are observed by #[SchoolListFragment].
 *
 * isLoading - controls UI loading overlay.
 * filters - maintains state of data filters.
 * schools - LiveData wrapper around list of school object.
 * error - maintains error state for UI.
 */
class SchoolListViewModel() : ViewModel() {
    private var repo: NYCSchoolsRepository? = null

    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val schools: MutableLiveData<List<NYCSchool>> by lazy { MutableLiveData<List<NYCSchool>>() }
    val filteredSchools: MutableLiveData<List<NYCSchool>> by lazy { MutableLiveData<List<NYCSchool>>() }
    val error: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    /**
     * Configuring dagger with Android ViewModels can be a bit verbose and complex.
     * Granted more time I would handle this properly, but for right now I will just
     * pass it as part of the usage of the viewmodel.
     */
    fun setInitialState(nycSchoolRepo: NYCSchoolsRepository?) {
        repo = nycSchoolRepo
        isLoading.value = false
        schools.value = listOf()
        filteredSchools.value = listOf()
        if (repo != null) loadSchools()
    }

    /**
     * Loads schools from the repository and sets loading state correctly.
     */
    private fun loadSchools() {
        if (isLoading.value == false) {
            isLoading.postValue(true)
            repo?.schoolList?.subscribeOn(Schedulers.io())?.doOnNext {
                schools.postValue(it)
                filteredSchools.postValue(it)
            }?.doOnError {
                error.postValue(R.string.user_error_msg)
            }?.doFinally {
                isLoading.postValue(false)
            }?.subscribe(NetworkObserver())
        }
    }

    /**
     * Filters source list of schools based on filter input and updates observed filtered list
     * of schools.
     */
    fun filter(filter: String) {
        filteredSchools.postValue(schools.value?.filter {
            it.name?.toLowerCase(Locale.getDefault())?.contains(
                    filter.toLowerCase(Locale.getDefault())
            ) ?: true
        })
    }
}