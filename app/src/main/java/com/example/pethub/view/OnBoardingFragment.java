package com.example.pethub.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;
import com.example.pethub.databinding.FragmentExerciseBinding;
import com.example.pethub.databinding.FragmentOnBoardingBinding;

public class OnBoardingFragment extends Fragment {

    private FragmentOnBoardingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.onBoardingFragment, true)
                        .build();

                Navigation.findNavController(requireView()).navigate(R.id.action_onBoardingFragment_to_registerFragment, null, navOptions);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}