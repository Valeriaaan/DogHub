package com.example.pethub.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;
import com.example.pethub.adapter.ViewPagerAdapter;
import com.example.pethub.model.Contact;
import com.example.pethub.model.Dog;
import com.example.pethub.view.contacts.ContactDetailsFragment;
import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.profile.ProfileDetailsFragment;
import com.example.pethub.view.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class ViewHolderFragment extends Fragment implements ProfileFragment.OnFabClickListener, HomeFragment.OnCardClickListener {

    private BottomNavigationView bottomNavigationView;
    private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_holder, container, false);

        bottomNavigationView = view.findViewById(R.id.bottom_navigationUser);
        viewPager = view.findViewById(R.id.viewPager);

        fragmentMap.put(R.id.navigation_home, new HomeFragment());
        fragmentMap.put(R.id.navigation_contact, new ContactsFragment());
        fragmentMap.put(R.id.navigation_profile, new ProfileFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);

        viewPager.setPageTransformer(new DepthPageTransformer());

        // Set bottom navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int position = 0;
            // Using item ID to determine the position
            if (item.getItemId() == R.id.navigation_contact) {
                position = 1;
            } else if (item.getItemId() == R.id.navigation_profile) {
                position = 2;
            }
            viewPager.setCurrentItem(position, true); // Switch to the selected fragment
            return true;
        });

        // Sync the ViewPager with the BottomNavigationView
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true); // Update selected item
            }
        });

        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onFabClicked() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_viewHolderFragment_to_registerStepOneFragment);
    }

    @Override
    public void onExerciseCardClicked() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_viewHolderFragment_to_exerciseFragment);
    }

    @Override
    public void onNutritionCardClicked() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_viewHolderFragment_to_nutritionFragment);
    }

    @Override
    public void onHealthCardClicked() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_viewHolderFragment_to_healthFragment);
    }

    public void navigateToProfileDetails(Dog dog) {
        ProfileDetailsFragment profileDetailsFragment = new ProfileDetailsFragment();

        Bundle args = new Bundle();
        args.putString("dogName", dog.getDogName());
        args.putString("dogBreed", dog.getDogBreed());
        args.putString("dogPicture", dog.getDogPicture());
        args.putString("dogSex", dog.getDogSex());
        args.putInt("dogAge", dog.getDogAge());
        args.putString("lastVaccinationDate", dog.getLastVaccinationDate());
        args.putString("nextVaccinationDate", dog.getNextVaccinationDate());
        args.putString("clinic", dog.getClinic());
        args.putString("allergies", dog.getAllergies());
        args.putString("medication", dog.getMedication());
        args.putString("surgery", dog.getSurgery());
        profileDetailsFragment.setArguments(args);

        // Replace the current fragment in nav_host_fragment with ProfileDetailsFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, profileDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToContactDetails(Contact contact) {
        ContactDetailsFragment contactsDetailsFragment = new ContactDetailsFragment();

        Bundle args = new Bundle();
        args.putString("name", contact.getVetName());
        args.putString("veterinarian", contact.getVet());
        args.putString("address", contact.getVetAddress());
        args.putString("contact", contact.getVetContact());
        args.putString("picture", contact.getVetPicture());
        contactsDetailsFragment.setArguments(args);

        // Replace the current fragment in nav_host_fragment with ProfileDetailsFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, contactsDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

}

