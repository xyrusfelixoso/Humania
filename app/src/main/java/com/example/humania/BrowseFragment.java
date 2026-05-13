package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BrowseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        // Find the cards in the inflated layout
        View card1 = view.findViewById(R.id.browseCard1);
        View card2 = view.findViewById(R.id.browseCard2);
        View card3 = view.findViewById(R.id.browseCard3);
        View card4 = view.findViewById(R.id.browseCard4);

        if (card1 != null) card1.setOnClickListener(v -> openDetail("Fresh Vegetables Bundle"));
        if (card2 != null) card2.setOnClickListener(v -> openDetail("Warm Clothes for Kids"));
        if (card3 != null) card3.setOnClickListener(v -> openDetail("Educational Toys Set"));
        if (card4 != null) card4.setOnClickListener(v -> openDetail("Canned Goods Assortment"));

        return view;
    }

    private void openDetail(String title) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
