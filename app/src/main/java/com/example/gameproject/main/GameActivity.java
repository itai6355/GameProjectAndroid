package com.example.gameproject.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameproject.Media.MPHelper;

public class GameActivity extends AppCompatActivity {


    private static final boolean DrawHitbox = false;
    private static MPHelper mpHelper;

    private static String username;
    private static String password;

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

    public static MPHelper getMpHelper() {
        return mpHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        setContentView(new GamePanel(this));
        mpHelper = new MPHelper(this);
        mpHelper.initializeMediaPlayerAsync(() -> mpHelper.play());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mpHelper.stop();
    }
}