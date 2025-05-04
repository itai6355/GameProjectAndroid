package com.example.gameproject.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import com.example.gameproject.Media.MediaPlayerHelper;
import com.example.gameproject.notification.NotificationScheduler;
import com.example.gameproject.notification.NotificationWorker;

import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "game_notifications";

    private static final boolean DrawHitbox = false;
    private static MediaPlayerHelper mediaPlayerHelper;

    private static String username;
    private static String password;
    private static GamePanel panel;

    public static boolean isDrawHitbox() {
        return DrawHitbox;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }

    public static boolean isDev() {
        return username.equals("admin") && password.equals("admin");
    }

    public static MediaPlayerHelper getMpHelper() {
        return mediaPlayerHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cancelNotificationChannel();
        cancelScheduledNotifications();
        cancelNotificationChannel();
        cancelScheduledNotifications();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        panel = new GamePanel(this);
        setContentView(panel);
        mediaPlayerHelper = new MediaPlayerHelper(this);
        mediaPlayerHelper.initializeMediaPlayerAsync(() -> mediaPlayerHelper.play());
        mediaPlayerHelper.setVolume(getIntent().getIntExtra("left",0), getIntent().getIntExtra("right",0));
    }

    private void cancelScheduledNotifications() {
        WorkManager.getInstance(this).cancelAllWorkByTag(NotificationWorker.class.getSimpleName());
    }

    private void cancelNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.deleteNotificationChannel(CHANNEL_ID);
    }

    private void createNotificationChannel() {
        CharSequence name = "Game Notifications";
        String description = "Notifications for game events";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cancelScheduledNotifications();
        cancelNotificationChannel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayerHelper.stop();
        createNotificationChannel();
        NotificationScheduler.scheduleNotification(this, 1, TimeUnit.HOURS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayerHelper.stop();
    }

    public static GamePanel getPanel() {
        return panel;
    }
}