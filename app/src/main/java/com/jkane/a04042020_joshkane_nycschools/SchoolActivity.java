package com.jkane.a04042020_joshkane_nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.jkane.a04042020_joshkane_nycschools.app.App;
import com.jkane.a04042020_joshkane_nycschools.databinding.MainActivityBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListFragment;
import com.jkane.a04042020_joshkane_nycschools.ui.schooldetails.SchoolDetailsFragment;

import static com.jkane.a04042020_joshkane_nycschools.ui.schooldetails.SchoolDetailsFragment.IMAGE_KEY;
import static com.jkane.a04042020_joshkane_nycschools.ui.schooldetails.SchoolDetailsFragment.SCHOOL_KEY;

public class SchoolActivity extends AppCompatActivity {

    public MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getAppComponent().inject(this);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SchoolListFragment.newInstance())
                    .commitNow();
        }
    }

    public void showLoading(Boolean show) {
        binding.loadingOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showError(String errorMsg) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    public void showSchoolDetails(NYCSchool nycSchool, String imageUrl) {
        SchoolDetailsFragment fragment = SchoolDetailsFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SCHOOL_KEY, nycSchool);
        bundle.putString(IMAGE_KEY, imageUrl);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
