package com.example.gameproject.main;

public class GameLoop implements Runnable {

    private final Thread gameThread;
    private final Game game;

    private static final int TARGET_UPDATE_FPS = 10;
    private static final int TARGET_RENDER_FPS = 120;
    private static final long OPTIMAL_UPDATE_TIME = 1000000000 / TARGET_UPDATE_FPS;
    private static final long OPTIMAL_RENDER_TIME = 1000000000 / TARGET_RENDER_FPS;

    public GameLoop(Game game) {
        this.game = game;
        gameThread = new Thread(this);
    }

    @Override
    public void run() {

        long lastUpdateTime = System.nanoTime();
        long lastRenderTime = System.nanoTime();

        long lastFPScheck = System.currentTimeMillis();
        int fps = 0;

        while (true) {
            long now = System.nanoTime();

            if (now - lastUpdateTime >= OPTIMAL_UPDATE_TIME) {
                double delta = (now - lastUpdateTime) / 1000000000.0;  // Delta for update

                game.update(delta);
                lastUpdateTime = now;

                fps++;
            }

            if (now - lastRenderTime >= OPTIMAL_RENDER_TIME) {
                game.render();
                lastRenderTime = now;
            }

            if (System.currentTimeMillis() - lastFPScheck >= 1000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                lastFPScheck += 1000;
            }
        }
    }

    public void startGameLoop() {
        gameThread.start();
    }
}
