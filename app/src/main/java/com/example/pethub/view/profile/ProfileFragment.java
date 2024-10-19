package com.example.pethub.view.profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.pethub.R;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.contacts.ContactsFragment;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the home ImageView and set the click listener
        ImageView navHome = view.findViewById(R.id.navHome);
        navHome.setOnClickListener(v -> {
            // Replace current Fragment with HomeFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HomeFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        // Find the contacts ImageView and set the click listener
        ImageView navContacts = view.findViewById(R.id.navContacts);
        navContacts.setOnClickListener(v -> {
            // Replace current Fragment with ContactsFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ContactsFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        return view;
    }
}
