package com.jkane.a04042020_joshkane_nycschools.ui.schoollist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolAdapterViewBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;

import java.util.List;

public class SchoolListRecyclerAdapter extends RecyclerView.Adapter<SchoolListRecyclerAdapter.SchoolViewHolder> {

    List<NYCSchool> list;
    private static SchoolListClickListener listener;

    public StringUtils stringUtils;

    public SchoolListRecyclerAdapter(List<NYCSchool> list, StringUtils stringUtils) {
        this.list = list;
        this.stringUtils = stringUtils;
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

    public void setData(List<NYCSchool> list) {
        this.list = list;
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
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(), view);
        }
    }
}


