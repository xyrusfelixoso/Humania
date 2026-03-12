package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstantceState) {
        super.onCreate(savedInstantceState);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          startActivity(new Intent(Splashscreen.this, MainActivity.class));
                                          finish();
                                      }

                                  }, 3000
        );
    }
}
