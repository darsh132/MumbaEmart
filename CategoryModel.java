package com.example.mumbae_mart.model;

public class CategoryModel {

    private String CategoryIconLink;
    private String categoryName;

    public CategoryModel(String CategoryIconLink, String CategoryName) {
        CategoryIconLink = CategoryIconLink;
        this.categoryName = CategoryName;
    }

    public String getCategoryIconLink() {
        return CategoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }
}