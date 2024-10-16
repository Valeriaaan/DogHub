package com.example.pethub.view.register;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pethub.R;
import com.example.pethub.view.home.HomeFragment;
import com.google.android.material.button.MaterialButton;

public class RegisterStepThreeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_step_three, container, false);

        // Find the Previous and Next buttons
        MaterialButton previousButton = view.findViewById(R.id.previousButton); // "Previous" button
        MaterialButton nextButton = view.findViewById(R.id.nextButton); // "Next" button

        // Set click listener for Previous button
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to RegisterStepTwoFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RegisterStepTwoFragment()) // Replace with RegisterStepTwoFragment
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });

        // Set click listener for Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment()) // Replace with HomeFragment
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });

        return view;
    }
}
