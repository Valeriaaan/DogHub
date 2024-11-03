package com.example.pethub.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pethub.view.contacts.ContactsFragment;
import com.example.pethub.view.home.HomeFragment;
import com.example.pethub.view.profile.ProfileFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ContactsFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new HomeFragment(); // Fallback
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of fragments
    }
}
