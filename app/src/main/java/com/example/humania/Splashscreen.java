package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen); 
        
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Redirect directly to DashboardActivity if logged in
                startActivity(new Intent(Splashscreen.this, DashboardActivity.class));
            } else {
                // Otherwise go to Login (MainActivity)
                startActivity(new Intent(Splashscreen.this, MainActivity.class));
            }
            finish();
        }, 3000);
    }
}
