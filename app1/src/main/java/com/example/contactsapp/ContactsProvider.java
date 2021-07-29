package com.example.contactsapp;

import android.content.Context;

import java.util.HashMap;

/**
 * Interface of contact provider
 */
public interface ContactsProvider {
    public HashMap<String, Contact> getContacts(Context context);
}
