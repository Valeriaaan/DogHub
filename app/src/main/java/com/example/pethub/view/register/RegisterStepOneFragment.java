package com.example.pethub.view.register;

import static android.Manifest.permission.CAMERA;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pethub.R;
import com.example.pethub.databinding.FragmentRegisterStepOneBinding;
import com.example.pethub.model.Dog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;

public class RegisterStepOneFragment extends Fragment {

    private FragmentRegisterStepOneBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_PICK = 2;
    private Uri imageUri; // Store the image URI for uploading
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db; // Firestore reference

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterStepOneBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        binding.addImageButton.setOnClickListener(v -> showImagePickerOptions());

        binding.buttonNext.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();
            String ageText = binding.editTextAge.getText().toString().trim();
            String sex = binding.editTextSex.getText().toString().trim();
            String breed = binding.editTextBreed.getText().toString().trim();

            if (name.isEmpty() || ageText.isEmpty() || sex.isEmpty() || breed.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Invalid age entered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Upload the image to Firebase Storage and get the URL
            uploadImageToFirebase(imageUri, name, age, sex, breed);
        });

        return binding.getRoot();
    }

    private void showImagePickerOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Image Source")
                .setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openCamera();
                        } else {
                            openGallery();
                        }
                    }
                })
                .show();
    }

    private void openCamera() {
        // Check for camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            // Create an intent to open the camera
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    binding.imagePetProfilePic.setImageBitmap(imageBitmap);
                    imageUri = getImageUri(imageBitmap);
                } else {
                    Toast.makeText(requireContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_GALLERY_PICK && data != null) {
                imageUri = data.getData();
                if (imageUri != null) {
                    binding.imagePetProfilePic.setImageURI(imageUri);
                } else {
                    Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImageToFirebase(Uri imageUri, String name, int age, String sex, String breed) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");

            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    ((RegisterFragment) requireParentFragment()).setStepOneData(name, age, sex, breed, imageUrl);
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
