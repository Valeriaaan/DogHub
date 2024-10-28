package com.example.pethub.view.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pethub.R;
import com.example.pethub.databinding.FragmentRegisterStepTwoBinding;

public class RegisterStepTwoFragment extends Fragment {

    private FragmentRegisterStepTwoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterStepTwoBinding.inflate(inflater, container, false);

        binding.buttonNext.setOnClickListener(v -> {
            String healthConcern = binding.editTextHealthConcern.getText().toString().trim();
            String medication = binding.editTextMedication.getText().toString().trim();
            String surgery = binding.editTextSurgery.getText().toString().trim();

            if (healthConcern.isEmpty() || medication.isEmpty() || surgery.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            ((RegisterFragment) getParentFragment()).setStepTwoData(healthConcern, medication, surgery);
        });

        return binding.getRoot();
    }
}
