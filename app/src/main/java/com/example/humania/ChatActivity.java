package com.example.humania;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private LinearLayout llMessagesContainer;
    private EditText etChatMessage;
    private ImageButton btnSendMessage;
    private TextView tvChatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        llMessagesContainer = findViewById(R.id.llMessagesContainer);
        etChatMessage = findViewById(R.id.etChatMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        tvChatName = findViewById(R.id.tvChatName);

        String donorName = getIntent().getStringExtra("donorName");
        if (donorName != null) {
            tvChatName.setText(donorName);
        }

        findViewById(R.id.btnChatBack).setOnClickListener(v -> finish());

        btnSendMessage.setOnClickListener(v -> {
            String message = etChatMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                addMessage(message, true);
                etChatMessage.setText("");
                
                // Mock auto-reply
                btnSendMessage.postDelayed(() -> addMessage("Got it! I'll check my schedule.", false), 1000);
            }
        });
    }

    private void addMessage(String text, boolean isOutgoing) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(32, 24, 32, 24);
        textView.setTextColor(isOutgoing ? getResources().getColor(R.color.white) : getResources().getColor(R.color.text_primary));
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16);
        params.gravity = isOutgoing ? android.view.Gravity.END : android.view.Gravity.START;
        textView.setLayoutParams(params);
        
        textView.setBackgroundResource(isOutgoing ? R.drawable.bg_chip_active : R.drawable.bg_chip);
        
        llMessagesContainer.addView(textView);
        
        // Scroll to bottom (simple way)
        llMessagesContainer.post(() -> {
            View parent = (View) llMessagesContainer.getParent();
            if (parent instanceof android.widget.ScrollView) {
                ((android.widget.ScrollView) parent).fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
