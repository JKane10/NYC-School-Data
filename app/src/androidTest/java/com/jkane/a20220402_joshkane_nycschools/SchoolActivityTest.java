package com.jkane.a20220402_joshkane_nycschools;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SchoolActivityTest {

    @Rule
    public final ActivityTestRule getRule() {
        return new ActivityTestRule(SchoolActivity.class);
    }

    @Test
    public void test() {

    }
}
