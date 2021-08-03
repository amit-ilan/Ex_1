package com.example.contactsapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    private final String name;
    private final String mail;
    private final String phone;
    private final String img;
    private final String deviceId;

    public Contact(String name, String mail, String phone, String img, String deviceId) {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(mail, contact.mail) &&
                Objects.equals(phone, contact.phone) &&
                Objects.equals(img, contact.img) &&
                Objects.equals(deviceId, contact.deviceId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, mail, phone, img, deviceId);
    }
}
