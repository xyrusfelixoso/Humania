package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private ImageView ivTogglePassword;
    private boolean passwordVisible = false;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Realtime Database with the specific regional URL
        database = FirebaseDatabase.getInstance("https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mDatabase = database.getReference();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);

        // Password visibility toggle
        ivTogglePassword.setOnClickListener(v -> {
            if (passwordVisible) {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordVisible = false;
            } else {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                passwordVisible = true;
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        // Login button click
        btnLogin.setOnClickListener(view -> checkLogin());

        // OTHER BUTTONS FUNCTIONALITY
        findViewById(R.id.tvForgotPassword).setOnClickListener(v -> 
            Toast.makeText(this, "Password reset functionality not implemented yet", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnGoogle).setOnClickListener(v -> 
            Toast.makeText(this, "Google Sign-In not implemented yet", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnFacebook).setOnClickListener(v -> 
            Toast.makeText(this, "Facebook Sign-In not implemented yet", Toast.LENGTH_SHORT).show());

        findViewById(R.id.tvSignUp).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void checkLogin() {
        String inputEmail = etEmail.getText().toString().trim();
        String inputPassword = etPassword.getText().toString().trim();

        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Intent intent = new Intent(MainActivity.this, activity_getstarted.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication failed: " + 
                                (task.getException() != null ? task.getException().getMessage() : "Check credentials"),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
