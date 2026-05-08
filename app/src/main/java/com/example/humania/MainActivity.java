package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etEmail, etPassword;
    private ImageView ivTogglePassword;
    private boolean passwordVisible = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 1. Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        // Open Sign Up Screen
        findViewById(R.id.tvSignUp).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void checkLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Palihog butangi og email ug password", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Firebase Login Logic
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, activity_getstarted.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed: " + 
                                (task.getException() != null ? task.getException().getMessage() : "Check credentials"), 
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
