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
        // I-set ang splash layout kung naa
        setContentView(R.layout.splashscreen); 
        
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Kung naka-login na, diretso sa Get Started o Home
                startActivity(new Intent(Splashscreen.this, activity_getstarted.class));
            } else {
                // Kung wala pa, adto sa Login (MainActivity)
                startActivity(new Intent(Splashscreen.this, MainActivity.class));
            }
            finish();
        }, 3000);
    }
}
