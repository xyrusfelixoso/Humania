package com.example.humania;

public class User {
    public String fullName;
    public String email;
    public int totalDonations;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.totalDonations = 0;
    }
}
