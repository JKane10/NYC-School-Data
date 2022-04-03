package com.jkane.a20220402_joshkane_nycschools;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.jkane.a20220402_joshkane_nycschools.models.NYCSchool;
import com.jkane.a20220402_joshkane_nycschools.ui.schoollist.SchoolListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SchoolViewModelTest {
    private final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Rule
    public final InstantTaskExecutorRule getRule() {
        return this.rule;
    }

    private SchoolListViewModel viewModel;
    private List<NYCSchool> testList = new ArrayList<>();
    private NYCSchool testNYCSchool;

    @Before
    public void Setup() {
        viewModel = new SchoolListViewModel();
        testNYCSchool = new NYCSchool("id", "Test School", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        testList.add(testNYCSchool);
    }

    @Test
    public void test_viewmodel_initial_state() {
        viewModel.setInitialState(null, null);
        assert (viewModel.getError().getValue() == null);
        assert (viewModel.getSchools().getValue().isEmpty());
        assert (viewModel.getFilteredSchools().getValue().isEmpty());
        assert (!viewModel.isLoading().getValue());
    }

    @Test
    public void test_adding_school() {
        viewModel.getSchools().postValue(testList);
        assert (!viewModel.getSchools().getValue().isEmpty());
        assert (viewModel.getSchools().getValue().get(0).getId().equals(testNYCSchool.getId()));
        assert (viewModel.getSchools().getValue().get(0).getName().equals(testNYCSchool.getName()));
    }

    @Test
    public void test_filtering_schools() {
        viewModel.getSchools().postValue(testList);
        viewModel.filter("Random Name");
        assert (viewModel.getFilteredSchools().getValue().isEmpty());
        viewModel.filter(testNYCSchool.getName());
        assert (!viewModel.getFilteredSchools().getValue().isEmpty());
    }
}
