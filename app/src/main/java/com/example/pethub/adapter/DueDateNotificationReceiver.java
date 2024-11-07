package com.example.pethub.adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pethub.R;

public class DueDateNotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "vaccination_due_channel";
    private static final String CHANNEL_NAME = "Vaccination Due Notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DueDateNotificationReceiver", "Receiver triggered for notification");

        // Create notification channel if API level is 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for pet vaccination due dates");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notifications_active_ic) // Ensure this icon exists in your resources
                .setContentTitle("Vaccination Reminder")
                .setContentText("Today is the due date for your pet's vaccination.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, builder.build()); // Ensure unique ID for notification
    }
}
