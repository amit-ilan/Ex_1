package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String MAIL = "mail";
    private static final String IMG = "img";

    // TODO: Rename and change types of parameters
    private String name;
    private String phone;
    private String mail;
    private String img;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    public static Bundle getBundle(String name, String phone, String mail, String img) {
        //DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(PHONE, phone);
        args.putString(MAIL, mail);
        args.putString(IMG, img);
        //fragment.setArguments(args);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            name = getArguments().getString(NAME);
//            phone = getArguments().getString(PHONE);
//            mail = getArguments().getString(MAIL);
//            img = getArguments().getString(IMG);
//        }

//        Intent intent = getActivity().getIntent();
//        String name = intent.getStringExtra(ContactViewModel.EXTRA_CONTACT_NAME);
//        String mail = intent.getStringExtra(ContactViewModel.EXTRA_CONTACT_MAIL);
//        String phone = intent.getStringExtra(ContactViewModel.EXTRA_CONTACT_PHONE);
//        String img = intent.getStringExtra(ContactViewModel.EXTRA_CONTACT_IMG);

    }

    public void backToMain(View view){ // TODO remove
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = getArguments().getString(NAME);
        mail = getArguments().getString(MAIL);
        phone = getArguments().getString(PHONE);
        img = getArguments().getString(IMG);

        TextView nameTextView = getActivity().findViewById(R.id.nameTextView);
        nameTextView.setText(name);

        TextView mailTextView = getActivity().findViewById(R.id.mailTextView);
        mailTextView.setText(mail);

        TextView phoneTextView = getActivity().findViewById(R.id.phoneTextView);
        phoneTextView.setText(phone);

        Glide.with(getActivity()).load(img).error(R.drawable.person).placeholder(R.drawable.person).into((ImageView) getActivity().findViewById(R.id.imageView));
    }
}