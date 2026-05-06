package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        // Get data from intent (optional, for dynamic content)
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            TextView tvTitle = findViewById(R.id.tvDetailTitle);
            if (tvTitle != null) tvTitle.setText(title);
        }

        // Back Button
        FrameLayout btnBack = findViewById(R.id.btnDetailBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Chat Button
        Button btnChat = findViewById(R.id.btnChat);
        if (btnChat != null) {
            btnChat.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                intent.putExtra("donorName", "Ana Lim"); // Mock data
                startActivity(intent);
            });
        }

        // Request Pickup Button
        Button btnRequest = findViewById(R.id.btnRequestPickup);
        if (btnRequest != null) {
            btnRequest.setOnClickListener(v -> 
                Toast.makeText(this, "Pickup request sent to the donor!", Toast.LENGTH_LONG).show());
        }

        // Favorite Button
        FrameLayout btnFavorite = findViewById(R.id.btnFavorite);
        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                TextView tvIcon = findViewById(R.id.tvFavoriteIcon);
                if (tvIcon != null) {
                    if (tvIcon.getText().toString().equals("🤍")) {
                        tvIcon.setText("❤️");
                        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        tvIcon.setText("🤍");
                        Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
