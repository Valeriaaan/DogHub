package com.example.pethub.view.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.pethub.R;
import com.example.pethub.databinding.FragmentContactDetailsBinding;

public class ContactDetailsFragment extends Fragment {


    private FragmentContactDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Get the data from arguments
        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String veterinarian = getArguments().getString("veterinarian");
            String address = getArguments().getString("address");
            String contact = getArguments().getString("contact");
            String picture = getArguments().getString("picture");

            // Set data to the views using binding
            binding.textViewClinicName.setText(name);
            binding.textViewVeterinarian.setText(!TextUtils.isEmpty(veterinarian) ? veterinarian : "Unknown");
            binding.textViewAddress.setText(!TextUtils.isEmpty(address) ? address : "Unknown");
            binding.textViewContact.setText(!TextUtils.isEmpty(contact) ? contact : "Unknown");

            // Use Glide to load the image or show a placeholder if empty
            Glide.with(this)
                    .load(!TextUtils.isEmpty(picture) ? picture : R.drawable.img_image_placeholder)
                    .placeholder(R.drawable.img_image_placeholder) // Display while loading
                    .error(R.drawable.img_image_placeholder) // Display if loading fails
                    .into(binding.imgUserProfile);

            binding.textViewContact.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(contact) && !contact.equals("Unknown")) {
                    // Create an intent to open the phone dialer with the number filled in
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contact));
                    startActivity(intent);
                }
            });

            binding.textViewContact.setText(Html.fromHtml("<u>"+contact+ "</u>"));

            binding.textViewAddress.setText(Html.fromHtml("<u>"+ address +"</u>"));

            // Make the address clickable
            binding.textViewAddress.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(name)) {
                    // Open Google Maps with the address
                    String uri = "geo:0,0?q=" + Uri.encode(name);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            });
        }

        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
