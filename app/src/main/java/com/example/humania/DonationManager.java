package com.example.humania;

import java.util.ArrayList;
import java.util.List;

public class DonationManager {
    private static List<Donation> myDonations = new ArrayList<>();

    public static void addDonation(Donation donation) {
        myDonations.add(donation);
    }

    public static List<Donation> getMyDonations() {
        return myDonations;
    }
    
    public static int getDonationCount() {
        return myDonations.size();
    }
}
