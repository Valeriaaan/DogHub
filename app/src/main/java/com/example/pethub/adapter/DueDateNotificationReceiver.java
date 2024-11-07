package com.example.pethub.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pethub.R;

public class DueDateNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DueDateNotificationReceiver", "Receiver triggered for notification");

        String channelId = intent.getStringExtra("CHANNEL_ID");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notifications_active_ic)
                .setContentTitle("Vaccination Reminder")
                .setContentText("Today is the due date for your pet's vaccination.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, builder.build()); // Ensure unique ID for second notification
    }
}
