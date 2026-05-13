package com.example.humania;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private List<Donation> donationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Donation donation);
    }

    public DonationAdapter(List<Donation> donationList, OnItemClickListener listener) {
        this.donationList = donationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation_card, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donationList.get(position);
        holder.bind(donation, listener);
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMeta, tvTagCategory, tvTagUrgency, tvDistance, tvEmoji;
        ImageView ivImage;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvDonationTitle);
            tvMeta = itemView.findViewById(R.id.tvDonationMeta);
            tvTagCategory = itemView.findViewById(R.id.tvTagCategory);
            tvTagUrgency = itemView.findViewById(R.id.tvTagUrgency);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvEmoji = itemView.findViewById(R.id.tvDonationEmoji);
            ivImage = itemView.findViewById(R.id.ivDonationImage);
        }

        public void bind(Donation donation, OnItemClickListener listener) {
            tvTitle.setText(donation.getTitle());
            tvMeta.setText("Posted: " + (donation.getTimestamp() != null ? donation.getTimestamp() : "Just now"));
            tvTagCategory.setText(donation.getCategory());
            tvTagUrgency.setText("Expires: " + donation.getExpiryDate());
            tvDistance.setText(donation.getLocation());

            // Simple emoji mapping based on category
            if (donation.getCategory() != null) {
                if (donation.getCategory().contains("Food")) tvEmoji.setText("🥦");
                else if (donation.getCategory().contains("Clothes")) tvEmoji.setText("👕");
                else if (donation.getCategory().contains("Toys")) tvEmoji.setText("🧸");
                else tvEmoji.setText("🎁");
            }

            itemView.setOnClickListener(v -> listener.onItemClick(donation));
        }
    }
}
