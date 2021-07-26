package com.example.contactsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContactViewModel extends ViewModel {

    public static final String EXTRA_CONTACT_NAME = "EXTRA.CONTACT_NAME";
    public static final String EXTRA_CONTACT_MAIL = "EXTRA.CONTACT_MAIL";
    public static final String EXTRA_CONTACT_PHONE = "EXTRA.CONTACT_PHONE";
    public static final String EXTRA_CONTACT_IMG = "EXTRA.CONTACT_IMG";

    private final MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();
    private HashMap<String, Contact> contactsMap = new HashMap<>();
    private List<String> hiddenContacts = new ArrayList<>();

    public void onListStart(Context context, ContactsProvider contactsProvider){
        HashMap<String, Contact> loadedContacts = contactsProvider.getContacts(context);

        for (String id: this.hiddenContacts){
            loadedContacts.remove(id);
        }

        this.contactsMap = loadedContacts;
        this.contactsLiveData.setValue(new ArrayList<>(loadedContacts.values()));
    }

    public void onHideContact(String contactId){
        this.hiddenContacts.add(contactId);
        this.contactsMap.remove(contactId);
        this.contactsLiveData.setValue(new ArrayList<>(this.contactsMap.values()));
    }

    public LiveData<List<Contact>> getContacts(){
        return this.contactsLiveData;
    }
}
