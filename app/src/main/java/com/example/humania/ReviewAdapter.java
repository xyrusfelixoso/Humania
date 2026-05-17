package com.example.humania;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.tvName.setText(review.getReviewerName());
        holder.tvTimestamp.setText(review.getTimestamp());
        holder.tvComment.setText(review.getComment());
        holder.ratingBar.setRating(review.getRating());
        
        if (review.getDonationTitle() != null && !review.getDonationTitle().isEmpty()) {
            holder.tvDonationItem.setVisibility(View.VISIBLE);
            holder.tvDonationItem.setText("Donated: " + review.getDonationTitle());
        } else {
            holder.tvDonationItem.setVisibility(View.GONE);
        }

        if (review.getReviewerProfileImage() != null && !review.getReviewerProfileImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(review.getReviewerProfileImage())
                    .placeholder(R.drawable.bg_avatar_green)
                    .into(holder.ivProfile);
        } else {
            holder.ivProfile.setImageResource(R.drawable.bg_avatar_green);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivProfile;
        TextView tvName, tvTimestamp, tvComment, tvDonationItem;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivReviewerProfile);
            tvName = itemView.findViewById(R.id.tvReviewerName);
            tvTimestamp = itemView.findViewById(R.id.tvReviewTimestamp);
            tvComment = itemView.findViewById(R.id.tvReviewComment);
            tvDonationItem = itemView.findViewById(R.id.tvReviewDonationItem);
            ratingBar = itemView.findViewById(R.id.reviewRatingBar);
        }
    }
}
