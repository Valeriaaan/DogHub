package com.example.pethub.view.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.pethub.R;
import com.example.pethub.adapter.ContactsAdapter;
import com.example.pethub.adapter.DogsAdapter;
import com.example.pethub.model.Contact;
import com.example.pethub.model.Dog;
import com.example.pethub.view.ViewHolderFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements ContactsAdapter.OnContactClickListener {

    private RecyclerView recyclerViewContacts;
    private ContactsAdapter contactsAdapter;
    private final List<Contact> contactList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerViewContacts = view.findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        firestore = FirebaseFirestore.getInstance();
        loadContactsData();

        contactsAdapter = new ContactsAdapter(getContext(), contactList, this);
        recyclerViewContacts.setAdapter(contactsAdapter);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsAdapter.filterContacts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactsAdapter.filterContacts(newText);
                return false;
            }
        });

        return view;
    }

    private void loadContactsData() {
        CollectionReference contactsRef = firestore.collection("contacts");

        contactsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                contactList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Contact contact = new Contact();
                    contact.setVetName(document.getString("name"));
                    contact.setVet(document.getString("veterinarian"));
                    contact.setVetContact(document.getString("contact"));
                    contact.setVetAddress(document.getString("address"));
                    contact.setVetPicture(document.getString("picture"));

                    contactList.add(contact);
                    Log.d("ContactData", "Contact Name: " + contact.getVetName() + ", Address: " + contact.getVetAddress());
                }
                // Initialize the adapter after data is loaded
                contactsAdapter = new ContactsAdapter(getContext(), contactList, this);
                recyclerViewContacts.setAdapter(contactsAdapter);
                contactsAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
            } else {
                Log.e("ContactsFragment", "Error getting contacts: ", task.getException());
            }
        });
    }

    @Override
    public void onContactClick(Contact contact) {
        if (getParentFragment() instanceof ViewHolderFragment) {
            ((ViewHolderFragment) getParentFragment()).navigateToContactDetails(contact);
        }
    }
}