package com.example.pethub.view.register;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pethub.R;
import com.example.pethub.databinding.FragmentRegisterStepThreeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterStepThreeFragment extends Fragment {

    private FragmentRegisterStepThreeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterStepThreeBinding.inflate(inflater, container, false);

        binding.editTextLastVaccination.setOnClickListener(v -> showDatePickerDialog((view, year, month, dayOfMonth) -> {
            String date = formatDate(year, month, dayOfMonth);
            binding.editTextLastVaccination.setText(date);
        }));

        binding.editTextNextVaccination.setOnClickListener(v -> showDatePickerDialog((view, year, month, dayOfMonth) -> {
            String date = formatDate(year, month, dayOfMonth);
            binding.editTextNextVaccination.setText(date);
        }));

        binding.buttonFinish.setOnClickListener(v -> {
            String lastVaccination = binding.editTextLastVaccination.getText().toString().trim();
            String nextVaccination = binding.editTextNextVaccination.getText().toString().trim();
            String clinic = binding.editTextClinic.getText().toString().trim();

            if (lastVaccination.isEmpty() || nextVaccination.isEmpty() || clinic.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            ((RegisterFragment) getParentFragment()).setStepThreeData(lastVaccination, nextVaccination, clinic);
        });

        return binding.getRoot();
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), listener, year, month, day);
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Format can be adjusted
        return sdf.format(calendar.getTime());
    }
}
