package com.itgroup.bean;

public class People {
    private String name;  //사람 이름
    private String image;  //이미지 파일 이름(확장자 포함)

    public People(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public People() {
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

    @Override
    public String toString() {
        return "people{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
