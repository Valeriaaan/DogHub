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

    public ProfileDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        // Initialize TextViews and ImageView
        TextView nameTextView = view.findViewById(R.id.textViewName);
        TextView breedTextView = view.findViewById(R.id.textViewBreed);
        ImageView profileImageView = view.findViewById(R.id.user_info_card); // Ensure profileImageView is initialized

        // Display default or placeholder information
        nameTextView.setText("Dog Name");
        breedTextView.setText("Dog Breed");

        // Handle profile image (if any exists)
        String imageUri = viewModel.getProfileImageUri();
        if (imageUri != null) {
            profileImageView.setImageURI(Uri.parse(imageUri));
        }

        return view;
    }
}
