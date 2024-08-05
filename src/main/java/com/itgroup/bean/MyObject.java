package com.itgroup.bean;

public class MyObject {  //품목의 이름과 이미지 정보를 저장하는 클래스
    private String name;
    private String image;

    public MyObject(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public MyObject() {
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "name='" + name + '\'' +
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
}
