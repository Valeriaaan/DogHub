package com.example.pethub.view.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        dogsAdapter = new DogsAdapter(getContext(), dogList, this);
        recyclerViewContacts.setAdapter(dogsAdapter);

        fabAddDog = view.findViewById(R.id.fabAddDog);
        fabAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onFabClicked();
                }
            }
        });

        return view;
    }

    private void loadDogsData() {
        CollectionReference dogsRef = firestore.collection("dogs");

        dogsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dogList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Dog dog = new Dog();

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

