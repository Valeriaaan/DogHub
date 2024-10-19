package com.example.pethub.view.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.pethub.R;
import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.profile.ProfileFragment;

public class HealthPageFragment extends Fragment {

    public HealthPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition_page, container, false);

        // Set up navigation for Home
        ImageView navHome = view.findViewById(R.id.navHome);
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current Fragment with HomeFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new HomeFragment());
                transaction.addToBackStack(null); // Optional: add to back stack
                transaction.commit();
            }
        });

        // Set up navigation for Contacts
        ImageView navContacts = view.findViewById(R.id.navContacts);
        navContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current Fragment with ContactsFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ContactsFragment());
                transaction.addToBackStack(null); // Optional: add to back stack
                transaction.commit();
            }
        });

        // Set up navigation for Profile
        ImageView navProfile = view.findViewById(R.id.navProfile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current Fragment with ProfileFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ProfileFragment());
                transaction.addToBackStack(null); // Optional: add to back stack
                transaction.commit();
            }
        });

        return view;
    }
}
