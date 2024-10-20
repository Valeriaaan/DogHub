package com.example.pethub.view.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider; // Import this for ViewModel
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pethub.R;
import com.example.pethub.view.RegistrationViewModel;
import com.example.pethub.view.profile.ProfileFragment;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class RegisterStepOneFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE = 2;
    private Uri selectedImageUri;
    private CircleImageView profileImageView;
    private EditText editTextName, editTextBreed;
    private RegistrationViewModel viewModel; // Declare ViewModel

    public RegisterStepOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step_one, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);

        profileImageView = view.findViewById(R.id.imageView2);
        editTextName = view.findViewById(R.id.editTextName);
        editTextBreed = view.findViewById(R.id.editTextBreed);

        profileImageView.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                showEditDeleteDialog();
            } else {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    launchImagePicker();
                }
            }
        });

        MaterialButton nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> validateInputs());

        return view;
    }

    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");  // Only allow images to be selected
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);  // Display selected image in ImageView
        }
    }

    private void validateInputs() {
        String name = editTextName.getText().toString().trim();
        String breed = editTextBreed.getText().toString().trim();

        if (name.isEmpty() || breed.isEmpty()) {
            Toast.makeText(getActivity(), "Please complete the form first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the data in ViewModel
        viewModel.setDogName(name);
        viewModel.setDogBreed(breed);
        if (selectedImageUri != null) {
            viewModel.setProfileImageUri(selectedImageUri.toString());
        }

        // Navigate to RegisterStepTwoFragment
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new RegisterStepTwoFragment())
                .addToBackStack(null)
                .commit();
    }



    private void showEditDeleteDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Edit or Delete Image")
                .setMessage("What would you like to do?")
                .setPositiveButton("Edit", (dialog, which) -> launchImagePicker())
                .setNegativeButton("Delete", (dialog, which) -> {
                    profileImageView.setImageResource(R.drawable.img_image_placeholder);
                    selectedImageUri = null;
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
