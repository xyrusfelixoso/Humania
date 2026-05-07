package com.example.humania;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ChatActivity extends AppCompatActivity {

    private LinearLayout llMessagesContainer;
    private EditText etChatMessage;
    private ImageButton btnSendMessage;
    private TextView tvChatName;
    private boolean isAdminMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        llMessagesContainer = findViewById(R.id.llMessagesContainer);
        etChatMessage = findViewById(R.id.etChatMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        tvChatName = findViewById(R.id.tvChatName);

        // Check if current user is admin
        isAdminMode = UserManager.isAdmin();

        String donorName = getIntent().getStringExtra("donorName");
        if (donorName != null) {
            tvChatName.setText(isAdminMode ? "User: " + donorName : donorName);
        }

        findViewById(R.id.btnChatBack).setOnClickListener(v -> finish());

        btnSendMessage.setOnClickListener(v -> {
            String message = etChatMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        });
        
        // Initial Admin Greeting if not admin
        if (!isAdminMode) {
            llMessagesContainer.postDelayed(() -> 
                addMessage("Hello! I am the Humania Admin. How can I help you today?", false), 500);
        }
    }

    private void sendMessage(String message) {
        // Add the user's/admin's message to the right
        addMessage(message, true);
        etChatMessage.setText("");

        // If a regular user sends a message, simulate an Admin reply in English
        if (!isAdminMode) {
            btnSendMessage.postDelayed(() -> {
                String adminReply = "Thank you for your message. We will process your request shortly.";
                addMessage(adminReply, false);
            }, 1500);
        } else {
            // If Admin is sending, maybe show a "Sent to User" toast
            Toast.makeText(this, "Reply sent to user", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMessage(String text, boolean isOutgoing) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(32, 24, 32, 24);
        
        // Fix: Use ContextCompat to get colors and fix the incorrect R.id.white reference
        int colorRes = isOutgoing ? R.color.white : android.R.color.black;
        textView.setTextColor(ContextCompat.getColor(this, colorRes));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16);
        params.gravity = isOutgoing ? android.view.Gravity.END : android.view.Gravity.START;
        textView.setLayoutParams(params);
        
        textView.setBackgroundResource(isOutgoing ? R.drawable.bg_chip_active : R.drawable.bg_chip);
        
        llMessagesContainer.addView(textView);
        
        llMessagesContainer.post(() -> {
            View parent = (View) llMessagesContainer.getParent();
            if (parent instanceof android.widget.ScrollView) {
                ((android.widget.ScrollView) parent).fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
