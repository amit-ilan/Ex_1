package com.example.contactsapp;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Viewmodel for contact app
 */
public class ContactViewModel extends ViewModel {

    private final MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();
    private HashMap<String, Contact> contactsMap = new HashMap<>();
    private final List<String> hiddenContacts = new ArrayList<>();

    /**
     * Provide contacts list, called when the UI start to view the list.
     *
     * @param context          context of the app
     * @param contactsProvider to get contacts from
     */
    public void onListStart(Context context, ContactsProvider contactsProvider) {
        HashMap<String, Contact> loadedContacts = contactsProvider.getContacts(context);

        for (String id : this.hiddenContacts) {
            loadedContacts.remove(id);
        }

        this.contactsMap = loadedContacts;
        this.contactsLiveData.setValue(new ArrayList<>(loadedContacts.values()));
    }

    /**
     * Removes contact from the visible contacts list.
     *
     * @param contactId contact to hide
     */
    public void onHideContact(String contactId) {
        if (!this.contactsMap.keySet().contains(contactId)) {
            return;
        }
        this.hiddenContacts.add(contactId);
        this.contactsMap.remove(contactId);
        this.contactsLiveData.setValue(new ArrayList<>(this.contactsMap.values()));
    }

    /**
     * @return liveData of the contacts list
     */
    public LiveData<List<Contact>> getContacts() {
        return this.contactsLiveData;
    }
}
