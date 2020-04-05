package com.jkane.a04042020_joshkane_nycschools.ui.schoollist;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkane.a04042020_joshkane_nycschools.MainActivity;
import com.jkane.a04042020_joshkane_nycschools.app.App;
import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolListFragmentBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.network.api.GooglePlacesAPI;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Fragment containing a list of Schools controlled by #[SchoolListRecyclerAdapter]
 */
public class SchoolListFragment extends Fragment {

    private SchoolListViewModel mViewModel;
    private SchoolListFragmentBinding binding;
    private SchoolListRecyclerAdapter adapter;

    @Inject
    NYCSchoolsRepository repo;

    @Inject
    StringUtils stringUtils;

    @Inject
    GooglePlacesAPI photoAPI;

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
        setupRecyclerView();
        setupObservers();
        setupFilter();
        if (mViewModel.getSchools().getValue() == null) mViewModel.setInitialState(repo);
    }

    private void setupRecyclerView() {
        adapter = new SchoolListRecyclerAdapter(mViewModel.getFilteredSchools().getValue(), stringUtils, photoAPI);
        binding.schoolListRecycler.setHasFixedSize(true);
        binding.schoolListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.schoolListRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener((position, v) -> {
            ((MainActivity) getActivity()).showSchoolDetails(adapter.list.get(position));
        });
    }

    private void setupObservers() {
        observeLoading();
        observeError();
        observeFilteredSchools();
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

    /**
     * Observes a filtered school list in the viewmodel.
     * Re-establishes the recyclerview when the list of filtered schools changes.
     */
    private void observeFilteredSchools() {
        mViewModel.getFilteredSchools().observe(
                getViewLifecycleOwner(),
                schools -> {
                    if (!schools.isEmpty()) {
                        adapter.updateData(schools);
                    }
                }
        );
    }

    private void setupFilter() {
        binding.clearSearchIcon.setOnClickListener(v -> {
            binding.searchInput.setText("");
        });
        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
