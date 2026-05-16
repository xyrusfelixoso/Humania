package com.example.humania;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MyRequestsActivity extends AppCompatActivity {

    private RecyclerView rvMyRequests;
    private PickupRequestAdapter adapter;
    private List<PickupRequest> requestList;
    private DatabaseReference mDatabase;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();
        currentUserId = FirebaseAuth.getInstance().getUid();

        rvMyRequests = findViewById(R.id.rvMyRequests);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        setupRecyclerView();
        loadMyRequests();
    }

    private void setupRecyclerView() {
        requestList = new ArrayList<>();
        // In "My Requests", we are the requester, so isDonorView is false
        adapter = new PickupRequestAdapter(requestList, false, null);
        rvMyRequests.setLayoutManager(new LinearLayoutManager(this));
        rvMyRequests.setAdapter(adapter);
    }

    private void loadMyRequests() {
        if (currentUserId == null) return;

        mDatabase.child("pickupRequests").orderByChild("requesterId").equalTo(currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        requestList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            PickupRequest request = data.getValue(PickupRequest.class);
                            if (request != null) {
                                requestList.add(request);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MyRequestsActivity.this, "Error loading requests", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
