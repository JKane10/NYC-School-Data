package com.jkane.a04042020_joshkane_nycschools.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.jkane.a04042020_joshkane_nycschools.R;
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolAdapterViewBinding;
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool;

import org.w3c.dom.Text;

import java.util.List;

public class SchoolListRecyclerAdapter extends RecyclerView.Adapter<SchoolListRecyclerAdapter.SchoolViewHolder> {

    List<NYCSchool> list;
    private static SchoolListClickListener listener;

    public SchoolListRecyclerAdapter(List<NYCSchool> list) {
        this.list = list;
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

    static class SchoolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SchoolAdapterViewBinding binding;
        String dbn;

        SchoolViewHolder(@NonNull SchoolAdapterViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        void bind(NYCSchool school) {
            dbn = school.getId();
            binding.name.setText(school.getName());
            binding.description.setText(String.format(getContextString(R.string.description_format), school.getOverview()));
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(), view);
        }

        /**
         * Helper method to get string values from context.
         *
         * @param resId - Id of the string resource.
         * @return - String that the resource maps to.
         */
        private String getContextString(@StringRes int resId) {
            return binding.getRoot().getContext().getString(resId);
        }
    }
}


