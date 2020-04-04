package com.jkane.a04042020_joshkane_nycschools.ui.main;

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
import com.jkane.a04042020_joshkane_nycschools.databinding.MainFragmentBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    private SchoolListRecyclerAdapter adapter;

    @Inject
    protected NYCSchoolsRepository repo;

    public static MainFragment newInstance() {
        return new MainFragment();
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
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setupRecyclerView(new ArrayList<>());
        setupObservers();
        mViewModel.setInitialState(repo);
    }

    private void setupRecyclerView(List<NYCSchool> list) {
        adapter = new SchoolListRecyclerAdapter(list);
        binding.schoolListRecycler.setHasFixedSize(true);
        binding.schoolListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.schoolListRecycler.setAdapter(adapter);

        adapter = new SchoolListRecyclerAdapter(mViewModel.getSchools().getValue());

        adapter.setOnItemClickListener((position, v) -> {
            adapter.list.get(position).getId();
        });
    }

    private void setupObservers() {
        observeLoading();
        observeError();
        observeSchools();
        // this doesn't seem kosher - need to look into a better way to share these elements
        ((MainActivity) getActivity()).binding.swipeToRefresh
                .setOnRefreshListener(() -> mViewModel.clearAndRefresh());
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
                    // TODO These should probably be separated out to be handled independently.
                    ((MainActivity) getActivity()).showLoading(isLoading);
                    ((MainActivity) getActivity()).showSwipeToRefresh(isLoading);
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
