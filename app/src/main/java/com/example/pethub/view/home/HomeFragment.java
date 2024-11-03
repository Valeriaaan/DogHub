package com.example.pethub.view.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.pethub.R;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    public interface OnCardClickListener {
        void onExerciseCardClicked();
        void onNutritionCardClicked();
        void onHealthCardClicked();
    }

    private OnCardClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof OnCardClickListener) {
            listener = (OnCardClickListener) parentFragment;
        } else {
            throw new RuntimeException(parentFragment.toString() + " must implement OnCardClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView exerciseCard = view.findViewById(R.id.cardExercise);
        CardView nutritionCard = view.findViewById(R.id.cardNutrition);
        CardView heathCard = view.findViewById(R.id.cardHealth);
        CardView cardProfile = view.findViewById(R.id.profileCard);

        exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onExerciseCardClicked();
                }
            }
        });

        nutritionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNutritionCardClicked();
                }
            }
        });

        heathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onHealthCardClicked();
                }
            }
        });

        // Navigate to AlarmFragment when cardProfile is clicked
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAlarmFragment(v);
            }
        });

        return view;
    }

    private void navigateToAlarmFragment(View view) {
        // Use Navigation component to navigate to AlarmFragment
        Navigation.findNavController(view).navigate(R.id.alarmFragment);
    }
}
