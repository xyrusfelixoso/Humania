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

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private ImageView ivTogglePassword;
    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

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
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnGoogle).setOnClickListener(v -> 
            Toast.makeText(this, "Signing in with Google...", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnFacebook).setOnClickListener(v -> 
            Toast.makeText(this, "Signing in with Facebook...", Toast.LENGTH_SHORT).show());

        // Open Sign Up Screen
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

        // Use UserManager to validate login
        if (UserManager.validateUser(inputEmail, inputPassword)) {
            Intent intent = new Intent(MainActivity.this, activity_getstarted.class);
            startActivity(intent);
            finish(); // Prevent going back to login
        } else {
            etEmail.setText("");
            etPassword.setText("");
            etEmail.setHint("Wrong email!");
            etPassword.setHint("Wrong password!");
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
