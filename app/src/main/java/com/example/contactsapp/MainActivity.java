package com.example.contactsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int CONTACT_PERMISSION = 123;


    ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        setContactPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContactPermission();
    }

    /**
     * ===================== Permission related functions ==============================
     */

    @AfterPermissionGranted(CONTACT_PERMISSION)
    private void setContactPermission(){
        String[] perms = {Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission
//            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//            NavController navController = navHostFragment.getNavController();
//            navController.navigate(R.id.listFragment);
            viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
            Log.d("viewModel", viewModel.toString() + " viewModel created");
            viewModel.onListStart(this, new AndroidContactsProvider());
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
        Log.d("1","onPermissionsDenied called");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
interface ContactClickListener {
    void onContactClicked(Contact contact);
}