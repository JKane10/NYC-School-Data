package com.jkane.a20220402_joshkane_nycschools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jkane.a20220402_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import com.jkane.a20220402_joshkane_nycschools.network.repositories.NYCSchoolRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jkane.a20220402_joshkane_nycschools.network.api.NYCSchoolsAPI
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NetworkTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    var repo: NYCSchoolsRepository? = null

    @Before
    fun setup() {
        repo = NYCSchoolRepositoryImpl(
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NYCSchoolsAPI::class.java)
        )
    }

    @Test
    fun `verify get school list api call succeeds`() = runBlocking {
        assert(repo?.getSchoolList()?.isNotEmpty() ?: false)
    }

    @Test
    fun `verify get school sat scores call is successful`() = runBlocking {
        assert(repo?.getSATScoresByDBN("21K728") != null)
    }
}