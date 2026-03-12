package com.example.humania;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
=======
import android.os.Bundle;
>>>>>>> 329a5679b4cde414db467d46aa983b1f078148d7

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
<<<<<<< HEAD
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;



=======

public class MainActivity extends AppCompatActivity {

>>>>>>> 329a5679b4cde414db467d46aa983b1f078148d7
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.accsplash), (v, insets) -> {
=======
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
>>>>>>> 329a5679b4cde414db467d46aa983b1f078148d7
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
<<<<<<< HEAD

       loginbutton = (Button) findViewById(R.id.btnGetStarted);
       loginbutton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
                openNextPage();
                String email = "pasikat";


           }
           public void openNextPage(){
               Intent intent = new Intent( MainActivity.this, activity_onboarding.class);
               startActivity(intent);
           }
       });



=======
>>>>>>> 329a5679b4cde414db467d46aa983b1f078148d7
    }
}