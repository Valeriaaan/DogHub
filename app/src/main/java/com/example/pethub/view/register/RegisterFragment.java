package com.example.pethub.view.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.pethub.R;
import com.example.pethub.model.Dog;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment extends Fragment {

    private String name, sex, breed, allergies, clinic, lastVaccinationDate, nextVaccinationDate, picture, medication, surgery;
    private int age;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFragment(new RegisterStepOneFragment());

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getChildFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else  {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.registerFragment, true)
                        .build();

                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void setStepOneData(String name, int age, String sex, String breed, String picture) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.breed = breed;
        this.picture = picture;

        loadFragment(new RegisterStepTwoFragment());
    }

    public void setStepTwoData(String allergies, String medication, String surgery) {
        this.allergies = allergies;
        this.medication = medication;
        this.surgery = surgery;

        loadFragment(new RegisterStepThreeFragment());
    }

    public void setStepThreeData(String lastVaccinationDate, String nextVaccinationDate, String clinic) {
        this.lastVaccinationDate = lastVaccinationDate;
        this.nextVaccinationDate = nextVaccinationDate;
        this.clinic = clinic;

        saveToFirestore();
    }

    private void saveToFirestore() {
        Dog dog = new Dog(name, age, sex, breed, allergies, lastVaccinationDate, nextVaccinationDate, clinic, picture, medication, surgery);

        db.collection("dogs")
                .add(dog)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(requireContext(), "Dog profile saved successfully!", Toast.LENGTH_SHORT).show();

                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.onBoardingFragment, true)
                            .build();

                    Navigation.findNavController(requireView()).navigate(R.id.action_registerStepTwoFragment_to_viewHolderFragment, null, navOptions);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Failed to save dog profile: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

}
