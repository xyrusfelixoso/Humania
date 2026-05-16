package com.example.humania;

import java.io.Serializable;

public class PickupRequest implements Serializable {
    private String requestId;
    private String donationId;
    private String donorId;
    private String requesterId;
    private String requesterName;
    private String itemName;
    private String itemPhotoPath;
    private String requestDate;
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED
    private String validationStatus; // NOT_VALIDATED, VALIDATED

    public PickupRequest() {}

    public PickupRequest(String requestId, String donationId, String donorId, String requesterId, String requesterName, String itemName, String itemPhotoPath, String requestDate) {
        this.requestId = requestId;
        this.donationId = donationId;
        this.donorId = donorId;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.itemName = itemName;
        this.itemPhotoPath = itemPhotoPath;
        this.requestDate = requestDate;
        this.status = "PENDING";
        this.validationStatus = "NOT_VALIDATED";
    }

    // Getters and Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getDonationId() { return donationId; }
    public void setDonationId(String donationId) { this.donationId = donationId; }

    public String getDonorId() { return donorId; }
    public void setDonorId(String donorId) { this.donorId = donorId; }

    public String getRequesterId() { return requesterId; }
    public void setRequesterId(String requesterId) { this.requesterId = requesterId; }

    public String getRequesterName() { return requesterName; }
    public void setRequesterName(String requesterName) { this.requesterName = requesterName; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemPhotoPath() { return itemPhotoPath; }
    public void setItemPhotoPath(String itemPhotoPath) { this.itemPhotoPath = itemPhotoPath; }

    public String getRequestDate() { return requestDate; }
    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getValidationStatus() { return validationStatus; }
    public void setValidationStatus(String validationStatus) { this.validationStatus = validationStatus; }
}
