package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        String category = getIntent().getStringExtra("category");
        if (category != null) {
            TextView tvTitle = findViewById(R.id.tvBrowseTitle);
            if (tvTitle != null) tvTitle.setText("Browse: " + category);
        }

        findViewById(R.id.btnBrowseBack).setOnClickListener(v -> finish());
        
        // In a real app, you'd filter your list here based on the category.
    }
}
