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
import health_content.RoutineCheckFragment;
import health_content.CommonHealthFragment;
import health_content.GroomingForHealth;
import health_content.MentalhealthEnrichment_kit_essentials;
import health_content.SignsOfHealthyDog;

public class HealthPageFragment extends Fragment {

    public HealthPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_page, container, false);

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

        CardView routineCardView = view.findViewById(R.id.RoutineCardView); // Changed to CardView
        routineCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RoutineCheckFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView healthIssueCardView = view.findViewById(R.id.HealthIssueCardView); // Changed to CardView
        healthIssueCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new CommonHealthFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView groomingCardView = view.findViewById(R.id.GroomingCardView); // Changed to CardView
        groomingCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new GroomingForHealth());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView parasiteCardView = view.findViewById(R.id.ParasiteCardView); // Changed to CardView
        parasiteCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new MentalhealthEnrichment_kit_essentials());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView signHealthyDogView = view.findViewById(R.id.SignHealthyDogView); // Changed to CardView
        signHealthyDogView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new SignsOfHealthyDog());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        return view;
    }
}
