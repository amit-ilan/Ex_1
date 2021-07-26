package com.example.contactsapp;

import android.content.Context;

import java.util.HashMap;

public interface ContactsProvider {
    public HashMap<String, Contact> getContacts(Context context);
}
