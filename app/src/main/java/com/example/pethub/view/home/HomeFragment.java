package com.example.pethub.view.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.example.pethub.R;
import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.profile.ProfileFragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the Nutrition CardView and set the click listener
        CardView cardNutrition = view.findViewById(R.id.cardNutrition);
        cardNutrition.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            // Replace current Fragment with NutritionPageFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NutritionPageFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        // Find the Health CardView and set the click listener
        CardView cardHealth = view.findViewById(R.id.cardHealth);
        cardHealth.setOnClickListener(v -> {
            v.setAlpha(0.5f);
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            // Replace current Fragment with HealthPageFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HealthPageFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        // Find the Exercise CardView and set the click listener
        CardView cardExercise = view.findViewById(R.id.cardExercise);
        cardExercise.setOnClickListener(v -> {
            v.setAlpha(0.5f);
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            // Replace current Fragment with ExercisePageFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ExercisePageFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        // Find the contacts ImageView and set the click listener
        ImageView navContacts = view.findViewById(R.id.navContacts);
        navContacts.setOnClickListener(v -> {
            v.setAlpha(0.5f);
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            // Replace current Fragment with ContactsFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ContactsFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        // Find the profile ImageView and set the click listener
        ImageView navProfile = view.findViewById(R.id.navProfile);
        navProfile.setOnClickListener(v -> {
            v.setAlpha(0.5f);
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            // Replace current Fragment with ProfileFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProfileFragment());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        return view;
    }
}
