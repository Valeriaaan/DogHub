package com.example.pethub.view.home.alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pethub.R;
import com.example.pethub.adapter.AlarmAdapter;
import com.example.pethub.model.AlarmItem;
import com.example.pethub.receiver.AlarmReceiver;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmFragment extends Fragment implements AlarmAdapter.OnAlarmInteractionListener {

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1001;
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private List<AlarmItem> alarmList;
    private FloatingActionButton fabAddAlarm;
    private FirebaseFirestore db;
    private String label;
    private String time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAlarm);
        fabAddAlarm = view.findViewById(R.id.fabAddAlarm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppCompatImageButton backButton = view.findViewById(R.id.back_button);

        db = FirebaseFirestore.getInstance();
        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(alarmList, this);
        recyclerView.setAdapter(alarmAdapter);

        loadAlarmsFromFirestore();

        fabAddAlarm.setOnClickListener(v -> showAddAlarmDialog());

        // Set up the back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use NavController to navigate back
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                if (navController.getCurrentDestination() != null) {
                    navController.navigateUp();
                }
            }
        });

        return view;
    }

    @Override
    public void onAlarmClick(AlarmItem alarmItem) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Choose Action")
                .setMessage("Would you like to edit or delete this alarm?")
                .setPositiveButton("Edit", (dialog, which) -> showEditAlarmDialog(alarmItem))
                .setNegativeButton("Delete", (dialog, which) -> showDeleteConfirmationDialog(alarmItem))
                .setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showDeleteConfirmationDialog(AlarmItem alarmItem) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this alarm?")
                .setPositiveButton("Yes", (dialog, which) -> deleteAlarmFromFirestore(alarmItem))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteAlarmFromFirestore(AlarmItem alarmItem) {
        db.collection("alarm")
                .whereEqualTo("time", alarmItem.getTime())
                .whereEqualTo("label", alarmItem.getLabel())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("alarm").document(document.getId()).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Alarm deleted successfully!", Toast.LENGTH_SHORT).show();
                                        loadAlarmsFromFirestore();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to delete alarm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(getContext(), "Error finding document: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkAndRequestExactAlarmPermission() {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Show a dialog or Toast to the user explaining why this is necessary
            Toast.makeText(requireContext(), "Please grant exact alarm permission in settings.", Toast.LENGTH_LONG).show();

            // Redirect the user to the app settings where they can enable exact alarm permission
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
            startActivity(intent);
        }
    }


    private void checkNotificationPermission(String newLabel, String newTime) {
        label = newLabel;  // Set the label and time as class-level variables
        time = newTime;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        } else {
            scheduleAlarm(label, time);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scheduleAlarm(label, time);
            } else {
                Toast.makeText(getContext(), "Notification permission is required to set alarms.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cancelAlarm(AlarmItem alarmItem) {
        // Create the intent to cancel the alarm
        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        intent.putExtra("label", alarmItem.getLabel());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                alarmItem.getLabel().hashCode(), // Unique ID based on label
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Cancel the alarm
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(requireContext(), "Alarm canceled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAlarmToggle(AlarmItem alarmItem) {
        // Get the new state from the toggle
        boolean isEnabled = alarmItem.isEnabled();
        Log.d("AlarmToggle", "Alarm toggled: label=" + alarmItem.getLabel() + ", isEnabled=" + isEnabled);

        // Update the alarm in Firestore with the current state
        updateAlarmInFirestore(alarmItem, alarmItem.getLabel(), alarmItem.getTime(), isEnabled);

        // If the alarm is being disabled (canceled), you can also handle the cancellation logic here
        if (!isEnabled) {
            cancelAlarm(alarmItem); // Call the method to cancel the alarm
        } else {
            scheduleAlarm(alarmItem.getLabel(), alarmItem.getTime()); // Reschedule if enabled
        }
    }

    @Override
    public void onAlarmCancel(AlarmItem alarmItem) {
        // Cancel the alarm when toggled off
        cancelAlarm(alarmItem);
    }

    private void showAddAlarmDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_alarm, null);
        EditText labelInput = dialogView.findViewById(R.id.labelEditText);

        MaterialAlertDialogBuilder mainDialogBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Add Alarm")
                .setView(dialogView)
                .setPositiveButton("Set Time", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog mainDialog = mainDialogBuilder.create();

        mainDialog.setOnShowListener(dialog -> {
            Button positiveButton = mainDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String label = labelInput.getText().toString().trim();

                if (label.isEmpty()) {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Warning")
                            .setMessage("Please enter a label for the alarm.")
                            .setPositiveButton("OK", (dialog1, which1) -> {})
                            .show();
                } else {
                    showTimePicker(label, true); // Assuming the default state is 'disabled'
                    mainDialog.dismiss();
                }
            });
        });

        mainDialog.show();
    }

    private void showEditAlarmDialog(AlarmItem alarmItem) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_alarm, null);
        EditText labelInput = dialogView.findViewById(R.id.labelEditText);

        labelInput.setText(alarmItem.getLabel());

        MaterialAlertDialogBuilder editDialogBuilder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Edit Alarm")
                .setView(dialogView)
                .setPositiveButton("Set Time", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog editDialog = editDialogBuilder.create();

        editDialog.setOnShowListener(dialog -> {
            Button positiveButton = editDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String label = labelInput.getText().toString().trim();

                if (label.isEmpty()) {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Warning")
                            .setMessage("Please enter a label for the alarm.")
                            .setPositiveButton("OK", (dialog1, which1) -> {})
                            .show();
                } else {
                    showEditTimePicker(alarmItem, label, alarmItem.isEnabled()); // Assuming you want to keep the existing state
                    editDialog.dismiss();
                }
            });
        });

        editDialog.show();
    }



    private void showEditTimePicker(AlarmItem alarmItem, String label, boolean isEnabled) {
        // Convert the current time string to hours and minutes
        String[] timeParts = alarmItem.getTime().split(" "); // Split time and AM/PM
        String[] hourMinute = timeParts[0].split(":"); // Split hour and minute
        int hour = Integer.parseInt(hourMinute[0]) % 12; // Convert to 12-hour format
        int minute = Integer.parseInt(hourMinute[1]);

        // Create MaterialTimePicker
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setHour(hour) // Set hour from alarm
                .setMinute(minute) // Set minute from alarm
                .setTitleText("Select Alarm Time")
                .setTimeFormat(TimeFormat.CLOCK_12H) // Set to 12-hour format
                .build();

        // Show the time picker
        timePicker.show(getParentFragmentManager(), "editTimePicker");

        // Handle time selection
        timePicker.addOnPositiveButtonClickListener(v -> {
            int selectedHour = timePicker.getHour();
            int selectedMinute = timePicker.getMinute();

            String formattedTime = formatTime(selectedHour, selectedMinute);
            // Call updateAlarmInFirestore with the updated time
            updateAlarmInFirestore(alarmItem, label, formattedTime, isEnabled); // Pass isEnabled here
        });
    }


    private void updateAlarmInFirestore(AlarmItem alarmItem, String label, String time, boolean isEnabled) {
        // Query Firestore to find the document by time and label
        db.collection("alarm")
                .whereEqualTo("time", alarmItem.getTime())  // Use the current time value from the alarm item
                .whereEqualTo("label", alarmItem.getLabel()) // Use the current label value from the alarm item
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Create a map of updated data
                            Map<String, Object> updatedData = new HashMap<>();
                            updatedData.put("time", time);
                            updatedData.put("label", label);
                            updatedData.put("isEnabled", isEnabled); // Update isEnabled flag

                            // Update the Firestore document
                            db.collection("alarm").document(document.getId()).update(updatedData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Alarm updated successfully!", Toast.LENGTH_SHORT).show();
                                        if (isEnabled) {
                                            scheduleAlarm(label, time); // Schedule if enabled
                                        } else {
                                            cancelAlarm(alarmItem); // Cancel if disabled
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Failed to update alarm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(getContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void addAlarmToFirestore(String label, String time, boolean isEnabled) {
        Map<String, Object> alarm = new HashMap<>();
        alarm.put("label", label);
        alarm.put("time", time);
        alarm.put("isEnabled", isEnabled); // Add isEnabled to Firestore

        db.collection("alarm")
                .add(alarm)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Alarm added successfully!", Toast.LENGTH_SHORT).show();
                    if (isEnabled) {
                        scheduleAlarm(label, time); // Schedule the alarm only if enabled
                    }
                    loadAlarmsFromFirestore();  // Refresh the list after adding
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add alarm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }



    private void showTimePicker(String label, boolean isEnabled) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select Alarm Time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();

        timePicker.show(getParentFragmentManager(), "timePicker");

        timePicker.addOnPositiveButtonClickListener(v -> {
            int selectedHour = timePicker.getHour();
            int selectedMinute = timePicker.getMinute();

            String formattedTime = formatTime(selectedHour, selectedMinute);
            addAlarmToFirestore(label, formattedTime, isEnabled); // Add isEnabled to Firestore
        });
    }


    private void scheduleAlarm(String label, String time) {
        checkAndRequestExactAlarmPermission();  // Ensure permission is checked first

        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        intent.putExtra("label", label); // Pass the label
        intent.putExtra("time", time); // Pass the time

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                label.hashCode(), // Unique ID based on label to avoid overwriting
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        // Parse the time string to set the alarm correctly
        String[] timeParts = time.split(" ");
        String[] hourMinute = timeParts[0].split(":");
        int hour = Integer.parseInt(hourMinute[0]) % 12; // Convert to 12-hour format
        int minute = Integer.parseInt(hourMinute[1]);

        // Set the alarm's hour and minute, adjusting for AM/PM
        calendar.set(Calendar.HOUR_OF_DAY, hour + (timeParts[1].equals("pm") ? 12 : 0));
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Check if the set time is in the past
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            // If it’s in the past, add one day to make it the next occurrence
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Schedule the exact alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(requireContext(), "Alarm set for " + time, Toast.LENGTH_SHORT).show();
    }



    private String formatTime(int hour, int minute) {
        String amPm = hour < 12 ? "am" : "pm";
        if (hour == 0) hour = 12;
        else if (hour > 12) hour -= 12;
        return String.format("%02d:%02d %s", hour, minute, amPm);
    }

    private void loadAlarmsFromFirestore() {
        db.collection("alarm").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("AlarmFragment", "Listen failed.", error);
                return;
            }
            alarmList.clear();
            for (QueryDocumentSnapshot doc : value) {
                AlarmItem alarmItem = doc.toObject(AlarmItem.class);
                alarmList.add(alarmItem);
            }
            alarmAdapter.notifyDataSetChanged();
        });
    }
}
