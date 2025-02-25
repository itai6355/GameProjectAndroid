package com.example.gameproject.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.gameproject.gamestates.DeathScreen;
import com.example.gameproject.gamestates.InventoryState;
import com.example.gameproject.gamestates.Playing;
import com.example.gameproject.gamestates.Setting;

public class Game {

    private SurfaceHolder holder;

    private Playing playing;
    private Setting setting;
    private DeathScreen deathScreen;
    private InventoryState inventoryState;

    private GameLoop gameLoop;
    private GameState currentGameState = GameState.PLAYING;

    public Game(SurfaceHolder holder) {
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();
    }

    public void update(double delta) {
        switch (currentGameState) {
            case PLAYING -> playing.update(delta);
            case DEATH_SCREEN -> deathScreen.update(delta);
            case SETTINGS -> setting.update(delta);
            case INVENTORY -> inventoryState.update(delta);
        }
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        switch (currentGameState) {
            case PLAYING -> playing.render(canvas);
            case DEATH_SCREEN -> deathScreen.render(canvas);
            case SETTINGS -> setting.render(canvas);
            case INVENTORY -> inventoryState.render(canvas);
        }

        holder.unlockCanvasAndPost(canvas);
    }

    private void initGameStates() {
        playing = new Playing(this);
        deathScreen = new DeathScreen(this);
        setting = new Setting(this);
        inventoryState = new InventoryState(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case PLAYING -> playing.touchEvents(event);
            case DEATH_SCREEN -> deathScreen.touchEvents(event);
            case SETTINGS -> setting.touchEvents(event);
            case INVENTORY -> inventoryState.touchEvents(event);
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState {
        PLAYING, DEATH_SCREEN, SETTINGS, INVENTORY;
    }

    public void setCurrentGameState(GameState gameState) {
        this.currentGameState = gameState;
    }

}
