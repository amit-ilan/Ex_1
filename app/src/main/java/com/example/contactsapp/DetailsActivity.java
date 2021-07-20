package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_CONTACT_NAME);
        String mail = intent.getStringExtra(MainActivity.EXTRA_CONTACT_MAIL);
        String phone = intent.getStringExtra(MainActivity.EXTRA_CONTACT_PHONE);
        String img = intent.getStringExtra(MainActivity.EXTRA_CONTACT_IMG);

        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(name);

        TextView mailTextView = findViewById(R.id.mailTextView);
        mailTextView.setText(mail);

        TextView phoneTextView = findViewById(R.id.phoneTextView);
        phoneTextView.setText(phone);

        Glide.with(getApplicationContext()).load(img).error(R.drawable.person).placeholder(R.drawable.person).into((ImageView) findViewById(R.id.imageView));
    }

    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}