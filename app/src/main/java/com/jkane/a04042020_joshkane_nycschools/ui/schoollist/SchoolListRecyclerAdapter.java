package com.jkane.a04042020_joshkane_nycschools.ui.schoollist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jkane.a04042020_joshkane_nycschools.BuildConfig;
import com.jkane.a04042020_joshkane_nycschools.R;
import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolAdapterViewBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;
import com.jkane.a04042020_joshkane_nycschools.network.api.GooglePlacesAPI;
import com.jkane.a04042020_joshkane_nycschools.network.observers.NetworkObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchoolListRecyclerAdapter extends RecyclerView.Adapter<SchoolListRecyclerAdapter.SchoolViewHolder> {

    List<NYCSchool> list;
    private static SchoolListClickListener listener;
    private GooglePlacesAPI photoAPI;

    public StringUtils stringUtils;

    public SchoolListRecyclerAdapter(List<NYCSchool> list, StringUtils stringUtils, GooglePlacesAPI photoAPI) {
        this.list = list == null ? new ArrayList<>() : list;
        this.stringUtils = stringUtils;
        this.photoAPI = photoAPI;
    }

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SchoolAdapterViewBinding binding = SchoolAdapterViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new SchoolViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<NYCSchool> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SchoolListClickListener listener) {
        SchoolListRecyclerAdapter.listener = listener;
    }

    public interface SchoolListClickListener {
        void onItemClick(int position, View v);
    }

    class SchoolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SchoolAdapterViewBinding binding;

        SchoolViewHolder(@NonNull SchoolAdapterViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        // TODO would be cool to use Google or Maps API to pull a thumbnail image based on address.
        void bind(NYCSchool school) {
            binding.name.setText(stringUtils.valueOrUnavailable(school.getName()));
            binding.website.setText(stringUtils.valueOrUnavailable(school.getWebsite()));
            binding.address.setText(stringUtils.addressOrUnavailable(school.getLocation()));
            binding.phone.setText(stringUtils.valueOrUnavailable(school.getPhoneNumber()));
            binding.email.setText(stringUtils.valueOrUnavailable(school.getEmail()));

            if (BuildConfig.PLACES_API_ENABLED) loadImage(school.getName());
        }

        /**
         * Ran a little off the rails here but was curious. This is a bit hacky, but essentially
         * using the Google Place API and the school name to load Google images.
         * <p>
         * This will only run if the PLACES_API_ENABLED flag is set to true in the build config
         * AND a valid Google API key is provided.
         * <p>
         * This was done in place to only load images as they're bound to the recycler.
         * This should probably be moved out to a separate utility class though and called from
         * here. Hardcoded the url params to save some time.
         *
         * @param schoolName Name of the school to be used for a Google Place Query.
         */
        private void loadImage(String schoolName) {

            photoAPI.getPlaceFromText("\"" + schoolName + "\"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(googlePlaceResponse -> {
                        String photoUrl = null;
                        if (!googlePlaceResponse.candidates.isEmpty() &&
                                googlePlaceResponse.candidates.get(0).photos != null &&
                                !googlePlaceResponse.candidates.get(0).photos.isEmpty()
                        ) {
                            String photoReference = googlePlaceResponse.candidates.get(0).photos
                                    .get(0).photoReference;
                            photoUrl = BuildConfig.PLACES_BASE_URL + "photo?key=" +
                                    BuildConfig.GOOGLE_PLACES_API_KEY +
                                    "&maxheight=100&maxwidth=100&photoreference=" + photoReference;
                        }
                        binding.image.setVisibility(View.VISIBLE);
                        Glide
                                .with(binding.getRoot())
                                .load(photoUrl)
                                .override(200, 200)
                                .placeholder(R.drawable.ic_refresh_24px)
                                .error(R.drawable.ic_broken_image_24px)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.image);
                    })
                    .doOnError(error -> {
                        Log.e(
                                getClass().getName(),
                                "loadImage - Failed to load image: " + error.toString()
                        );
                    })
                    .subscribe(new NetworkObserver());
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(), view);
        }
    }
}


