package com.example.contactsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;

public class AndroidContactsProvider implements ContactsProvider {
    @Override
    public HashMap<String, Contact> getContacts(Context context) {
        HashMap<String, Contact> contacts = new HashMap<>();

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,null
        );

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

                contacts.put(id, new Contact(cursor.getString(nameIndex),
                            email,
                            phone,
                            cursor.getString(imgIndex),
                            id));
            }
        }
        return contacts;
    }
}
