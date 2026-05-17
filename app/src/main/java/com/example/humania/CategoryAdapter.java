package com.example.humania;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private String selectedCategory = "All";
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categoryList, String selectedCategory, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.selectedCategory = selectedCategory;
        this.listener = listener;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_chip, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category, selectedCategory.equalsIgnoreCase(category.getName()), listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = (TextView) itemView;
        }

        public void bind(Category category, boolean isSelected, OnCategoryClickListener listener) {
            tvCategory.setText(category.getDisplayName());
            
            if (isSelected) {
                tvCategory.setBackgroundResource(R.drawable.bg_chip_active);
                tvCategory.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            } else {
                tvCategory.setBackgroundResource(R.drawable.bg_chip);
                tvCategory.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
            }

            itemView.setOnClickListener(v -> listener.onCategoryClick(category));
        }
    }
}
