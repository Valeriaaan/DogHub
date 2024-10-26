package com.example.pethub.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pethub.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private FloatingActionButton fabAddDog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fabAddDog = view.findViewById(R.id.fabAddDog);

        // Set click listener on the FAB
        fabAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileDetailsFragment
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment); // Use your NavHostFragment ID
                navController.navigate(R.id.action_profileFragment_to_profileDetailsFragment); // Use the correct action ID
            }
        });

        return view;
    }
}
