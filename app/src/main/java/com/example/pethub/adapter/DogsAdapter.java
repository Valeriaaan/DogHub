package com.example.pethub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pethub.R;
import com.example.pethub.model.Dog;

import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> {

    private final List<Dog> dogList;
    private final Context context;
    private final OnDogClickListener onDogClickListener;
    private ItemTouchHelper itemTouchHelper;

    public DogsAdapter(Context context, List<Dog> dogList, OnDogClickListener listener, ItemTouchHelper itemTouchHelper) {
        this.context = context;
        this.dogList = dogList;
        this.onDogClickListener = listener;
        this.itemTouchHelper = this.itemTouchHelper;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dogs, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.textViewDogName.setText(dog.getDogName());
        holder.textViewDogBreed.setText(dog.getDogBreed());

        // Load the image with Glide
        if (dog.getDogPicture() != null && !dog.getDogPicture().isEmpty()) {
            Glide.with(context).load(dog.getDogPicture())
                    .placeholder(R.drawable.img_image_placeholder)
                    .into(holder.imageViewPicture);
        } else {
            holder.imageViewPicture.setImageResource(R.drawable.img_image_placeholder);
        }

        holder.itemView.setOnClickListener(view -> {
            if (onDogClickListener != null) {
                onDogClickListener.onDogClick(dog);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (itemTouchHelper != null) {
                itemTouchHelper.startDrag(holder);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPicture;
        TextView textViewDogName, textViewDogBreed;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDogName = itemView.findViewById(R.id.textViewDogName);
            textViewDogBreed = itemView.findViewById(R.id.textViewDogBreed);
            imageViewPicture = itemView.findViewById(R.id.imageViewPicture);
        }
    }

    public interface OnDogClickListener {
        void onDogClick(Dog dog);
    }

    public void removeDog(int position) {
        dogList.remove(position);
        notifyItemRemoved(position);
    }

}
