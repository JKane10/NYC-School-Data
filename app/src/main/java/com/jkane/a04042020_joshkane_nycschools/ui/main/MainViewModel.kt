package com.jkane.a04042020_joshkane_nycschools.ui.main

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkane.a04042020_joshkane_nycschools.R
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Contains logic for maintaining and filtering schools data and acts as view state.
 * These values are observed by #[MainFragment].
 *
 * isLoading - controls UI loading overlay.
 * filters - maintains state of data filters.
 * schools - LiveData wrapper around list of school object.
 * error - maintains error state for UI.
 */
class MainViewModel() : ViewModel() {
    private var repo: NYCSchoolsRepository? = null

    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val filters: MutableLiveData<MutableMap<String, String>> by lazy { MutableLiveData<MutableMap<String, String>>() }
    val schools: MutableLiveData<List<NYCSchool>> by lazy { MutableLiveData<List<NYCSchool>>() }
    val error: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    /**
     * Configuring dagger with Android ViewModels can be a bit verbose and complex.
     * Granted more time I would handle this properly, but for right now I will just
     * pass it as part of the usage of the viewmodel.
     */
    fun setInitialState(nycSchoolRepo: NYCSchoolsRepository) {
        repo = nycSchoolRepo
        isLoading.value = false
        filters.value = mutableMapOf()
        schools.value = listOf()
        if (repo != null) loadSchools()
    }

    fun clearAndRefresh() {
        isLoading.value = false
        filters.value = mutableMapOf()
        schools.value = listOf()
        if (repo != null) loadSchools()
    }

    private fun loadSchools() {
        if (isLoading.value == false) {
            isLoading.postValue(true)
            repo?.schoolList?.subscribeOn(Schedulers.io())?.doOnNext {
                schools.postValue(it)
            }?.doOnError {
                error.postValue(R.string.user_error_msg)
            }?.doFinally {
                isLoading.postValue(false)
            }?.subscribe()
        }
    }
}