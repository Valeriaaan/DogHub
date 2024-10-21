package exercise_content;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.pethub.R;

public class indoor_outdoor_exercises extends Fragment {

    public indoor_outdoor_exercises() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indoor_outdoor_exercises, container, false);

        // Find the back icon
        View backIcon = view.findViewById(R.id.back_icon);

        // Change alpha for feedback
        backIcon.setAlpha(0.5f);

        // Reset alpha after a short delay
        backIcon.postDelayed(() -> backIcon.setAlpha(1.0f), 200);

        // Set an OnClickListener to handle back navigation
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack(); // Go back to the previous fragment
            }
        });

        return view;
    }
}
