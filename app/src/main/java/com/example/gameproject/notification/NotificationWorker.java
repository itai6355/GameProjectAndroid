package com.example.gameproject.notification;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class NotificationWorker extends Worker {

    private static final String CHANNEL_ID = "game_notifications";

    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
            sendNotification(getApplicationContext(), "Android Game Reminder", "Don't forget to check out your game progress!");
            scheduleNextNotification(getApplicationContext());
        return Result.success();
    }

    private void sendNotification(Context context, String title, String body) {
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Game Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle(title).setContentText(body)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setAutoCancel(true).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("NotificationWorker", "Notification sent: " + title + ", " + body);
        notificationManager.notify(0, notification);
    }

    private void scheduleNextNotification(Context context) {
        OneTimeWorkRequest nextWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(context).enqueue(nextWorkRequest);
    }



}


