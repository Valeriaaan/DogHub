package com.example.pethub.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    private static final float SCALE_FACTOR = 0.75f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) { // [-Infinity,-1)
            page.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            // Adjust the scale and alpha of the page
            final float scaleFactor = position < 0
                    ? 1 + position // Scale up when swiping right
                    : 1 - position; // Scale down when swiping left
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            // Set the rotation around the Y-axis for a cube effect
            final float rotationY = position * -30; // Rotate 30 degrees
            page.setTranslationY(0);
            page.setRotationY(rotationY);

            // Fade the page out at the end
            page.setAlpha(1 - Math.abs(position));
        } else { // (1,+Infinity]
            page.setAlpha(0);
        }
    }
}
