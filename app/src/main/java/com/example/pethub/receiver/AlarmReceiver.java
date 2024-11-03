package com.example.pethub.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.example.pethub.R;
import com.example.pethub.view.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarm_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarm received");

        // Retrieve the alarm time and label from the intent
        String alarmTime = intent.getStringExtra("time"); // Time for the title
        String alarmLabel = intent.getStringExtra("label"); // Label for the text

        // Create an intent for the activity you want to start when the alarm triggers
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Create the PendingIntent with FLAG_IMMUTABLE for Android 12+
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Set up the notification manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // For Android 8.0 (Oreo) and above, create a notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        Log.d("AlarmReceiver", "Alarm Time: " + alarmTime + ", Alarm Label: " + alarmLabel);

        // Build the notification using the retrieved time and label
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)  // Replace with your own icon
                .setContentTitle(alarmTime)  // Set the time as the title
                .setContentText(alarmLabel)  // Set the label as the text
                .setContentIntent(pendingIntent)  // Attach the pendingIntent
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // Notify using the NotificationManager
        notificationManager.notify(1, notification);
    }
}
