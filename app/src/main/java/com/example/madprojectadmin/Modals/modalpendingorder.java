package com.example.madprojectadmin.Modals;

public class modalpendingorder {
    private String orderId;
    private String name;
    private int totalQuantity;

    public modalpendingorder() {
        // Default constructor required for Firebase
    }

    public modalpendingorder(String orderId, String name, int totalQuantity) {
        this.orderId = orderId;
        this.name = name;
        this.totalQuantity = totalQuantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
