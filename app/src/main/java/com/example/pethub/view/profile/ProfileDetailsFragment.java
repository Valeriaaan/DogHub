package com.example.pethub.view.profile;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import android.Manifest;
import com.example.pethub.R;
import com.example.pethub.databinding.FragmentProfileDetailsBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileDetailsFragment extends Fragment {

    private FragmentProfileDetailsBinding binding;
    private static final String TAG = "ProfileDetailsFragment";
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_PICK = 2;
    private Uri imageUri;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firestore = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        if (getArguments() != null) {
            // Extract data from arguments
            String dogName = getArguments().getString("dogName");
            String dogBreed = getArguments().getString("dogBreed");
            String dogPicture = getArguments().getString("dogPicture");
            String sex = getArguments().getString("dogSex");
            int age = getArguments().getInt("dogAge");
            String lastVaccinationDate = getArguments().getString("lastVaccinationDate");
            String nextVaccinationDate = getArguments().getString("nextVaccinationDate");
            String clinic = getArguments().getString("clinic");
            String allergies = getArguments().getString("allergies");
            String medication = getArguments().getString("medication");
            String surgery = getArguments().getString("surgery");

            // Set default values for each TextView if the data is null or empty
            binding.textViewName.setText(getOrDefault(dogName, "Not Set"));
            binding.textViewBreed.setText(getOrDefault(dogBreed, "Not Set"));
            binding.textViewSex.setText(getOrDefault(sex, "Not Set"));
            binding.textViewAge.setText(age > 0 ? String.valueOf(age) : "Not Set");
            binding.textViewLastVaccination.setText(getOrDefault(lastVaccinationDate, "Not Set"));
            binding.textViewNextVaccination.setText(getOrDefault(nextVaccinationDate, "Not Set"));
            binding.textViewClinic.setText(getOrDefault(clinic, "Not Set"));
            binding.textViewAllergies.setText(getOrDefault(allergies, "Not Set"));
            binding.textViewMedication.setText(getOrDefault(medication, "Not Set"));
            binding.textViewSurgery.setText(getOrDefault(surgery, "Not Set"));

            // Load image using Glide
            Glide.with(this)
                    .load(!TextUtils.isEmpty(dogPicture) ? dogPicture : R.drawable.img_image_placeholder)
                    .placeholder(R.drawable.img_image_placeholder)
                    .error(R.drawable.img_image_placeholder)
                    .into(binding.imgDogProfile);
        }

        binding.imgDogProfile.setOnClickListener(v -> showImagePickerOptions());

        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listeners for the cards
        binding.cardDetails.setOnClickListener(v -> showEditDetailsDialog("Edit Profile"));
        binding.cardVaccination.setOnClickListener(v -> showEditVaccinationDialog("Edit Vaccination History"));
        binding.cardMedical.setOnClickListener(v -> showEditMedicalDialog("Edit Medical History"));
    }

    private void showEditDetailsDialog(String title) {
        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextSex = dialogView.findViewById(R.id.editTextSex);
        EditText editTextAge = dialogView.findViewById(R.id.editTextAge);
        EditText editTextBreed = dialogView.findViewById(R.id.editTextBreed);

        // Create the dialog
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setView(dialogView)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Save", null); // Set a placeholder for the positive button

        AlertDialog dialog = dialogBuilder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (saveButton != null) {
                saveButton.setOnClickListener(v -> {
                    // Handle saving data and validation
                    String name = editTextName.getText().toString().trim();
                    String sex = editTextSex.getText().toString().trim();
                    String ageString = editTextAge.getText().toString().trim();
                    String breed = editTextBreed.getText().toString().trim();

                    // Validate input fields
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sex) ||
                            TextUtils.isEmpty(ageString) || TextUtils.isEmpty(breed)) {
                        // Show error dialog or Toast
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Error")
                                .setMessage("All fields must be filled.")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        // Proceed with saving the data
                        int age = Integer.parseInt(ageString);
                        updateProfile(name, sex, age, breed);
                        // Dismiss the dialog only if the data is saved successfully
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    // Example method to handle profile update logic
    private void updateProfile(String name, String sex, int age, String breed) {
        // Query the Firestore for the specific dog document
        firestore.collection("dogs")
                .whereEqualTo("dogName", binding.textViewName.getText().toString().trim())
                .whereEqualTo("dogBreed", binding.textViewBreed.getText().toString().trim())
                .whereEqualTo("dogSex", binding.textViewSex.getText().toString().trim())
                .whereEqualTo("dogAge", Integer.parseInt(binding.textViewAge.getText().toString().trim()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Update the document with new values
                            document.getReference().update("dogName", name,
                                            "dogSex", sex,
                                            "dogAge", age,
                                            "dogBreed", breed)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Profile successfully updated!"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating profile", e));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void showEditVaccinationDialog(String title) {
        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_vaccination, null);

        EditText editTextVaccinationDate = dialogView.findViewById(R.id.editTextVaccinationDate);
        EditText editTextNextDueDate = dialogView.findViewById(R.id.editTextNextDueDate);
        EditText editTextClinic = dialogView.findViewById(R.id.editTextClinic);

        // Create the dialog
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setView(dialogView)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Save", null); // Set a placeholder for the positive button

        AlertDialog dialog = dialogBuilder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (saveButton != null) {
                saveButton.setOnClickListener(v -> {
                    // Handle saving data and validation
                    String vaccinationDate = editTextVaccinationDate.getText().toString().trim();
                    String nextDueDate = editTextNextDueDate.getText().toString().trim();
                    String clinic = editTextClinic.getText().toString().trim();

                    // Validate input fields
                    if (TextUtils.isEmpty(vaccinationDate) || TextUtils.isEmpty(nextDueDate) ||
                            TextUtils.isEmpty(clinic)) {
                        // Show error dialog or Toast
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Error")
                                .setMessage("All fields must be filled.")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        // Save the vaccination history (implement your saving logic here)
                        updateVaccinationHistory(vaccinationDate, nextDueDate, clinic);
                        dialog.dismiss(); // Dismiss dialog if data is saved successfully
                    }
                });
            }

            // Date Picker setup
            editTextVaccinationDate.setOnClickListener(v -> showDatePicker(editTextVaccinationDate));
            editTextNextDueDate.setOnClickListener(v -> showDatePicker(editTextNextDueDate));
        });

        dialog.show();
    }

    private void showDatePicker(EditText editText) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert the date to a readable format and set it to the EditText
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            editText.setText(sdf.format(new Date(selection)));
        });

        datePicker.show(getChildFragmentManager(), "DATE_PICKER");
    }

    // Example method to handle vaccination history saving logic
    private void updateVaccinationHistory(String vaccinationDate, String nextDueDate, String clinic) {
        firestore.collection("dogs")
                .whereEqualTo("dogName", binding.textViewName.getText().toString().trim())
                .whereEqualTo("dogBreed", binding.textViewBreed.getText().toString().trim())
                .whereEqualTo("lastVaccinationDate", binding.textViewLastVaccination.getText().toString().trim())
                .whereEqualTo("nextVaccinationDate", binding.textViewNextVaccination.getText().toString().trim())
                .whereEqualTo("clinic", binding.textViewClinic.getText().toString().trim())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Update the document with new vaccination values
                            document.getReference().update("lastVaccinationDate", vaccinationDate,
                                            "nextVaccinationDate", nextDueDate,
                                            "clinic", clinic)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Vaccination history successfully updated!"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating vaccination history", e));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


    private void showEditMedicalDialog(String title) {
        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_medical, null);

        EditText editTextAllergies = dialogView.findViewById(R.id.editTextAllergies);
        EditText editTextMedication = dialogView.findViewById(R.id.editTextMedication);
        EditText editTextSurgery = dialogView.findViewById(R.id.editTextSurgery);

        // Create the dialog
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setView(dialogView)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Save", null); // Set a placeholder for the positive button

        AlertDialog dialog = dialogBuilder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (saveButton != null) {
                saveButton.setOnClickListener(v -> {
                    // Handle saving data and validation
                    String allergies = editTextAllergies.getText().toString().trim();
                    String medication = editTextMedication.getText().toString().trim();
                    String surgery = editTextSurgery.getText().toString().trim();

                    // Validate input fields
                    if (TextUtils.isEmpty(allergies) || TextUtils.isEmpty(medication) ||
                            TextUtils.isEmpty(surgery)) {
                        // Show error dialog or Toast
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Error")
                                .setMessage("All fields must be filled.")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        // Save the medical history (implement your saving logic here)
                        updateMedicalHistory(allergies, medication, surgery);
                        dialog.dismiss(); // Dismiss dialog if data is saved successfully
                    }
                });
            }
        });

        dialog.show();
    }

    // Example method to handle medical history saving logic
    private void updateMedicalHistory(String allergies, String medication, String surgery) {
        firestore.collection("dogs")
                .whereEqualTo("dogName", binding.textViewName.getText().toString().trim())
                .whereEqualTo("dogBreed", binding.textViewBreed.getText().toString().trim())
                .whereEqualTo("allergies", binding.textViewAllergies.getText().toString().trim())
                .whereEqualTo("medication", binding.textViewMedication.getText().toString().trim())
                .whereEqualTo("surgery", binding.textViewSurgery.getText().toString().trim())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Update the document with new medical values
                            document.getReference().update("allergies", allergies,
                                            "medication", medication,
                                            "surgery", surgery)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Medical history successfully updated!"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating medical history", e));
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
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
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    binding.imgDogProfile.setImageBitmap(imageBitmap);
                    imageUri = getImageUri(imageBitmap);
                    uploadImageToFirebase(imageUri);
                }
            } else if (requestCode == REQUEST_GALLERY_PICK && data != null) {
                imageUri = data.getData();
                if (imageUri != null) {
                    binding.imgDogProfile.setImageURI(imageUri);
                    uploadImageToFirebase(imageUri);
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

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    // Update image URL in Firestore or any other relevant method
                    updateDogProfileImage(imageUrl);
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDogProfileImage(String imageUrl) {

        // Create a query to find the specific dog
        Query query = firestore.collection("dogs") // Replace "dogs" with your collection name
                .whereEqualTo("dogName", binding.textViewName.getText().toString().trim())
                .whereEqualTo("dogBreed", binding.textViewBreed.getText().toString().trim())
                .whereEqualTo("dogSex", binding.textViewSex.getText().toString().trim())
                .whereEqualTo("dogAge", Integer.parseInt(binding.textViewAge.getText().toString().trim()));

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Assuming the document ID is the unique identifier for the dog profile
                    String dogId = document.getId();
                    // Update the dog profile with the new image URL
                    firestore.collection("dogs").document(dogId)
                            .update("dogPicture", imageUrl) // Assuming the field name is "dogPicture"
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Image URL updated successfully: " + imageUrl);
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Error updating image URL", e);
                            });
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });
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

    private String getOrDefault(String value, String defaultValue) {
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
