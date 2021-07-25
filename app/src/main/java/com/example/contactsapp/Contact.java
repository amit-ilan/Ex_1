package com.example.contactsapp;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String mail;
    private String phone;
    private String img;
    private String deviceId;

    public Contact(String name, String mail, String phone, String img, String deviceId){
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.img = img;
        this.deviceId = deviceId;
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

    public String getDeviceId() {
        return deviceId;
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

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
