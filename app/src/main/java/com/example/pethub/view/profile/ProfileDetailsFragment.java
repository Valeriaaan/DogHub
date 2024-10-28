package com.example.pethub.view.profile;

import android.text.TextUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pethub.R;
import com.example.pethub.databinding.FragmentProfileDetailsBinding;

public class ProfileDetailsFragment extends Fragment {

    private FragmentProfileDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (getArguments() != null) {
            String dogName = getArguments().getString("dogName");
            String dogBreed = getArguments().getString("dogBreed");
            String dogPicture = getArguments().getString("dogPicture");
            String sex = getArguments().getString("dogSex");
            int age = getArguments().getInt("dogAge");
            String lastVaccinationDate = getArguments().getString("lastVaccinationDate");
            String nextVaccinationDate = getArguments().getString("nextVaccinationDate");
            String clinic = getArguments().getString("clinic");
            String allergies = getArguments().getString("allergies");
            String medication = getArguments().getString("medication");
            String surgery = getArguments().getString("surgery");

            // Set default values for each TextView if the data is null or empty
            binding.textViewName.setText(getOrDefault(dogName, "Not Set"));
            binding.textViewBreed.setText(getOrDefault(dogBreed, "Not Set"));
            binding.textViewSex.setText(getOrDefault(sex, "Not Set"));
            binding.textViewAge.setText(age > 0 ? String.valueOf(age) : "Not Set");
            binding.textViewLastVaccination.setText(getOrDefault(lastVaccinationDate, "Not Set"));
            binding.textViewNextVaccination.setText(getOrDefault(nextVaccinationDate, "Not Set"));
            binding.textViewClinic.setText(getOrDefault(clinic, "Not Set"));
            binding.textViewAllergies.setText(getOrDefault(allergies, "Not Set"));
            binding.textViewMedication.setText(getOrDefault(medication, "Not Set"));
            binding.textViewSurgery.setText(getOrDefault(surgery, "Not Set"));

            Glide.with(this)
                    .load(!TextUtils.isEmpty(dogPicture) ? dogPicture : R.drawable.img_image_placeholder)
                    .placeholder(R.drawable.img_image_placeholder)
                    .error(R.drawable.img_image_placeholder)
                    .into(binding.imgDogProfile);
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private String getOrDefault(String value, String defaultValue) {
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
