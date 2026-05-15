package com.example.humania;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class DonationManager {
    private static final String DATABASE_URL = "https://humania-942a7-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance(DATABASE_URL).getReference();
    private static int mDonationCount = 0;
    private static String mCurrentListenerUserId = null;

    /**
     * Gets the current user's total donation count.
     * This method maintains a listener to the Firebase database to keep the count updated.
     * @return The number of donations made by the current user.
     */
    public static int getDonationCount() {
        String userId = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

        if (userId != null && !userId.equals(mCurrentListenerUserId)) {
            mCurrentListenerUserId = userId;
            mDonationCount = 0;
            mDatabase.child("users").child(userId).child("totalDonations")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Integer count = snapshot.getValue(Integer.class);
                            if (count != null) {
                                mDonationCount = count;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
        return mDonationCount;
    }

    public static void addDonation(Donation donation, OnDonationCompleteListener listener) {
        String donationId = mDatabase.child("donations").push().getKey();
        if (donationId == null) return;

        mDatabase.child("donations").child(donationId).setValue(donation)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Increment user's personal donation count
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        mDatabase.child("users").child(userId).child("totalDonations")
                                .setValue(ServerValue.increment(1));
                        
                        // Increment global donation count
                        mDatabase.child("globalStats").child("totalDonations")
                                .setValue(ServerValue.increment(1));

                        if (listener != null) listener.onComplete(true, null);
                    } else {
                        if (listener != null) listener.onComplete(false, task.getException().getMessage());
                    }
                });
    }

    public interface OnDonationCompleteListener {
        void onComplete(boolean success, String message);
    }
}
