package com.example.sanya.challenge2;

/**
 * Created by sanya on 28/1/17.
 */

public class Product {
    private String brandName;
    private String productName;
    private String thumbnailImageUrl;
    private String originalPrice;

    public Product(String brandName, String productName, String thumbnailImageUrl, String originalPrice) {
        this.brandName = brandName;
        this.productName = productName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.originalPrice = originalPrice;
    }

    public String getBrandName() {
        return this.brandName;
    }
    public String getProductName() {
        return this.productName;
    }
    public String getOriginalPrice() {
        return this.originalPrice;
    }
    public String getThumbnailImageUrl() {
        return this.thumbnailImageUrl;
    }

    public void setBrandName(String brandName) {this.brandName = brandName; };
    public void setBProductName(String productName) {this.productName = productName; };
    public void setOriginalPrice(String thumbnailImageUrl) {this.thumbnailImageUrl = thumbnailImageUrl; };
    public void setThumbnailImageUrl(String originalPrice) {this.originalPrice = originalPrice; };
}
