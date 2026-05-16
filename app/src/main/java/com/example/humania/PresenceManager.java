package com.example.humania;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class PresenceManager {
    private static final String DATABASE_URL = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference();

    public static void setUserOnline(boolean isOnline) {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference userRef = mDatabase.child("users").child(userId);
        if (isOnline) {
            userRef.child("isOnline").setValue(true);
            userRef.child("lastSeen").setValue(ServerValue.TIMESTAMP);
            // Handle disconnection
            userRef.child("isOnline").onDisconnect().setValue(false);
            userRef.child("lastSeen").onDisconnect().setValue(ServerValue.TIMESTAMP);
        } else {
            userRef.child("isOnline").setValue(false);
            userRef.child("lastSeen").setValue(ServerValue.TIMESTAMP);
        }
    }
}
