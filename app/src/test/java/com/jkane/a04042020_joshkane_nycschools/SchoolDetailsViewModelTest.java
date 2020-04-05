package com.jkane.a04042020_joshkane_nycschools;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchoolSATScores;
import com.jkane.a04042020_joshkane_nycschools.ui.schooldetails.SchoolDetailsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SchoolDetailsViewModelTest {
    private final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Rule
    public final InstantTaskExecutorRule getRule() {
        return this.rule;
    }

    private SchoolDetailsViewModel viewModel;
    private NYCSchool testNYCSchool;
    private NYCSchoolSATScores testNYCSchoolSATScores;

    @Before
    public void Setup() {
        viewModel = new SchoolDetailsViewModel();
        testNYCSchool = new NYCSchool("id", "Test School", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        testNYCSchoolSATScores = new NYCSchoolSATScores("id", "name", "", "", "", "");
    }

    @Test
    public void test_viewmodel_initial_state() {
        viewModel.setInitialState(testNYCSchool, null);
        assert (viewModel.getError().getValue() == null);
        assert (viewModel.getFilters().getValue().isEmpty());
        assert (viewModel.getSchool().getValue() == testNYCSchool);
        assert (viewModel.getSchoolScores().getValue() == null);
        assert (!viewModel.isLoading().getValue());
    }

    @Test
    public void test_adding_scores() {
        viewModel.getSchoolScores().postValue(testNYCSchoolSATScores);
        assert (viewModel.getSchoolScores().getValue() != null);
        assert (viewModel.getSchoolScores().getValue().getId().equals(testNYCSchoolSATScores.getId()));
        assert (viewModel.getSchoolScores().getValue().getName().equals(testNYCSchoolSATScores.getName()));
    }
}
