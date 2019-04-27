package com.example.resmanage.Model;

public class Food {
    private String Name, Image, CategoryID;
    private String Price;

    public Food() {
    }

    public Food(String name, String image, String price, String categoryID) {
        Name = name;
        Image = image;
        Price = price;
        CategoryID = categoryID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
