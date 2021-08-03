package com.example.contactsapp;

import android.content.Context;

import java.util.HashMap;

public class EmptyContactProvider implements ContactsProvider {
    @Override
    public HashMap<String, Contact> getContacts() {
        return new HashMap<>();
    }
}
