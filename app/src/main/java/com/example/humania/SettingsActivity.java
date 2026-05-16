package com.example.humania;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private EditText etUpdateName, etUpdatePassword;
    private Button btnSaveSettings;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();

        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdatePassword = findViewById(R.id.etUpdatePassword);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        loadCurrentUserData();

        btnSaveSettings.setOnClickListener(v -> saveSettings());
    }

    private void loadCurrentUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase.child("users").child(user.getUid()).child("fullName").get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().getValue() != null) {
                    etUpdateName.setText(task.getResult().getValue().toString());
                }
            });
        }
    }

    private void saveSettings() {
        String newName = etUpdateName.getText().toString().trim();
        String newPassword = etUpdatePassword.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) return;

        if (!newName.isEmpty()) {
            mDatabase.child("users").child(user.getUid()).child("fullName").setValue(newName);
        }

        if (!newPassword.isEmpty()) {
            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                user.updatePassword(newPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error updating password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}
