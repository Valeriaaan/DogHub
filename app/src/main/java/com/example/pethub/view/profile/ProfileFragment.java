package com.example.pethub.view.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pethub.R;
import com.example.pethub.adapter.DogsAdapter;
import com.example.pethub.model.Dog;
import com.example.pethub.view.ViewHolderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment implements DogsAdapter.OnDogClickListener{

    public interface OnFabClickListener {
        void onFabClicked();
    }

    private FloatingActionButton fabAddDog;
    private OnFabClickListener listener;
    private RecyclerView recyclerViewContacts;
    private DogsAdapter dogsAdapter;
    private final List<Dog> dogList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if parent fragment implements the listener
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof OnFabClickListener) {
            listener = (OnFabClickListener) parentFragment;
        } else {
            throw new RuntimeException(parentFragment.toString() + " must implement OnFabClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerViewContacts = view.findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        firestore = FirebaseFirestore.getInstance();
        loadDogsData();

        // Create ItemTouchHelper instance
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT // Enable swipe left/right
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(dogList, fromPosition, toPosition);
                dogsAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Dog dogToRemove = dogList.get(position);

                // Show confirmation dialog before deletion
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure you want to delete " + dogToRemove.getDogName() + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Remove from Firestore
                            removeDogFromFirestore(dogToRemove);
                            // Remove from local list
                            dogsAdapter.removeDog(position);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // Restore the item (refresh the RecyclerView)
                            dogsAdapter.notifyItemChanged(position);
                        })
                        .show();
            }

        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerViewContacts);

        // Initialize DogsAdapter with the ItemTouchHelper
        dogsAdapter = new DogsAdapter(getContext(), dogList, this, itemTouchHelper);
        recyclerViewContacts.setAdapter(dogsAdapter);

        fabAddDog = view.findViewById(R.id.fabAddDog);
        fabAddDog.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFabClicked();
            }
        });

        return view;
    }

    private void removeDogFromFirestore(Dog dog) {
        // Assuming dog object has a method to get its document ID
        firestore.collection("dogs")
                .document(dog.getDocumentId()) // Make sure Dog has getDocumentId() method
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("DeleteDog", "Dog successfully deleted!");
                })
                .addOnFailureListener(e -> {
                    Log.w("DeleteDog", "Error deleting dog", e);
                });
    }


    private void loadDogsData() {
        CollectionReference dogsRef = firestore.collection("dogs");

        dogsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dogList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Dog dog = new Dog();

                    dog.setDocumentId(document.getId());
                    dog.setDogName(document.getString("dogName"));
                    dog.setDogBreed(document.getString("dogBreed"));
                    dog.setDogPicture(document.getString("dogPicture"));
                    dog.setDogSex(document.getString("dogSex"));
                    dog.setDogAge(document.getLong("dogAge") != null ? document.getLong("dogAge").intValue() : 0);
                    dog.setLastVaccinationDate(document.getString("lastVaccinationDate"));
                    dog.setNextVaccinationDate(document.getString("nextVaccinationDate"));
                    dog.setClinic(document.getString("clinic"));
                    dog.setAllergies(document.getString("allergies"));
                    dog.setMedication(document.getString("medication"));
                    dog.setSurgery(document.getString("surgery"));

                    dogList.add(dog);

                    Log.d("DogData", "Dog Name: " + dog.getDogName() + ", Breed: " + dog.getDogBreed());
                }
                dogsAdapter.notifyDataSetChanged();
            } else {
                // Handle the error
            }
        });
    }

    @Override
    public void onDogClick(Dog dog) {
        // Use getParentFragment() to pass the event to ViewHolderFragment
        if (getParentFragment() instanceof ViewHolderFragment) {
            ((ViewHolderFragment) getParentFragment()).navigateToProfileDetails(dog);
        }
    }
}