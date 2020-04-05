package com.jkane.a04042020_joshkane_nycschools;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityTestRule getRule() {
        return new ActivityTestRule(MainActivity.class);
    }

    @Test
    public void test() {

    }
}
