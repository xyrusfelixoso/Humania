package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_onboarding extends AppCompatActivity {

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

        // Initialize buttons
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnDonate = findViewById(R.id.button3);
        Button btnNeedHelp = findViewById(R.id.button5);
        Button btnOrg = findViewById(R.id.button4);

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Redirect all choices to DashboardActivity
        if (btnDonate != null) {
            btnDonate.setOnClickListener(v -> {
                Intent intent = new Intent(activity_onboarding.this, DashboardActivity.class);
                intent.putExtra("ROLE", "donor");
                startActivity(intent);
                finish();
            });
        }

        if (btnNeedHelp != null) {
            btnNeedHelp.setOnClickListener(v -> {
                Intent intent = new Intent(activity_onboarding.this, DashboardActivity.class);
                intent.putExtra("ROLE", "recipient");
                startActivity(intent);
                finish();
            });
        }

        if (btnOrg != null) {
            btnOrg.setOnClickListener(v -> {
                Intent intent = new Intent(activity_onboarding.this, DashboardActivity.class);
                intent.putExtra("ROLE", "organization");
                startActivity(intent);
                finish();
            });
        }
    }
}
