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
import com.example.pethub.databinding.FragmentHealthBinding;
import com.example.pethub.databinding.FragmentNutritionBinding;

public class NutritionFragment extends Fragment {

    private FragmentNutritionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNutritionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.RecommendedCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(NutritionFragment.this);
            navController.navigate(R.id.action_nutritionFragment_to_nutritionRecommendedFragment);
        });

        binding.PortionSizeCardView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(NutritionFragment.this);
            navController.navigate(R.id.action_nutritionFragment_to_nutritionTypesFragment);
        });

        binding.Treats.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(NutritionFragment.this);
            navController.navigate(R.id.action_nutritionFragment_to_nutritionTreatsFragment);
        });

        binding.HomamadeDogFoodGuidelines.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(NutritionFragment.this);
            navController.navigate(R.id.action_nutritionFragment_to_nutritionHomemadeFragment);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}