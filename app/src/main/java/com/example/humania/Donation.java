package com.example.humania;

import java.io.Serializable;

public class Donation implements Serializable {
    private String title;
    private String description;
    private String quantity;
    private String expiryDate;
    private String location;
    private double latitude;
    private double longitude;
    private String category;
    private String photoPath;
    private String timestamp;

    public Donation(String title, String description, String quantity, String expiryDate, String location, double latitude, double longitude, String category, String photoPath, String timestamp) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.photoPath = photoPath;
        this.timestamp = timestamp;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getQuantity() { return quantity; }
    public String getExpiryDate() { return expiryDate; }
    public String getLocation() { return location; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCategory() { return category; }
    public String getPhotoPath() { return photoPath; }
    public String getTimestamp() { return timestamp; }
}
