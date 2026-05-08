package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // Avatar (Yellow Emoji) -> Profile Screen
        View ivProfileAvatar = findViewById(R.id.ivProfileAvatar);
        if (ivProfileAvatar != null) {
            ivProfileAvatar.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            });
        }

        // See All -> Browse all
        TextView tvSeeAll = findViewById(R.id.tvSeeAll);
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, BrowseActivity.class);
                intent.putExtra("category", "All Donations");
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            });
        }

        // Bottom Navigation Setup
        setupBottomNavigation();

        // Category Chips Setup
        setupChips();
        
        // Donation Cards Setup
        setupCards();
        
        // Update stats
        updateStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStats();
        
        // Sync bottom nav selection
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    private void updateStats() {
        int count = DonationManager.getDonationCount();
        TextView tvStatDonated = findViewById(R.id.tvHomeStatDonated);
        if (tvStatDonated != null) {
            tvStatDonated.setText(String.valueOf(count));
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                Intent intent = null;
                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_browse) {
                    intent = new Intent(this, BrowseActivity.class);
                    intent.putExtra("category", "All");
                } else if (id == R.id.nav_messages) {
                    intent = new Intent(this, MessageListActivity.class);
                } else if (id == R.id.nav_profile) {
                    intent = new Intent(this, ProfileActivity.class);
                }
                
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
    }

    private void setupChips() {
        findViewById(R.id.chipAll).setOnClickListener(v -> openBrowse("All"));
        findViewById(R.id.chipFood).setOnClickListener(v -> openBrowse("Food"));
        findViewById(R.id.chipClothes).setOnClickListener(v -> openBrowse("Clothes"));
        findViewById(R.id.chipItems).setOnClickListener(v -> openBrowse("Items"));
        findViewById(R.id.chipToys).setOnClickListener(v -> openBrowse("Toys"));
    }

    private void openBrowse(String category) {
        Intent intent = new Intent(HomeActivity.this, BrowseActivity.class);
        intent.putExtra("category", category);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void setupCards() {
        if (findViewById(R.id.card1) != null) {
            findViewById(R.id.card1).setOnClickListener(v -> openDetail("Fresh Vegetables Bundle"));
        }
        if (findViewById(R.id.card2) != null) {
            findViewById(R.id.card2).setOnClickListener(v -> openDetail("Warm Clothes for Kids"));
        }
        if (findViewById(R.id.card3) != null) {
            findViewById(R.id.card3).setOnClickListener(v -> openDetail("Educational Toys Set"));
        }
    }

    private void openDetail(String title) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
