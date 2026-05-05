package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_onboarding extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnDonate, btnNeedHelp, btnOrg;

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

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        btnDonate = findViewById(R.id.button3);
        btnNeedHelp = findViewById(R.id.button5);
        btnOrg = findViewById(R.id.button4);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Navigation to Dashboard
        View.OnClickListener navigateToDashboard = v -> {
            Intent intent = new Intent(activity_onboarding.this, activity_getstarted.class);
            // Optionally pass user role
            if (v.getId() == R.id.button3) intent.putExtra("ROLE", "DONOR");
            else if (v.getId() == R.id.button5) intent.putExtra("ROLE", "NEED_HELP");
            else if (v.getId() == R.id.button4) intent.putExtra("ROLE", "ORGANIZATION");
            
            startActivity(intent);
        };

        btnDonate.setOnClickListener(navigateToDashboard);
        btnNeedHelp.setOnClickListener(navigateToDashboard);
        btnOrg.setOnClickListener(navigateToDashboard);
    }
}
