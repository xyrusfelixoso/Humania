package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessagesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        View chatItem1 = view.findViewById(R.id.chatItem1);
        View chatItem2 = view.findViewById(R.id.chatItem2);

        if (chatItem1 != null) {
            chatItem1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("donorName", "Ana Lim");
                startActivity(intent);
            });
        }

        if (chatItem2 != null) {
            chatItem2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("donorName", "Mang Juan");
                startActivity(intent);
            });
        }

        return view;
    }
}
