package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class btn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.btnGetStarted);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(btn.this, activity_onboarding.class);
            startActivity(intent);
        });
    }
}