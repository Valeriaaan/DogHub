package com.example.pethub.view.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pethub.R;
import com.example.pethub.view.RegistrationViewModel;

public class ProfileDetailsFragment extends Fragment {

    private RegistrationViewModel viewModel; // Declare ViewModel
    private TextView nameTextView, breedTextView, ageTextView, sexTextView; // Add TextViews for other fields
    private ImageView profileImageView; // To display the profile image

    public static ProfileDetailsFragment newInstance(String dogName, String dogBreed) {
        ProfileDetailsFragment fragment = new ProfileDetailsFragment();
        Bundle args = new Bundle();
        args.putString("DOG_NAME", dogName);
        args.putString("DOG_BREED", dogBreed);
        // Add other views for displaying vaccination date, due date, clinic, allergies, etc.

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        // Retrieve arguments
        if (getArguments() != null) {
            String dogName = getArguments().getString("DOG_NAME");
            String dogBreed = getArguments().getString("DOG_BREED");
            // Set values in ViewModel or handle as needed
            viewModel.setDogName(dogName);
            viewModel.setDogBreed(dogBreed);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        // Initialize TextViews and ImageView
        nameTextView = view.findViewById(R.id.textViewName);
        breedTextView = view.findViewById(R.id.textViewBreed);


        // Set the data from ViewModel
        if (viewModel != null) {
            nameTextView.setText(viewModel.getDogName() != null ? viewModel.getDogName() : "Unknown");
            breedTextView.setText(viewModel.getDogBreed() != null ? viewModel.getDogBreed() : "Unknown");

            // If you have a profile image, set it as well
            String imageUri = viewModel.getProfileImageUri();
            if (imageUri != null) {
                profileImageView.setImageURI(Uri.parse(imageUri));
            }
        }

        return view;
    }

}