package com.jkane.a20220402_joshkane_nycschools.ui.schooldetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkane.a20220402_joshkane_nycschools.R
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchool
import com.jkane.a20220402_joshkane_nycschools.models.NYCSchoolSATScores
import com.jkane.a20220402_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SchoolDetailsViewModel : ViewModel() {

    private var repo: NYCSchoolsRepository? = null

    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val filters: MutableLiveData<MutableMap<String, String>> by lazy { MutableLiveData<MutableMap<String, String>>() }
    val school: MutableLiveData<NYCSchool> by lazy { MutableLiveData<NYCSchool>() }
    val schoolScores: MutableLiveData<NYCSchoolSATScores> by lazy { MutableLiveData<NYCSchoolSATScores>() }
    val error: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    /**
     * Configuring dagger with Android ViewModels can be a bit verbose and complex.
     * Granted more time I would handle this properly, but for right now I will just
     * pass it as part of the usage of the viewmodel.
     */
    fun setInitialState(school: NYCSchool, nycSchoolRepo: NYCSchoolsRepository?) {
        repo = nycSchoolRepo
        isLoading.value = false
        filters.value = mutableMapOf()
        this.school.value = school
        schoolScores.value = null
        if (repo != null) loadSchools()
    }

    private fun loadSchools() {
        if (isLoading.value == false) {
            isLoading.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {
                school.value?.id?.let { schoolId ->
                    try {
                        schoolScores.postValue(repo?.getSATScoresByDBN(schoolId))
                    } catch (exception: Exception) {
                        error.postValue(R.string.no_sat_scores)
                    }
                    isLoading.postValue(false)
                }
            }
        }
    }
}