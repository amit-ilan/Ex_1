package com.example.contactsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

/**
 * Viewa list of all contacts.
 */
public class ListFragment extends Fragment implements ContactClickListener {

    private CustomAdapter adapter;
    private ContactViewModel viewModel;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        Log.d("viewModel", viewModel.toString() + " viewModel created");
        viewModel.getContacts().observe(requireActivity(), contacts -> {
            // set up the RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.rvContacts);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new CustomAdapter(contacts, this, getActivity());
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Moves to details fragment of given contact
     *
     * @param contact to show
     */
    @Override
    public void onContactClicked(Contact contact) {
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        String name = contact.getName();
        String phone = contact.getPhone();
        String mail = contact.getMail();
        String img = contact.getImg();
        String id = contact.getDeviceId();

        Bundle bundle = DetailsFragment.getBundle(name, phone, mail, img, id);
        navController.navigate(R.id.action_listFragment_to_detailsFragment, bundle);
    }
}
