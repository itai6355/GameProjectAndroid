package com.example.gameproject.gamestates.setting;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private SettingView settingView;
    private Settingloop settingloop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingView = new SettingView(this, null);
        settingloop = new Settingloop(settingView);
        settingView.setSettingloop(settingloop);
        setContentView(settingView);
        settingloop.startSettingLoop();

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        settingView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    static class Settingloop implements Runnable {
        private static final int TARGET_UPDATE_FPS = 10;
        private static final int TARGET_RENDER_FPS = 120;
        private static final long OPTIMAL_UPDATE_TIME = 1000000000 / TARGET_UPDATE_FPS;
        private static final long OPTIMAL_RENDER_TIME = 1000000000 / TARGET_RENDER_FPS;
        private final Thread gameThread;
        private final SettingView settingView;
        private volatile boolean running = false;

        public Settingloop(SettingView settingView) {
            gameThread = new Thread(this);
            this.settingView = settingView;
        }

        @Override
        public void run() {
            long lastUpdateTime = System.nanoTime();
            long lastRenderTime = System.nanoTime();

            long lastFPScheck = System.currentTimeMillis();
            int fps = 0;

            while (running) {
                long now = System.nanoTime();

                if (now - lastUpdateTime >= OPTIMAL_UPDATE_TIME) {
                    double delta = (now - lastUpdateTime) / 1000000000.0;

                    settingView.update(delta);
                    lastUpdateTime = now;

                    fps++;
                }

                if (now - lastRenderTime >= OPTIMAL_RENDER_TIME) {
                    settingView.render();

                    lastRenderTime = now;
                }

                if (System.currentTimeMillis() - lastFPScheck >= 1000) {
                    System.out.println("FPS: " + fps);
                    fps = 0;
                    lastFPScheck += 1000;
                }
            }
        }

        public void startSettingLoop() {
            running = true;
            if (!gameThread.isAlive()) {
                gameThread.start();
            }
        }

        public void stopSettingLoop() {
            running = false;
            try {
                gameThread.join(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}