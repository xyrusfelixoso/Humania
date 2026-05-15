package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        // Get data from intent
        Donation donation = (Donation) getIntent().getSerializableExtra("donation");
        
        if (donation != null) {
            displayDonationDetails(donation);
        } else {
            // Fallback for old "title" extra
            String title = getIntent().getStringExtra("title");
            if (title != null) {
                TextView tvTitle = findViewById(R.id.tvDetailTitle);
                if (tvTitle != null) tvTitle.setText(title);
            }
        }

        // Back Button
        FrameLayout btnBack = findViewById(R.id.btnDetailBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Chat Button
        Button btnChat = findViewById(R.id.btnChat);
        if (btnChat != null) {
            btnChat.setOnClickListener(v -> {
                if (donation != null && donation.getUserId() != null) {
                    Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                    intent.putExtra("otherUserId", donation.getUserId());
                    intent.putExtra("donorName", donation.getDonorName());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Donor information not available", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Request Pickup Button
        Button btnRequest = findViewById(R.id.btnRequestPickup);
        if (btnRequest != null) {
            btnRequest.setOnClickListener(v -> 
                Toast.makeText(this, "Pickup request sent to the donor!", Toast.LENGTH_LONG).show());
        }

        // Favorite Button
        FrameLayout btnFavorite = findViewById(R.id.btnFavorite);
        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                TextView tvIcon = findViewById(R.id.tvFavoriteIcon);
                if (tvIcon != null) {
                    if (tvIcon.getText().toString().equals("🤍")) {
                        tvIcon.setText("❤️");
                        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        tvIcon.setText("🤍");
                        Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void displayDonationDetails(Donation donation) {
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        TextView tvLocation = findViewById(R.id.tvDetailLocation);
        TextView tvCategory = findViewById(R.id.tvDetailTagCategory);
        TextView tvUrgency = findViewById(R.id.tvDetailTagUrgency);
        TextView tvEmoji = findViewById(R.id.tvDetailEmoji);
        TextView tvDonor = findViewById(R.id.tvDonorName); // Assuming this ID exists based on the requirement
        ImageView ivImage = findViewById(R.id.ivDetailImage);

        if (tvTitle != null) tvTitle.setText(donation.getTitle());
        if (tvDescription != null) tvDescription.setText(donation.getDescription());
        if (tvLocation != null) tvLocation.setText("📍 " + donation.getLocation());
        if (tvCategory != null) tvCategory.setText(donation.getCategory());
        if (tvUrgency != null) tvUrgency.setText("⚡ Expires: " + donation.getExpiryDate());
        if (tvDonor != null) tvDonor.setText(donation.getDonorName() != null ? donation.getDonorName() : "Anonymous Donor");

        // Load image with Glide
        if (ivImage != null) {
            if (donation.getPhotoPath() != null && !donation.getPhotoPath().isEmpty()) {
                ivImage.setVisibility(View.VISIBLE);
                if (tvEmoji != null) tvEmoji.setVisibility(View.GONE);
                Glide.with(this)
                        .load(donation.getPhotoPath())
                        .into(ivImage);
            } else {
                ivImage.setVisibility(View.GONE);
                if (tvEmoji != null) tvEmoji.setVisibility(View.VISIBLE);
            }
        }

        // Emoji mapping
        if (tvEmoji != null && donation.getCategory() != null) {
            String cat = donation.getCategory().toLowerCase();
            if (cat.contains("food")) tvEmoji.setText("🥦");
            else if (cat.contains("clothes")) tvEmoji.setText("👕");
            else if (cat.contains("toys")) tvEmoji.setText("🧸");
            else tvEmoji.setText("🎁");
        }
    }
}
