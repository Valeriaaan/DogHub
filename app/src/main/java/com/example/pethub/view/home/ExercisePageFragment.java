package com.example.pethub.view.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pethub.R;
import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.profile.ProfileFragment;
import exercise_content.exercise_by_age;
import exercise_content.exercise_by_breed;
import exercise_content.types_of_exercise;
import exercise_content.indoor_outdoor_exercises;
import exercise_content.signs_of_too_little_and_too_much_exercise;

public class ExercisePageFragment extends Fragment {

    public ExercisePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_page, container, false);

        // Set up navigation for Home
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

        // Set up navigation for Contacts
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

        // Set up navigation for Profile
        ImageView navProfile = view.findViewById(R.id.navProfile);
        navProfile.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });


        CardView exerciseAgeCardView = view.findViewById(R.id.ExerciseAgeCardView); // Changed to CardView
        exerciseAgeCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new exercise_by_age());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView exerciseBreedCardView = view.findViewById(R.id.ExerciseBreedCardView); // Changed to CardView
        exerciseBreedCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new exercise_by_breed());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView typesExerciseCardView = view.findViewById(R.id.TypesExerciseCardView); // Changed to CardView
        typesExerciseCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new types_of_exercise());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView indoorExerciseCardView = view.findViewById(R.id.IndoorExerciseCardView); // Changed to CardView
        indoorExerciseCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new indoor_outdoor_exercises());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView littleExerciseCardView = view.findViewById(R.id.LittleExerciseCardView); // Changed to CardView
        littleExerciseCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new signs_of_too_little_and_too_much_exercise());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        return view;
    }
}
