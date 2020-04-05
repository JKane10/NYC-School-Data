package com.jkane.a04042020_joshkane_nycschools.ui.schoollist;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkane.a04042020_joshkane_nycschools.MainActivity;
import com.jkane.a04042020_joshkane_nycschools.app.App;
import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolListFragmentBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SchoolListFragment extends Fragment {

    private SchoolListViewModel mViewModel;
    private SchoolListFragmentBinding binding;
    private SchoolListRecyclerAdapter adapter;

    @Inject
    NYCSchoolsRepository repo;

    @Inject
    StringUtils stringUtils;

    public static SchoolListFragment newInstance() {
        return new SchoolListFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SchoolListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SchoolListViewModel.class);
        setupRecyclerView(new ArrayList<>());
        setupObservers();
        if (mViewModel.getSchools().getValue() == null) mViewModel.setInitialState(repo);
    }

    private void setupRecyclerView(List<NYCSchool> list) {
        adapter = new SchoolListRecyclerAdapter(list, stringUtils);
        binding.schoolListRecycler.setHasFixedSize(true);
        binding.schoolListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.schoolListRecycler.setAdapter(adapter);

        adapter = new SchoolListRecyclerAdapter(mViewModel.getSchools().getValue(), stringUtils);

        adapter.setOnItemClickListener((position, v) -> {
            ((MainActivity) getActivity()).showSchoolDetails(adapter.list.get(position));
        });
    }

    private void setupObservers() {
        observeLoading();
        observeError();
        observeSchools();
    }

    /**
     * Observes isLoading on the ViewModel and reaches out to parent activity to
     * render a loading overlay accordingly.
     * <p>
     * This tightly couples this fragment to MainActivity.
     * TODO Decouple this by creating a base fragment or wrapper fragment.
     * <p>
     * Loading state is currently being controlled by #[MainViewModel#loadSchools()].
     */
    private void observeLoading() {
        mViewModel.isLoading().observe(
                getViewLifecycleOwner(),
                isLoading -> {
                    ((MainActivity) getActivity()).showLoading(isLoading);
                }
        );
    }

    /**
     * Observes error on the ViewModel and reaches out to parent activity to
     * render a snackbar containing a user friendly error message if one is posted.
     * <p>
     * This tightly couples this fragment to MainActivity.
     * TODO Decouple this by creating a base fragment or wrapper fragment.
     * <p>
     * <p>
     * Error contains an int that represents a string value and will need to be provided
     * context in order to access the resource id. This is for ease of localization and
     * keeping all string values in the resource file.
     */
    private void observeError() {
        mViewModel.getError().observe(
                getViewLifecycleOwner(),
                error -> ((MainActivity) getActivity()).showError(getString(error))
        );
    }

    private void observeSchools() {
        mViewModel.getSchools().observe(
                getViewLifecycleOwner(),
                schools -> {
                    if (!schools.isEmpty()) {
                        setupRecyclerView(schools);
                    }
                }
        );
    }
}
