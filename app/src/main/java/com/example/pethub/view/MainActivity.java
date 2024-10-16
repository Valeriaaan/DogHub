package com.example.pethub.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.pethub.R;
import com.example.pethub.view.register.RegisterStepOneFragment;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout; // Declare LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the layout containing "Get Started" elements
        linearLayout = findViewById(R.id.LinearLayout); // Initialize LinearLayout

        // Find the start button
        MaterialButton startButton = findViewById(R.id.start_btn);

        // Set a click listener to replace the current fragment with RegisterStepOneFragment
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the LinearLayout
                linearLayout.setVisibility(View.GONE);

                // Hide the logo
                findViewById(R.id.logoCard).setVisibility(View.GONE);

                // Use FragmentManager to replace the current fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RegisterStepOneFragment())
                        .commit();
            }
        });


        // Apply system window insets for edge-to-edge layouts
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
