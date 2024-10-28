package com.example.pethub.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;
import com.example.pethub.databinding.FragmentExerciseBinding;
import com.example.pethub.databinding.FragmentHealthBinding;

public class ExerciseFragment extends Fragment {

    private FragmentExerciseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.ExerciseAgeCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExerciseFragment.this);
            navController.navigate(R.id.action_exerciseFragment_to_exerciseAgeFragment);
        });

        binding.ExerciseBreedCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExerciseFragment.this);
            navController.navigate(R.id.action_exerciseFragment_to_exerciseBreedFragment);
        });

        binding.TypesExerciseCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExerciseFragment.this);
            navController.navigate(R.id.action_exerciseFragment_to_exerciseTypesFragment);
        });

        binding.IndoorExerciseCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExerciseFragment.this);
            navController.navigate(R.id.action_exerciseFragment_to_exerciseIndoorFragment);
        });

        binding.LittleExerciseCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExerciseFragment.this);
            navController.navigate(R.id.action_exerciseFragment_to_exerciseSignsFragment);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}