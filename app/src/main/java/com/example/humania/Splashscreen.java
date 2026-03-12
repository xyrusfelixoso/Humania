package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usually, Splashscreen has a layout or theme, 
        // but if it's just a delay, we can just post the intent.
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Changing this to go to btn (Get Started) or MainActivity (Login)
                // Based on common flows. Let's assume btn is the intended first screen after splash.
                startActivity(new Intent(Splashscreen.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
