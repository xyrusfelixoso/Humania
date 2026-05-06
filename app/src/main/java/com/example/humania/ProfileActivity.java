package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStats();
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
            findViewById(R.id.menuMessages).setOnClickListener(v -> Toast.makeText(this, "Opening Messages", Toast.LENGTH_SHORT).show());
        }
        if (findViewById(R.id.menuReviews) != null) {
            findViewById(R.id.menuReviews).setOnClickListener(v -> Toast.makeText(this, "Opening Reviews", Toast.LENGTH_SHORT).show());
        }
        if (findViewById(R.id.menuSettings) != null) {
            findViewById(R.id.menuSettings).setOnClickListener(v -> Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show());
        }
    }
}
