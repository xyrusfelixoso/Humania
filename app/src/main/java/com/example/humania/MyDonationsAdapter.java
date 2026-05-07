package com.example.humania;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.ViewHolder> {

    private List<Donation> donations;

    public MyDonationsAdapter(List<Donation> donations) {
        this.donations = donations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donation donation = donations.get(position);
        holder.tvTitle.setText(donation.getTitle());
        holder.tvMeta.setText("Posted on " + donation.getTimestamp());
        holder.tvCategory.setText(getEmojiForCategory(donation.getCategory()) + " " + donation.getCategory());
        
        // Handle Photo display
        if (donation.getPhotoPath() != null && !donation.getPhotoPath().isEmpty()) {
            File imgFile = new File(donation.getPhotoPath());
            if (imgFile.exists()) {
                holder.ivDonationImage.setVisibility(View.VISIBLE);
                holder.tvEmoji.setVisibility(View.GONE);
                holder.ivDonationImage.setImageURI(Uri.fromFile(imgFile));
            } else {
                holder.ivDonationImage.setVisibility(View.GONE);
                holder.tvEmoji.setVisibility(View.VISIBLE);
                holder.tvEmoji.setText(getEmojiForCategory(donation.getCategory()));
            }
        } else {
            holder.ivDonationImage.setVisibility(View.GONE);
            holder.tvEmoji.setVisibility(View.VISIBLE);
            holder.tvEmoji.setText(getEmojiForCategory(donation.getCategory()));
        }
        
        // Display details
        holder.tvUrgency.setText(donation.getQuantity() + " items");
        holder.tvDistance.setText(donation.getLocation());
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    private String getEmojiForCategory(String category) {
        switch (category) {
            case "Food": return "🥦";
            case "Clothes": return "👕";
            case "Items": return "📦";
            case "Toys": return "🧸";
            default: return "📦";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMeta, tvCategory, tvUrgency, tvDistance, tvEmoji;
        ImageView ivDonationImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvDonationTitle);
            tvMeta = itemView.findViewById(R.id.tvDonationMeta);
            tvCategory = itemView.findViewById(R.id.tvTagCategory);
            tvUrgency = itemView.findViewById(R.id.tvTagUrgency);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvEmoji = itemView.findViewById(R.id.tvDonationEmoji);
            ivDonationImage = itemView.findViewById(R.id.ivDonationImage);
        }
    }
}
