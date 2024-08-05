package com.itgroup.bean;

public class Item {
    private String category;  //카테고리
    private String name;  //상품 이름
    private String image;  //이미지 파일 이름(확장자 포함)

    @Override
    public String toString() {
        return "Item{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public Item() {
    }

    public Item(String category, String name, String image) {
        this.category = category;
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }




}
