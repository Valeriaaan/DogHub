package com.example.pethub.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;
import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class ViewHolderFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private final Map<Integer, Fragment> fragmentMap = new HashMap<>(); // Map for menu item IDs to fragments

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_holder, container, false);

        bottomNavigationView = view.findViewById(R.id.bottom_navigationUser);

        // Initialize the fragment map
        fragmentMap.put(R.id.navigation_home, new HomeFragment());
        fragmentMap.put(R.id.navigation_contact, new ContactsFragment());
        fragmentMap.put(R.id.navigation_profile, new ProfileFragment());

        // Set default fragment
        loadFragment(fragmentMap.get(R.id.navigation_home));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = fragmentMap.get(item.getItemId()); // Get the corresponding fragment
                return loadFragment(selectedFragment); // Load the selected fragment
            }
        });

        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        // Replace the current fragment with the selected one
        if (fragment != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}
