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
import nutrition_content.RecommendedDietByAge;
import nutrition_content.TypesOfDogFood;
import nutrition_content.Treats;
import nutrition_content.HomemadeDogfoodGuidelines;

public class NutritionPageFragment extends Fragment {

    public NutritionPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_page, container, false);

        // Set up navigation for Home
        ImageView navHome = view.findViewById(R.id.navHome);
        navHome.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HomeFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Set up navigation for Contacts
        ImageView navContacts = view.findViewById(R.id.navContacts);
        navContacts.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ContactsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Set up navigation for Profile
        ImageView navProfile = view.findViewById(R.id.navProfile);
        navProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        CardView recommendedCardView = view.findViewById(R.id.RecommendedCardView); // Changed to CardView
        recommendedCardView.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RecommendedDietByAge());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView TypesOfDogFood = view.findViewById(R.id.PortionSizeCardView); // Changed to CardView
        TypesOfDogFood.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new TypesOfDogFood());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView treats = view.findViewById(R.id.Treats); // Changed to CardView
        treats.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new Treats());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });


        CardView homamadeDogFoodGuidelines = view.findViewById(R.id.HomamadeDogFoodGuidelines); // Changed to CardView
        homamadeDogFoodGuidelines.setOnClickListener(v -> {
            // Change alpha for feedback
            v.setAlpha(0.5f);

            // Reset alpha after a short delay
            v.postDelayed(() -> v.setAlpha(1.0f), 200);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new HomemadeDogfoodGuidelines());
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        });

        return view;
    }
}
