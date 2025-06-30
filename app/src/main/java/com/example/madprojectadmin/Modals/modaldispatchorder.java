package com.example.madprojectadmin.Modals;

public class modaldispatchorder {

    private String customerName;
    private String paymentStatus;

    // Constructor
    public modaldispatchorder(String customerName, String paymentStatus) {
        this.customerName = customerName;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
