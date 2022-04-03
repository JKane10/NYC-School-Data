package com.jkane.a04042020_joshkane_nycschools.ui.schooldetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jkane.a04042020_joshkane_nycschools.SchoolActivity;
import com.jkane.a04042020_joshkane_nycschools.app.App;
import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolDetailsFragmentBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository;

import javax.inject.Inject;

/**
 * Fragment that contains school details.
 */
public class SchoolDetailsFragment extends Fragment {

    public static String SCHOOL_KEY = "SCHOOL_KEY";
    public static String IMAGE_KEY = "IMAGE_KEY";
    private SchoolDetailsViewModel mViewModel;
    private SchoolDetailsFragmentBinding binding;

    @Inject
    NYCSchoolsRepository repo;

    @Inject
    StringUtils stringUtil;

    public static SchoolDetailsFragment newInstance() {
        return new SchoolDetailsFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SchoolDetailsFragmentBinding.inflate(inflater, container, false);
        NYCSchool school = requireArguments().getParcelable(SCHOOL_KEY);
        String imageUrl = requireArguments().getString(IMAGE_KEY);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(binding.getRoot())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.image);
        }

        mViewModel = ViewModelProviders.of(this).get(SchoolDetailsViewModel.class);
        setupObservers();

        mViewModel.setInitialState(school, repo);
        return binding.getRoot();
    }

    private void setupObservers() {
        observeLoading();
        observeError();
        observeSchool();
        observeSATScores();
    }

    private void observeSATScores() {
        mViewModel.getSchoolScores().observe(
                getViewLifecycleOwner(),
                scores -> {
                    if (scores != null) {
                        binding.numTests.setText(stringUtil.valueOrUnavailable(scores.getNumberOfTest()));
                        binding.readScore.setText(stringUtil.valueOrUnavailable(scores.getReadingAvg()));
                        binding.mathScore.setText(stringUtil.valueOrUnavailable(scores.getMathAvg()));
                        binding.writingScore.setText(stringUtil.valueOrUnavailable(scores.getWritingAvg()));
                    }
                }
        );
    }

    private void observeSchool() {
        mViewModel.getSchool().observe(
                getViewLifecycleOwner(),
                school -> {
                    if (school != null) {
                        binding.name.setText(stringUtil.valueOrUnavailable(school.getName()));
                        binding.hours.setText((stringUtil.valueOrUnavailable(school.getStartTime()) + " - " + stringUtil.valueOrUnavailable(school.getEndTime())));
                        binding.website.setText(stringUtil.valueOrUnavailable(school.getWebsite()));
                        binding.phone.setText(stringUtil.valueOrUnavailable(school.getPhoneNumber()));
                        binding.email.setText(stringUtil.valueOrUnavailable(school.getEmail()));
                        binding.address.setText(stringUtil.addressOrUnavailable(school.getLocation()));
                        binding.overview.setText(stringUtil.valueOrUnavailable(school.getOverview()));
                        binding.buses.setText(stringUtil.valueOrUnavailable(school.getBuses()));
                        binding.subway.setText(stringUtil.valueOrUnavailable(school.getSubways()));
                        binding.attendance.setText(stringUtil.percentOrUnavailable(school.getAttendanceRate()));
                        binding.graduation.setText(stringUtil.percentOrUnavailable(school.getGraduationRate()));
                        binding.college.setText(stringUtil.percentOrUnavailable(school.getCollegeRate()));
                        binding.extracurricular.setText(stringUtil.valueOrUnavailable(school.getExtracurricular()));
                    }
                }
        );
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
                isLoading -> ((SchoolActivity) requireActivity()).showLoading(isLoading)
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
                error -> ((SchoolActivity) requireActivity()).showError(getString(error))
        );
    }
}
