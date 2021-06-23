package com.example.mumbae_mart.model;

public class MyOrderItemModel {

    private int product_image;
    private int rating;
    private String productTitle;
    private String deliveryStatus;

    public MyOrderItemModel(int product_image, String rating, String productTitle, String deliveryStatus) {
    }

    public void MyorderItemModel(int product_image, int rating, String productTitle, String deliveryStatus) {
        this.product_image = product_image;
        this.rating = rating;
        this.productTitle = productTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getProductImage() {
        return product_image;
    }

    public void setProductImage(int productImage) {
        this.product_image = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}