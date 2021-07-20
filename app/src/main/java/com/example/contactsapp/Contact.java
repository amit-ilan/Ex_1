package com.example.contactsapp;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String mail;
    private String phone;
    private String img;

    public Contact(String name, String mail, String phone, String img){
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
