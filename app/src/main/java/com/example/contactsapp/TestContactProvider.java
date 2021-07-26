package com.example.contactsapp;

import android.content.Context;

import java.util.HashMap;

public class TestContactProvider implements ContactsProvider{
    @Override
    public HashMap<String, Contact> getContacts(Context context) {
        return new HashMap<String, Contact>(){{
            put("1", new Contact("A", "A@l.com", "111-111","","1"));
            put("2", new Contact("B", "B@l.com", "111-222","","2"));
            put("3", new Contact("C", "C@l.com", "111-333","","3"));
            put("4", new Contact("D", "D@l.com", "111-444","","4"));
        }};
    }
}
