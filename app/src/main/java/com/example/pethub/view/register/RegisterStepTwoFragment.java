package com.example.pethub.view.register;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pethub.R;
import com.example.pethub.view.RegistrationViewModel;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.profile.ProfileFragment; // Ensure you import the ProfileFragment
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterStepTwoFragment extends Fragment {

    private TextView vaccinationDateInput;
    private TextView dueDateInput;
    private AutoCompleteTextView vetClinicInput;

    // Sample list of vet clinics
    private final ArrayList<String> vetClinics = new ArrayList<>();
    private RegistrationViewModel viewModel; // Declare ViewModel

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_step_two, container, false);

        // Initialize date input fields
        vaccinationDateInput = view.findViewById(R.id.VaccinationDateInput);
        dueDateInput = view.findViewById(R.id.tvDueDate);
        vetClinicInput = view.findViewById(R.id.tvVetClinic);

        // Vet clinics
        vetClinics.add("Animal Park Veterinary Clinic");
        vetClinics.add("Camarines Vet Pro Plus");
        vetClinics.add("Dames Animal Clinic");
        vetClinics.add("Daet Pet Salon and Veterinary Services");
        vetClinics.add("Office of the Provincial Veterinarian");
        vetClinics.add("Rons Animal Clinic New");
        vetClinics.add("Jahfriends Veterinary Clinic");
        vetClinics.add("Kho Veterinary Clinic");
        vetClinics.add("ANIMAL HEART VETERINARY CLINIC");
        vetClinics.add("Animalandia Veterinary Clinic");
        vetClinics.add("Animal Park Veterinary Clinic");

        // Set up the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, vetClinics);
        vetClinicInput.setAdapter(adapter);

        // Set click listener for Latest Vaccination Date
        vaccinationDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(vaccinationDateInput);
            }
        });

        // Set click listener for Next Due Date of Vaccination
        dueDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dueDateInput);
            }
        });

        // Find the Previous and Next buttons
        MaterialButton previousButton = view.findViewById(R.id.previousButton); // "Previous" button
        MaterialButton nextButton = view.findViewById(R.id.nextButton); // "Next" button

        // Set click listener for Previous button
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RegisterStepOneFragment())
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });

        // Set click listener for Next button
        nextButton.setOnClickListener(v -> {
            // Validate fields before proceeding
            if (validateFields()) {
                // Store the data in ViewModel
                viewModel.setVaccinationDate(vaccinationDateInput.getText().toString());
                viewModel.setDueDate(dueDateInput.getText().toString());
                viewModel.setVetClinic(vetClinicInput.getText().toString());
                // Proceed to ProfileFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                // Show Toast message if validation fails
                Toast.makeText(getContext(), "Please complete the form first.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showDatePickerDialog(TextView dateTextView) {
        // Implement date picker dialog logic
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set selected date to TextView
                    dateTextView.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

        datePickerDialog.show();
    }

    private boolean validateFields() {
        // Check if any of the required fields are empty
        return !vaccinationDateInput.getText().toString().trim().isEmpty() &&
                !dueDateInput.getText().toString().trim().isEmpty() &&
                !vetClinicInput.getText().toString().trim().isEmpty();
    }
}
