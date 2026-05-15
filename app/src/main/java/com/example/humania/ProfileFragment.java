package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ProfileFragment extends Fragment {

    private TextView tvProfileName, tvProfileHandle, tvStatDonated;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private View sectionMyDonations;
    private RecyclerView rvMyDonations;
    private MyDonationsAdapter adapter;
    private List<Donation> donationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();

        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileHandle = view.findViewById(R.id.tvProfileHandle);
        tvStatDonated = view.findViewById(R.id.tvStatDonated);
        sectionMyDonations = view.findViewById(R.id.sectionMyDonations);
        rvMyDonations = view.findViewById(R.id.rvMyDonationsProfile);

        // Sidebar/Drawer menu button
        View btnMenu = view.findViewById(R.id.btnOpenDrawer);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> {
                if (getActivity() instanceof DashboardActivity) {
                    ((DashboardActivity) getActivity()).openDrawer();
                }
            });
        }

        // See All Donations link
        View tvSeeAll = view.findViewById(R.id.tvSeeAllDonations);
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), MyDonationsActivity.class));
            });
        }

        // Initialize Menu Items with CORRECT labels to fix the "all My Donations" bug
        setupMenuItem(view.findViewById(R.id.menuRequests), "My Requests", "Manage your requests", "🤝");
        setupMenuItem(view.findViewById(R.id.menuMessages), "My Messages", "View conversations", "💬");
        setupMenuItem(view.findViewById(R.id.menuReviews), "Reviews", "Feedback from others", "⭐");
        setupMenuItem(view.findViewById(R.id.menuSettings), "Settings", "Account and security", "⚙️");

        setupRecyclerView();
        loadUserData();
        loadMyDonations();

        return view;
    }

    private void setupRecyclerView() {
        donationList = new ArrayList<>();
        adapter = new MyDonationsAdapter(donationList);
        if (rvMyDonations != null) {
            rvMyDonations.setLayoutManager(new LinearLayoutManager(getContext()));
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

                // Bug Fix: Only show the "My Donations" section if the user has actually donated items ("mao ray mo gawas")
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
                Toast.makeText(getContext(), title + " clicked", Toast.LENGTH_SHORT).show());
        }
    }
}
