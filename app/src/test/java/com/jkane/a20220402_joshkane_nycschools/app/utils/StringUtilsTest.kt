package com.jkane.a20220402_joshkane_nycschools.app.utils

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class StringUtilsTest {

    private val defaultString = "DEFAULT"
    private val mockContext: Context = mockk {
        every { getString(any()) } returns defaultString
    }
    private val stringUtil = StringUtils(mockContext)

    @Test
    fun `When given a valid string, valueOrUnavailable returns that string`() {
        val validString = "validString"
        assert(stringUtil.valueOrUnavailable(validString).equals(validString))
    }

    @Test
    fun `When given an invalid string, ensure valueOrUnavailable returns a default string`() {
        val invalidString: String? = null
        assert(stringUtil.valueOrUnavailable(invalidString).equals(defaultString))
    }

    @Test
    fun `When given a valid address, ensure addressOrUnavailable returns a valid address`() {
        val validString = "1 Street Way, New York, NY, 10011 (555, -5555)"
        val expectedString = "1 Street Way, New York, NY, 10011"
        assert(stringUtil.addressOrUnavailable(validString).equals(expectedString))
    }

    @Test
    fun `When given an invalid address, ensure addressOrUnavailable returns a default string`() {
        val invalidString: String? = null
        val expectedString = defaultString
        assert(stringUtil.addressOrUnavailable(invalidString).equals(expectedString))
    }

    @Test
    fun `When given a valid percent, ensure percentOrUnavailable returns a valid percent`() {
        val validString = "0.9700003"
        val expectedString = "97%"
        assert(stringUtil.percentOrUnavailable(validString).equals(expectedString))
    }

    @Test
    fun `When given an invalid percent, ensure percentOrUnavailable returns a default string`() {
        val invalidString: String? = null
        val expectedString = defaultString
        assert(stringUtil.addressOrUnavailable(invalidString).equals(expectedString))
    }
}