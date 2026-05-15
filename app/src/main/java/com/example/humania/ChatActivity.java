package com.example.humania;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private LinearLayout llMessagesContainer;
    private EditText etChatMessage;
    private ImageButton btnSendMessage;
    private TextView tvChatName, tvChatAvatarEmoji;
    private ScrollView scrollView;
    
    private DatabaseReference mDatabase;
    private String currentUserId;
    private String otherUserId;
    private String chatId;
    private String otherUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        llMessagesContainer = findViewById(R.id.llMessagesContainer);
        etChatMessage = findViewById(R.id.etChatMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        tvChatName = findViewById(R.id.tvChatName);
        tvChatAvatarEmoji = findViewById(R.id.tvChatAvatarEmoji);
        scrollView = findViewById(R.id.svChat);

        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();
        currentUserId = FirebaseAuth.getInstance().getUid();
        
        otherUserId = getIntent().getStringExtra("otherUserId");
        otherUserName = getIntent().getStringExtra("donorName");

        if (otherUserName != null) {
            tvChatName.setText(otherUserName);
            tvChatAvatarEmoji.setText(getEmojiForName(otherUserName));
        }

        if (currentUserId == null || otherUserId == null) {
            Toast.makeText(this, "Error: User not identified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create a unique chatId for the two users
        if (currentUserId.compareTo(otherUserId) < 0) {
            chatId = currentUserId + "_" + otherUserId;
        } else {
            chatId = otherUserId + "_" + currentUserId;
        }

        findViewById(R.id.btnChatBack).setOnClickListener(v -> finish());

        btnSendMessage.setOnClickListener(v -> {
            String message = etChatMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        });

        loadMessages();
    }

    private void loadMessages() {
        mDatabase.child("chats").child(chatId).child("messages")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                llMessagesContainer.removeAllViews();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        addMessageToUI(chatMessage.getMessage(), chatMessage.getSenderId().equals(currentUserId));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String message) {
        long timestamp = System.currentTimeMillis();
        ChatMessage chatMessage = new ChatMessage(message, currentUserId, otherUserId, timestamp);
        
        mDatabase.child("chats").child(chatId).child("messages").push().setValue(chatMessage)
                .addOnSuccessListener(aVoid -> {
                    etChatMessage.setText("");
                    updateConversations(message, timestamp);
                })
                .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show());
    }

    private void updateConversations(String lastMessage, long timestamp) {
        mDatabase.child("users").child(currentUserId).child("fullName").get().addOnSuccessListener(snapshot -> {
            String currentUserName = snapshot.getValue(String.class);
            
            Conversation convForMe = new Conversation(otherUserId, otherUserName, lastMessage, timestamp);
            mDatabase.child("conversations").child(currentUserId).child(otherUserId).setValue(convForMe);

            Conversation convForOther = new Conversation(currentUserId, currentUserName, lastMessage, timestamp);
            mDatabase.child("conversations").child(otherUserId).child(currentUserId).setValue(convForOther);
        });
    }

    private void addMessageToUI(String text, boolean isOutgoing) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(32, 24, 32, 24);
        textView.setTextSize(14);
        
        int colorRes = isOutgoing ? android.R.color.white : android.R.color.black;
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
            if (scrollView != null) {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private String getEmojiForName(String name) {
        String[] emojis = {"😊", "😎", "🦊", "🐻", "🐱", "🐶", "🐼", "🐨", "🦁", "🐯"};
        int index = Math.abs(name.hashCode()) % emojis.length;
        return emojis[index];
    }
}
