package com.example.pethub.view.contacts;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.pethub.R;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.profile.ProfileFragment;

public class ContactsFragment extends Fragment {

    // Required empty public constructor
    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Find the home ImageView and set the click listener
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

        // Find the profile ImageView and set the click listener
        ImageView navProfile = view.findViewById(R.id.navProfile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current Fragment with ProfileFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();;
                transaction.replace(R.id.fragment_container, new ProfileFragment());
                transaction.addToBackStack(null); // Optional: add to back stack
                transaction.commit();
            }
        });

        return view;
    }
}
