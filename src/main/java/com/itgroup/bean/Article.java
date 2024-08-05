package com.itgroup.bean;

public class Article {
    private String category;
    private String name;
    private String image;

    @Override
    public String toString() {
        return "Article{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Article() {
    }

    public Article(String category, String name, String image) {
        this.image = image;
        this.name = name;
        this.category = category;
    }
}
