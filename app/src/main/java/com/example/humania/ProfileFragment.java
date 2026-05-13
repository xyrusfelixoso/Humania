package com.example.humania;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView btnMenu = view.findViewById(R.id.btnOpenDrawer);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> {
                if (getActivity() instanceof DashboardActivity) {
                    ((DashboardActivity) getActivity()).openDrawer();
                }
            });
        }

        setupMenu(view.findViewById(R.id.menuDonations), "My Donations");
        setupMenu(view.findViewById(R.id.menuRequests), "My Requests");
        setupMenu(view.findViewById(R.id.menuMessages), "My Messages");
        setupMenu(view.findViewById(R.id.menuReviews), "Reviews");
        setupMenu(view.findViewById(R.id.menuSettings), "Settings");

        return view;
    }

    private void setupMenu(View menu, String name) {
        if (menu != null) {
            menu.setOnClickListener(v -> 
                Toast.makeText(getContext(), name + " clicked", Toast.LENGTH_SHORT).show());
        }
    }
}
