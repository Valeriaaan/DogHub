package com.example.pethub.view.register;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pethub.R;
import com.example.pethub.adapter.DueDateNotificationReceiver;
import com.example.pethub.databinding.FragmentRegisterStepThreeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterStepThreeFragment extends Fragment {

    private static final String CHANNEL_ID = "vaccination_due_notification";
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    private FragmentRegisterStepThreeBinding binding;
    private Calendar selectedDueDate; // Store the user-selected due date

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterStepThreeBinding.inflate(inflater, container, false);

        binding.editTextLastVaccination.setOnClickListener(v -> showDatePickerDialog((view, year, month, dayOfMonth) -> {
            String date = formatDate(year, month, dayOfMonth);
            binding.editTextLastVaccination.setText(date);
        }));

        binding.editTextNextVaccination.setOnClickListener(v -> showDatePickerDialog((view, year, month, dayOfMonth) -> {
            String date = formatDate(year, month, dayOfMonth);
            binding.editTextNextVaccination.setText(date);
            selectedDueDate = Calendar.getInstance(); // Save selected date for daily notification
            selectedDueDate.set(year, month, dayOfMonth);
        }));

        binding.buttonFinish.setOnClickListener(v -> {
            String lastVaccination = binding.editTextLastVaccination.getText().toString().trim();
            String nextVaccination = binding.editTextNextVaccination.getText().toString().trim();
            String clinic = binding.editTextClinic.getText().toString().trim();

            if (lastVaccination.isEmpty() || nextVaccination.isEmpty() || clinic.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            ((RegisterFragment) getParentFragment()).setStepThreeData(lastVaccination, nextVaccination, clinic);

            // Check and request notification permission if API level is 33 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    createNotificationChannel();
                    showVaccinationDueNotification(nextVaccination); // Show the next vaccination notification
                    scheduleDueDateNotification(); // Schedule the due date notification based on user selection
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
                }
            } else {
                // Permissions are automatically granted on lower API levels
                createNotificationChannel();
                showVaccinationDueNotification(nextVaccination); // Show the next vaccination notification
                scheduleDueDateNotification(); // Schedule the due date notification based on user selection
            }
        });

        return binding.getRoot();
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String nextVaccination = binding.editTextNextVaccination.getText().toString().trim();
                showVaccinationDueNotification(nextVaccination);
                scheduleDueDateNotification();
            } else {
                Toast.makeText(requireContext(), "Permission required to show notifications", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vaccination Due Notification";
            String description = "Notifies about the next due date of vaccination";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showVaccinationDueNotification(String dueDate) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notifications_active_ic) // Replace with your notification icon
                .setContentTitle("Vaccination Due Date")
                .setContentText("Next due date for vaccination: " + dueDate)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        notificationManager.notify(1, builder.build());
    }

    private void scheduleDueDateNotification() {
        if (selectedDueDate != null) {
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Toast.makeText(requireContext(), "Exact alarm permission required", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent intent = new Intent(requireContext(), DueDateNotificationReceiver.class);
            intent.putExtra("CHANNEL_ID", CHANNEL_ID);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


            // Log to verify alarm setting
            Log.d("RegisterStepThree", "Setting alarm for: " + selectedDueDate.getTime());

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedDueDate.getTimeInMillis(), pendingIntent);
        }
    }


    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), listener, year, month, day);
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
