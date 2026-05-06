package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_onboarding extends AppCompatActivity {

    private Button btnDonate, btnNeedHelp, btnOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.onboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnDonate = findViewById(R.id.button3);
        btnNeedHelp = findViewById(R.id.button5);
        btnOrganization = findViewById(R.id.button4);

        // I WANT TO DONATE -> Go to DonateActivity
        btnDonate.setOnClickListener(v -> {
            Intent intent = new Intent(activity_onboarding.this, DonateActivity.class);
            startActivity(intent);
        });

        // I NEED HELP! -> Go to HomeActivity (to see available donations)
        btnNeedHelp.setOnClickListener(v -> {
            Intent intent = new Intent(activity_onboarding.this, HomeActivity.class);
            startActivity(intent);
        });

        // IM AN ORGANIZATION -> Go to HomeActivity (as requested)
        btnOrganization.setOnClickListener(v -> {
            Intent intent = new Intent(activity_onboarding.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}