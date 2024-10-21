package com.example.pethub.view.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pethub.R;
import com.example.pethub.view.RegistrationViewModel;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.contacts.ContactsFragment;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the home and contacts ImageView and set the click listener
        ImageView navHome = view.findViewById(R.id.navHome);
        navHome.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HomeFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        ImageView navContacts = view.findViewById(R.id.navContacts);
        navContacts.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ContactsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Get references to the dog profile views
        ImageView profileImageView = view.findViewById(R.id.ivThumbnail);
        TextView dogNameTextView = view.findViewById(R.id.textViewDogName);
        TextView dogBreedTextView = view.findViewById(R.id.textViewDogBreed);
        CardView dogProfileCard = view.findViewById(R.id.cardDogProfile); // Ensure the ID exists in XML

        // Retrieve data from ViewModel
        RegistrationViewModel viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        String dogName = viewModel.getDogName();
        String dogBreed = viewModel.getDogBreed();
        String profileImageUri = viewModel.getProfileImageUri();

        // Set the values to the respective views
        if (dogName != null) {
            dogNameTextView.setText(dogName);
        }
        if (dogBreed != null) {
            dogBreedTextView.setText(dogBreed);
        }
        if (profileImageUri != null) {
            profileImageView.setImageURI(Uri.parse(profileImageUri));
        }

        // Set click listener for the card (just navigate to ProfileDetailsFragment without passing data)
        dogProfileCard.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProfileDetailsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
