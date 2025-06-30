package com.example.madprojectadmin.Modals;

public class modalallitemmenu {
    private String imageUrl, title, subtitle, firebaseKey, category;
    private int quantity;
    private double price;

    public modalallitemmenu() {
        // Default constructor required for Firebase
    }

    public modalallitemmenu(String imageUrl, String title, String subtitle, int quantity, double price, String firebaseKey) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subtitle = subtitle;
        this.quantity = quantity;
        this.price = price;
        this.firebaseKey = firebaseKey;
    }

    // 🔧 Add category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Existing getters/setters for all other fields
    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}
