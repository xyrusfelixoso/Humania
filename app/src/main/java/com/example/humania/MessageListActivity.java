package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        findViewById(R.id.btnMsgBack).setOnClickListener(v -> finish());

        findViewById(R.id.chatItem1).setOnClickListener(v -> {
            Intent intent = new Intent(MessageListActivity.this, ChatActivity.class);
            intent.putExtra("donorName", "Ana Lim");
            startActivity(intent);
        });

        findViewById(R.id.chatItem2).setOnClickListener(v -> {
            Intent intent = new Intent(MessageListActivity.this, ChatActivity.class);
            intent.putExtra("donorName", "Mang Juan");
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_messages);
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
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }
}
