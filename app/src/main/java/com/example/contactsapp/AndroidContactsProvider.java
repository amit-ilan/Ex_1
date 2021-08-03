package com.example.contactsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.HashMap;

/**
 * Provides contacts list from android device
 */
public class AndroidContactsProvider implements ContactsProvider {
    @Override
    public HashMap<String, Contact> getContacts(Context context) {
        HashMap<String, Contact> contacts = new HashMap<>();

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null, null
        );

        if (cursor != null) {
            String phone;
            String email;

            int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int imgIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idIndex);

                phone = getProperty(context, id, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER);
                email = getProperty(context, id, ContactsContract.CommonDataKinds.Email.CONTENT_URI, ContactsContract.CommonDataKinds.Email.CONTACT_ID, ContactsContract.CommonDataKinds.Email.ADDRESS);

                contacts.put(id, new Contact(cursor.getString(nameIndex),
                        email,
                        phone,
                        cursor.getString(imgIndex),
                        id));
            }
            cursor.close();
        }
        return contacts;
    }

    /**
     * Get required property (phone, email..) of required contact from given chart
     *
     * @param context           context of the app
     * @param id                id of contact
     * @param contentUri        chart to query
     * @param propertySelection selection for the query
     * @param propertyColumn    column of required property
     * @return value of the property
     */
    private String getProperty(Context context, String id, Uri contentUri, String propertySelection, String propertyColumn) {
        String property = null;
        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(
                contentUri,
                null,
                propertySelection + " = ?",
                new String[]{id},
                null);
        while (cursor.moveToNext()) {
            property = cursor.getString(cursor.getColumnIndex(propertyColumn));
        }
        return property;
    }
}
