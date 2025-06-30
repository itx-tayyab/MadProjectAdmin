package com.example.madprojectadmin.Modals;

public class modalorderdetails {
    private String itemName;
    private String itemDescription;
    private String imageUrl;
    private int itemPrice;
    private int quantity;

    public modalorderdetails() {
        // Required empty constructor for Firebase
    }

    public modalorderdetails(String itemName, String itemDescription, String imageUrl, int itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.imageUrl = imageUrl;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
