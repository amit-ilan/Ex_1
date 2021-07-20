package com.example.contactsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity implements ContactClickListener, EasyPermissions.PermissionCallbacks {

    public static final String EXTRA_CONTACT_NAME = "EXTRA.CONTACT_NAME";
    public static final String EXTRA_CONTACT_MAIL = "EXTRA.CONTACT_MAIL";
    public static final String EXTRA_CONTACT_PHONE = "EXTRA.CONTACT_PHONE";
    public static final String EXTRA_CONTACT_IMG = "EXTRA.CONTACT_IMG";
    private static final int CONTACT_PERMISSION = 123;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContactPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContactPermission();
    }

    @AfterPermissionGranted(CONTACT_PERMISSION)
    private void setContactPermission(){
        String[] perms = {Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            init(loadContacts().toArray(new Contact[0]));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_contact), CONTACT_PERMISSION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull @NotNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //Toast.makeText(getApplicationContext(),"Permissions required",Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.rvContacts), "Permission required", Snackbar.LENGTH_LONG)
                    .setAction("GO TO SETTINGS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });

            snackbar.show();
    }

    private Collection<Contact> loadContacts(){

        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,null
        );

        List<Contact> contacts = new ArrayList<>();

        if (cursor != null) {
            String phone = null;
            String email = null;

            int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int imgIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);

            while (cursor.moveToNext()) {
                String id = cursor.getString(idIndex);

                Cursor pCur = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    phone = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Cursor eCur = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (eCur.moveToNext()) {
                    email = eCur.getString(eCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.ADDRESS));
                }

                contacts.add(new Contact(cursor.getString(nameIndex),
                        email,
                        phone,
                        cursor.getString(imgIndex)));
            }
        }

        return contacts;
    }

    private void init(Contact[] contacts){
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(contacts, this, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onContactClicked(Contact contact) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

        // extract all contact fields
        String img = contact.getImg();
        String mail = contact.getMail();
        String name = contact.getName();
        String phone = contact.getPhone();

        // add it to intent
        intent.putExtra(EXTRA_CONTACT_NAME, name);
        intent.putExtra(EXTRA_CONTACT_MAIL, mail);
        intent.putExtra(EXTRA_CONTACT_PHONE, phone);
        intent.putExtra(EXTRA_CONTACT_IMG, img);

        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private Contact[] localDataSet;
        private final ContactClickListener contactClickListener;
        private Context context;

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         * @param contactClickListener
         */
        public CustomAdapter(Contact[] dataSet, ContactClickListener contactClickListener, Context context) {
            localDataSet = dataSet;
            this.contactClickListener = contactClickListener;
            this.context = context;
        }


        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.contant_item, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            Contact contact = localDataSet[position];

            viewHolder.textView.setText(contact.getName());
            String img = contact.getImg();
            Glide.with(this.context).load(img).error(R.drawable.person).placeholder(R.drawable.person).into(viewHolder.imageView);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //option 1: open activity here
                    //option 2 move the event up in the chain
                    contactClickListener.onContactClicked(contact);
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.length;
        }


        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textView = (TextView) view.findViewById(R.id.contact_name);
                imageView = (ImageView) view.findViewById(R.id.contact_img);
            }

            public TextView getTextView() {
                return textView;
            }
            public ImageView getImageView() { return imageView;}
        }
    }


}
interface ContactClickListener {
    void onContactClicked(Contact contact);
}