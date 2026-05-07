package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Menu Item Listeners
        setupMenu();
        
        // Update stats
        updateStats();

        // Bottom Navigation Setup
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStats();
        
        // Ensure the correct item is selected in bottom nav
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_profile);
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_profile);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_browse) {
                    Intent intent = new Intent(this, BrowseActivity.class);
                    intent.putExtra("category", "All");
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.nav_messages) {
                    startActivity(new Intent(this, MessageListActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_profile) {
                    return true;
                }
                return false;
            });
        }
    }

    private void updateStats() {
        int count = DonationManager.getDonationCount();
        
        // Update the "Donated" stat at the top
        TextView tvStatDonated = findViewById(R.id.tvStatDonated);
        if (tvStatDonated != null) {
            tvStatDonated.setText(String.valueOf(count));
        }
        
        // Update menu subtitle
        View menuDonations = findViewById(R.id.menuDonations);
        if (menuDonations != null) {
            TextView tvSubtitle = menuDonations.findViewById(R.id.tvMenuSubtitle);
            if (tvSubtitle != null) {
                tvSubtitle.setText(count + " items posted");
            }
        }
    }

    private void setupMenu() {
        if (findViewById(R.id.menuDonations) != null) {
            findViewById(R.id.menuDonations).setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, MyDonationsActivity.class);
                startActivity(intent);
            });
        }
        if (findViewById(R.id.menuRequests) != null) {
            findViewById(R.id.menuRequests).setOnClickListener(v -> Toast.makeText(this, "Opening My Requests", Toast.LENGTH_SHORT).show());
        }
        if (findViewById(R.id.menuMessages) != null) {
            findViewById(R.id.menuMessages).setOnClickListener(v -> {
                startActivity(new Intent(this, MessageListActivity.class));
                finish();
            });
        }
        if (findViewById(R.id.menuReviews) != null) {
            findViewById(R.id.menuReviews).setOnClickListener(v -> Toast.makeText(this, "Opening Reviews", Toast.LENGTH_SHORT).show());
        }
        if (findViewById(R.id.menuSettings) != null) {
            findViewById(R.id.menuSettings).setOnClickListener(v -> Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show());
        }
    }
}
