package com.example.pethub.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pethub.R;
import com.example.pethub.databinding.FragmentContactDetailsBinding;
import com.example.pethub.databinding.FragmentHealthBinding;

public class HealthFragment extends Fragment {

    private FragmentHealthBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHealthBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.routineCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HealthFragment.this);
            navController.navigate(R.id.action_healthFragment_to_healthRoutineCheckUpsFragment);
        });

        binding.HealthIssueCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HealthFragment.this);
            navController.navigate(R.id.action_healthFragment_to_healthCommonIssuesFragment);
        });

        binding.GroomingCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HealthFragment.this);
            navController.navigate(R.id.action_healthFragment_to_healthGroomingFragment);
        });

        binding.ParasiteCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HealthFragment.this);
            navController.navigate(R.id.action_healthFragment_to_healthMentalEnrichmentFragment);
        });

        binding.SignHealthyDogView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(HealthFragment.this);
            navController.navigate(R.id.action_healthFragment_to_healthSignsHealthyFragment);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}