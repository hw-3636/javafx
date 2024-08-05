package com.itgroup.bean;

public class Person {
    private String number;
    private String name;  //이름
    private String lastName;  //성씨

    public Person() {
    }

    public Person(String number, String name, String lastName) {
        this.number = number;
        this.name = name;
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}
