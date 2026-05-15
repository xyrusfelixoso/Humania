package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileHandle, tvStatDonated;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private View sectionMyDonations;
    private RecyclerView rvMyDonations;
    private MyDonationsAdapter adapter;
    private List<Donation> donationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        mAuth = FirebaseAuth.getInstance();
        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();

        initViews();
        setupRecyclerView();
        setupBottomNavigation();
        loadUserData();
        loadMyDonations();
    }

    private void initViews() {
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileHandle = findViewById(R.id.tvProfileHandle);
        tvStatDonated = findViewById(R.id.tvStatDonated);
        sectionMyDonations = findViewById(R.id.sectionMyDonations);
        rvMyDonations = findViewById(R.id.rvMyDonationsProfile);

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        View tvSeeAll = findViewById(R.id.tvSeeAllDonations);
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> {
                startActivity(new Intent(this, MyDonationsActivity.class));
            });
        }

        // Bug Fix: Assign correct labels to menu rows to prevent them all showing "My Donations"
        setupMenuItem(findViewById(R.id.menuRequests), "My Requests", "Manage your requests", "🤝");
        setupMenuItem(findViewById(R.id.menuMessages), "My Messages", "View conversations", "💬");
        setupMenuItem(findViewById(R.id.menuReviews), "Reviews", "Feedback from others", "⭐");
        setupMenuItem(findViewById(R.id.menuSettings), "Settings", "Account and security", "⚙️");
    }

    private void setupRecyclerView() {
        donationList = new ArrayList<>();
        adapter = new MyDonationsAdapter(donationList);
        if (rvMyDonations != null) {
            rvMyDonations.setLayoutManager(new LinearLayoutManager(this));
            rvMyDonations.setAdapter(adapter);
            rvMyDonations.setNestedScrollingEnabled(false);
        }
    }

    private void loadUserData() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        if (tvProfileName != null) tvProfileName.setText(user.fullName);
                        if (tvProfileHandle != null) tvProfileHandle.setText("@" + user.fullName.toLowerCase().replace(" ", "") + " · ✅ Verified");
                        if (tvStatDonated != null) tvStatDonated.setText(String.valueOf(user.totalDonations));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    private void loadMyDonations() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) return;

        String userId = firebaseUser.getUid();
        Query myDonationsQuery = mDatabase.child("donations").orderByChild("userId").equalTo(userId);

        myDonationsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Donation donation = postSnapshot.getValue(Donation.class);
                    if (donation != null) {
                        donationList.add(0, donation); // Newest first
                    }
                }

                // Only show the donation section if the user has donated items
                if (donationList.isEmpty()) {
                    if (sectionMyDonations != null) sectionMyDonations.setVisibility(View.GONE);
                } else {
                    if (sectionMyDonations != null) sectionMyDonations.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setupMenuItem(View menu, String title, String subtitle, String icon) {
        if (menu != null) {
            TextView tvTitle = menu.findViewById(R.id.tvMenuTitle);
            TextView tvSubtitle = menu.findViewById(R.id.tvMenuSubtitle);
            TextView tvIcon = menu.findViewById(R.id.tvMenuIcon);

            if (tvTitle != null) tvTitle.setText(title);
            if (tvSubtitle != null) tvSubtitle.setText(subtitle);
            if (tvIcon != null) tvIcon.setText(icon);

            menu.setOnClickListener(v -> 
                Toast.makeText(this, title + " clicked", Toast.LENGTH_SHORT).show());
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
                }
                return false;
            });
        }
    }
}
