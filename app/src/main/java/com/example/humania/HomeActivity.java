package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                startActivity(intent);
            });
        }

        // FAB to Post Donation Screen
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, DonateActivity.class);
                startActivity(intent);
            });
        }

        // See All -> Browse all
        TextView tvSeeAll = findViewById(R.id.tvSeeAll);
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, BrowseActivity.class);
                intent.putExtra("category", "All Donations");
                startActivity(intent);
            });
        }

        // Category Chips Setup
        setupChips();
        
        // Donation Cards Setup
        setupCards();
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
