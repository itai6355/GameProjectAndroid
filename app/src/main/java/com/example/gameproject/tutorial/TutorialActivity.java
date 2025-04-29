package com.example.gameproject.tutorial;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {

    private TutorialView tutorialView;
    private Tutorialloop tutorialloop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tutorialView = new TutorialView(this);
        tutorialloop = new Tutorialloop(tutorialView);
        tutorialView.setLoop(tutorialloop);
        setContentView(tutorialView);
        tutorialloop.startSettingLoop();

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
        tutorialView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public static class Tutorialloop implements Runnable {
        private static final int TARGET_UPDATE_FPS = 1;
        private static final int TARGET_RENDER_FPS = 1;
        private static final long OPTIMAL_UPDATE_TIME = 1000000000 / TARGET_UPDATE_FPS;
        private static final long OPTIMAL_RENDER_TIME = 1000000000 / TARGET_RENDER_FPS;
        private final Thread gameThread;
        private final TutorialView tutorialView;
        private volatile boolean running = false;

        public Tutorialloop(TutorialView tutorialView) {
            gameThread = new Thread(this);
            this.tutorialView = tutorialView;
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

                    tutorialView.update(delta);
                    lastUpdateTime = now;

                    fps++;
                }

                if (now - lastRenderTime >= OPTIMAL_RENDER_TIME) {
                    tutorialView.render();

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