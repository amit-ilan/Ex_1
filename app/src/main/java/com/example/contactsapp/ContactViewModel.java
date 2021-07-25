package com.example.contactsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class ContactViewModel extends ViewModel {

    public static final String EXTRA_CONTACT_NAME = "EXTRA.CONTACT_NAME";
    public static final String EXTRA_CONTACT_MAIL = "EXTRA.CONTACT_MAIL";
    public static final String EXTRA_CONTACT_PHONE = "EXTRA.CONTACT_PHONE";
    public static final String EXTRA_CONTACT_IMG = "EXTRA.CONTACT_IMG";

    private final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

    public void onListStart(Context context, boolean havePerm){

        if(!havePerm){
            //update ui?
            return; // exit
        }

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,null
        );

        List<Contact> loadedContacts = new ArrayList<>();

        if (cursor != null) {
            String phone = null;
            String email = null;

            int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int imgIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idIndex);

                Cursor pCur = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    phone = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Cursor eCur = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (eCur.moveToNext()) {
                    email = eCur.getString(eCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.ADDRESS));
                }

                loadedContacts.add(new Contact(cursor.getString(nameIndex),
                        email,
                        phone,
                        cursor.getString(imgIndex)));
            }
        }

        this.contacts.setValue(loadedContacts);
    }

    public LiveData<List<Contact>> getContacts(){
        return this.contacts;
    }
}
