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
import com.google.android.material.button.MaterialButton;

public class RegisterStepTwoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_step_two, container, false);

        // Find the Previous and Next buttons
        MaterialButton previousButton = view.findViewById(R.id.previousButton); // "Previous" button
        MaterialButton nextButton = view.findViewById(R.id.nextButton); // "Next" button

        // Set click listener for Previous button
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to RegisterStepOneFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RegisterStepOneFragment()) // fragment_container is the ID of the container where fragments are displayed
                        .addToBackStack(null) // Optional: Adds this transaction to the back stack so the user can navigate back
                        .commit();
            }
        });

        // Set click listener for Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate forward to RegisterStepThreeFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RegisterStepThreeFragment()) // fragment_container is the ID of the container where fragments are displayed
                        .addToBackStack(null) // Optional: Adds this transaction to the back stack so the user can navigate back
                        .commit();
            }
        });

        return view;
    }
}
