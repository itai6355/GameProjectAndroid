package com.example.gameproject.notification;

import android.content.Context;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;


public class NotificationScheduler {

    public static void scheduleNotification(Context context, long time,TimeUnit timeUnit) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest
                .Builder(NotificationWorker.class)
                .setInitialDelay(time, timeUnit)
                .build();
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}

