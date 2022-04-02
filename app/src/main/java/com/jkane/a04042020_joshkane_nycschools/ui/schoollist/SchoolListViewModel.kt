package com.jkane.a04042020_joshkane_nycschools.ui.schoollist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkane.a04042020_joshkane_nycschools.R
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Contains logic for maintaining and filtering schools data and acts as view state.
 * These values are observed by #[SchoolListFragment].
 *
 * isLoading - controls UI loading overlay.
 * filters - maintains state of data filters.
 * schools - LiveData wrapper around list of school object.
 * error - maintains error state for UI.
 */
class SchoolListViewModel : ViewModel() {
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
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repo?.getSchoolList().let {
                        schools.postValue(it)
                        filteredSchools.postValue(it)
                    }
                } catch (exception: Exception) {
                    error.postValue(R.string.user_error_msg)
                }
                isLoading.postValue(false)
            }
        }
    }

    /**
     * Filters source list of schools based on filter input and updates observed filtered list
     * of schools.
     */
    fun filter(filter: String) {
        filteredSchools.postValue(schools.value?.filter {
            it.name?.lowercase(Locale.getDefault())?.contains(
                filter.lowercase(Locale.getDefault())
            ) ?: false
        })
    }
}