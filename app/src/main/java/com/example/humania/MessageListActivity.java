package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        findViewById(R.id.btnMsgBack).setOnClickListener(v -> finish());

        findViewById(R.id.chatItem1).setOnClickListener(v -> {
            Intent intent = new Intent(MessageListActivity.this, ChatActivity.class);
            intent.putExtra("donorName", "Ana Lim");
            startActivity(intent);
        });

        findViewById(R.id.chatItem2).setOnClickListener(v -> {
            Intent intent = new Intent(MessageListActivity.this, ChatActivity.class);
            intent.putExtra("donorName", "Mang Juan");
            startActivity(intent);
        });
    }
}
