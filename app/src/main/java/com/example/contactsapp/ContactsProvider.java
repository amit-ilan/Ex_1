package com.example.contactsapp;

import android.content.Context;

import java.util.HashMap;

/**
 * Interface of contact provider
 */
public interface ContactsProvider {
    HashMap<String, Contact> getContacts();
}
