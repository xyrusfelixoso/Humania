package com.example.humania;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesFragment extends Fragment {

    private DatabaseReference mDatabase;
    private RecyclerView rvMessages;
    private ConversationAdapter adapter;
    private List<Conversation> conversationList;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        String databaseUrl = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl).getReference();
        currentUserId = FirebaseAuth.getInstance().getUid();

        rvMessages = view.findViewById(R.id.rvMessages);
        setupRecyclerView();
        
        if (currentUserId != null) {
            loadUsersAndConversations();
        }

        return view;
    }

    private void setupRecyclerView() {
        conversationList = new ArrayList<>();
        adapter = new ConversationAdapter(conversationList, conversation -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("otherUserId", conversation.getOtherUserId());
            intent.putExtra("donorName", conversation.getOtherUserName());
            startActivity(intent);
        });
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessages.setAdapter(adapter);
    }

    private void loadUsersAndConversations() {
        // Fetch all registered users
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot usersSnapshot) {
                Map<String, Conversation> userMap = new HashMap<>();
                for (DataSnapshot ds : usersSnapshot.getChildren()) {
                    String userId = ds.getKey();
                    if (userId != null && !userId.equals(currentUserId)) {
                        User user = ds.getValue(User.class);
                        if (user != null) {
                            // Initial conversation object for every user
                            userMap.put(userId, new Conversation(userId, user.fullName, "No messages yet", 0));
                        }
                    }
                }

                // Fetch conversation summaries to get the latest message preview
                mDatabase.child("conversations").child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot convSnapshot) {
                        for (DataSnapshot ds : convSnapshot.getChildren()) {
                            Conversation conv = ds.getValue(Conversation.class);
                            if (conv != null && userMap.containsKey(conv.getOtherUserId())) {
                                Conversation userConv = userMap.get(conv.getOtherUserId());
                                userConv.setLastMessage(conv.getLastMessage());
                                userConv.setTimestamp(conv.getTimestamp());
                            }
                        }

                        List<Conversation> fullList = new ArrayList<>(userMap.values());
                        // Sort: Conversations with recent messages first, then others
                        Collections.sort(fullList, (c1, c2) -> {
                            if (c1.getTimestamp() == 0 && c2.getTimestamp() == 0) return 0;
                            if (c1.getTimestamp() == 0) return 1;
                            if (c2.getTimestamp() == 0) return -1;
                            return Long.compare(c2.getTimestamp(), c1.getTimestamp());
                        });

                        conversationList.clear();
                        conversationList.addAll(fullList);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Failed to load users: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
