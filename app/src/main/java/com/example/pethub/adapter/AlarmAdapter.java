package com.example.pethub.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pethub.R;
import com.example.pethub.model.AlarmItem;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<AlarmItem> alarmList;
    private OnAlarmInteractionListener onAlarmInteractionListener;
    private FirebaseFirestore firestore;
    private Map<String, ListenerRegistration> listenerRegistrations = new HashMap<>();

    public interface OnAlarmInteractionListener {
        void onAlarmClick(AlarmItem alarmItem);
        void onAlarmToggle(AlarmItem alarmItem);
        void onAlarmCancel(AlarmItem alarmItem);
    }

    public AlarmAdapter(List<AlarmItem> alarmList, OnAlarmInteractionListener listener) {
        this.alarmList = alarmList;
        this.onAlarmInteractionListener = listener;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmItem alarmItem = alarmList.get(position);
        holder.bind(alarmItem);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView labelTextView;
        SwitchMaterial alarmSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            labelTextView = itemView.findViewById(R.id.labelTextView);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onAlarmInteractionListener != null) {
                    onAlarmInteractionListener.onAlarmClick(alarmList.get(position));
                }
            });
        }

        public void bind(AlarmItem alarmItem) {
            labelTextView.setText(alarmItem.getLabel());
            timeTextView.setText(alarmItem.getTime());

            // Remove listener to avoid triggering when setting the switch state
            alarmSwitch.setOnCheckedChangeListener(null);
            alarmSwitch.setChecked(alarmItem.isEnabled());

            // Set a new listener to handle switch toggle
            alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onAlarmInteractionListener != null) {
                    alarmItem.setEnabled(isChecked);
                    onAlarmInteractionListener.onAlarmToggle(alarmItem);

                    // Update Firestore state based on time and label
                    Query updateQuery = firestore.collection("alarms")
                            .whereEqualTo("time", alarmItem.getTime())
                            .whereEqualTo("label", alarmItem.getLabel());

                    updateQuery.get().addOnSuccessListener(querySnapshot -> {
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            document.getReference().update("enabled", isChecked)
                                    .addOnSuccessListener(aVoid -> Log.d("AlarmAdapter", "Alarm state updated in Firestore"))
                                    .addOnFailureListener(e -> Log.w("AlarmAdapter", "Error updating alarm state", e));
                        }
                    });
                }
            });

            // Remove any existing Firestore listener for this alarm item
            if (listenerRegistrations.containsKey(alarmItem.getId())) {
                listenerRegistrations.get(alarmItem.getId()).remove();
            }

            // Firestore listener to keep UI in sync with the database
            Query listenQuery = firestore.collection("alarms")
                    .whereEqualTo("time", alarmItem.getTime())
                    .whereEqualTo("label", alarmItem.getLabel());

            ListenerRegistration listenerRegistration = listenQuery.addSnapshotListener((querySnapshot, e) -> {
                if (e != null) {
                    Log.w("AlarmAdapter", "Listen failed.", e);
                    return;
                }

                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Boolean enabled = document.getBoolean("enabled");
                        if (enabled != null) {
                            alarmSwitch.setOnCheckedChangeListener(null);
                            alarmSwitch.setChecked(enabled);
                            alarmItem.setEnabled(enabled);

                            // Reattach the listener for future toggles
                            alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                int pos = getAdapterPosition();
                                if (pos != RecyclerView.NO_POSITION && onAlarmInteractionListener != null) {
                                    alarmItem.setEnabled(isChecked);
                                    onAlarmInteractionListener.onAlarmToggle(alarmItem);

                                    // Update Firestore state
                                    Query toggleQuery = firestore.collection("alarms")
                                            .whereEqualTo("time", alarmItem.getTime())
                                            .whereEqualTo("label", alarmItem.getLabel());

                                    toggleQuery.get().addOnSuccessListener(querySnapshot2 -> {
                                        for (QueryDocumentSnapshot document2 : querySnapshot2) {
                                            document2.getReference().update("enabled", isChecked)
                                                    .addOnSuccessListener(aVoid -> Log.d("AlarmAdapter", "Alarm state updated in Firestore"))
                                                    .addOnFailureListener(e2 -> Log.w("AlarmAdapter", "Error updating alarm state", e2));
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            });

            // Store the listener for later removal
            listenerRegistrations.put(alarmItem.getId(), listenerRegistration);
        }
    }

    public void removeListeners() {
        for (ListenerRegistration listener : listenerRegistrations.values()) {
            listener.remove();
        }
        listenerRegistrations.clear();
    }
}
