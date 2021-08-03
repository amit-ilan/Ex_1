package com.example.contactsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

/**
 * Views details of one contact.
 */
public class DetailsFragment extends Fragment {
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_PHONE = "phone";
    private static final String CONTACT_MAIL = "mail";
    private static final String CONTACT_IMG = "img";
    private static final String CONTACT_ID = "id";

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    public static Bundle getBundle(String name, String phone, String mail, String img, String id) {
        Bundle args = new Bundle();
        args.putString(CONTACT_NAME, name);
        args.putString(CONTACT_PHONE, phone);
        args.putString(CONTACT_MAIL, mail);
        args.putString(CONTACT_IMG, img);
        args.putString(CONTACT_ID, id);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        assert getArguments() != null;
        String name = getArguments().getString(CONTACT_NAME);
        String mail = getArguments().getString(CONTACT_MAIL);
        String phone = getArguments().getString(CONTACT_PHONE);
        String img = getArguments().getString(CONTACT_IMG);
        String id = getArguments().getString(CONTACT_ID);

        TextView nameTextView = requireActivity().findViewById(R.id.nameTextView);
        nameTextView.setText(name);

        TextView mailTextView = requireActivity().findViewById(R.id.mailTextView);
        mailTextView.setText(mail);

        TextView phoneTextView = requireActivity().findViewById(R.id.phoneTextView);
        phoneTextView.setText(phone);

        Button hideButton = requireActivity().findViewById(R.id.hideButton);
        hideButton.setOnClickListener(view1 -> hideContact(id));

        Glide.with(requireActivity()).load(img).error(R.drawable.person).placeholder(R.drawable.person).into((ImageView) requireActivity().findViewById(R.id.imageView));
    }

    /**
     * Hides given contact, called when HIDE button pressed
     *
     * @param id of contact to remove
     */
    public void hideContact(String id) {
        ContactViewModel viewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        viewModel.onHideContact(id);
        Toast.makeText(getContext(), "Contact hidden from list", Toast.LENGTH_SHORT).show();
    }
}