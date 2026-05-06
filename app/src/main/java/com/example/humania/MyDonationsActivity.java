package com.example.humania;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyDonationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        RecyclerView rvMyDonations = findViewById(R.id.rvMyDonations);
        TextView tvEmptyState = findViewById(R.id.tvEmptyState);

        List<Donation> donations = DonationManager.getMyDonations();

        if (donations.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvMyDonations.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvMyDonations.setVisibility(View.VISIBLE);
            
            rvMyDonations.setLayoutManager(new LinearLayoutManager(this));
            MyDonationsAdapter adapter = new MyDonationsAdapter(donations);
            rvMyDonations.setAdapter(adapter);
        }
    }
}
