package com.example.pethub.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pethub.R;
import com.example.pethub.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private final List<Contact> contactList;
    private final List<Contact> originalContactList; // To store unfiltered contacts
    private final Context context;
    private final OnContactClickListener onContactClickListener;

    public ContactsAdapter(Context context, List<Contact> contactList, OnContactClickListener listener) {
        this.context = context;
        this.contactList = new ArrayList<>(contactList); // Initialize contactList with provided list
        this.originalContactList = new ArrayList<>(contactList); // Store the original list for filtering
        this.onContactClickListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.textViewName.setText(contact.getVetName());
        holder.textViewAddress.setText(contact.getVetAddress());

        // Use Glide to load the contact picture
        Glide.with(holder.itemView.getContext())
                .load(contact.getVetPicture())
                .placeholder(R.drawable.img_image_placeholder)
                .error(R.drawable.img_image_placeholder)
                .into(holder.imageViewContact);

        holder.itemView.setOnClickListener(view -> {
            if (onContactClickListener != null) {
                onContactClickListener.onContactClick(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAddress;
        ImageView imageViewContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            imageViewContact = itemView.findViewById(R.id.imageViewPicture);
        }
    }

    // Method to filter contacts
    public void filterContacts(String query) {
        List<Contact> filteredList = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(originalContactList); // Show all contacts if query is empty
        } else {
            for (Contact contact : originalContactList) {
                if (contact.getVetName() != null && contact.getVetName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(contact);
                }
            }
        }
        contactList.clear();
        contactList.addAll(filteredList);
        notifyDataSetChanged();
        Log.d("ContactsAdapter", "Filtered contacts count: " + contactList.size());
    }

    public interface OnContactClickListener {
        void onContactClick(Contact contact);
    }
}
