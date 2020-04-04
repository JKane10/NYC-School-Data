package com.jkane.a04042020_joshkane_nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.jkane.a04042020_joshkane_nycschools.app.App;
import com.jkane.a04042020_joshkane_nycschools.databinding.MainActivityBinding;
import com.jkane.a04042020_joshkane_nycschools.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    public MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getAppComponent().inject(this);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public void showLoading(Boolean show) {
        binding.loadingOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showError(String errorMsg) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    public void showSwipeToRefresh(Boolean show) {
        if (!show) binding.swipeToRefresh.setRefreshing(false);
    }
}
