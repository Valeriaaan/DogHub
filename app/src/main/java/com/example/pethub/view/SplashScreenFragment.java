package com.example.pethub.view;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;

public class SplashScreenFragment extends Fragment {

    private static final long SPLASH_DURATION = 2000; // 2 seconds

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigate to ViewHolderFragment after a delay
        new Handler().postDelayed(() -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_splash_to_view_holder);
        }, SPLASH_DURATION);
    }
}
