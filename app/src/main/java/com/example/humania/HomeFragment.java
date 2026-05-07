package com.example.humania;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Category Chips
        setupChip(view.findViewById(R.id.chipAll), "All");
        setupChip(view.findViewById(R.id.chipFood), "Food");
        setupChip(view.findViewById(R.id.chipClothes), "Clothes");
        setupChip(view.findViewById(R.id.chipItems), "Items");
        setupChip(view.findViewById(R.id.chipToys), "Toys");

        // See All
        view.findViewById(R.id.tvSeeAll).setOnClickListener(v -> 
            Toast.makeText(getContext(), "See All clicked", Toast.LENGTH_SHORT).show());

        // Cards
        view.findViewById(R.id.card1).setOnClickListener(v -> openDetail());
        view.findViewById(R.id.card2).setOnClickListener(v -> openDetail());
        view.findViewById(R.id.card3).setOnClickListener(v -> openDetail());

        return view;
    }

    private void setupChip(View chip, String name) {
        if (chip != null) {
            chip.setOnClickListener(v -> 
                Toast.makeText(getContext(), name + " category selected", Toast.LENGTH_SHORT).show());
        }
    }

    private void openDetail() {
        // Logic to open detail fragment or activity
        Toast.makeText(getContext(), "Donation Detail clicked", Toast.LENGTH_SHORT).show();
    }
}
