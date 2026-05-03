package com.fashionstore.model;

import java.sql.Timestamp;

public class WishlistItem {
    private int wishlistItemId;
    private int wishlistId;
    private int productId;
    private Timestamp addedAt;
    
    // Joined data
    private String productName;
    private double price;
    private String imageUrl;

    public int getWishlistItemId() { return wishlistItemId; }
    public void setWishlistItemId(int wishlistItemId) { this.wishlistItemId = wishlistItemId; }
    public int getWishlistId() { return wishlistId; }
    public void setWishlistId(int wishlistId) { this.wishlistId = wishlistId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(Timestamp addedAt) { this.addedAt = addedAt; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
