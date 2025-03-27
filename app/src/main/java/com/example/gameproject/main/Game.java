package com.example.gameproject.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.environments.MapManager;
import com.example.gameproject.gamestates.death.DeathScreen;
import com.example.gameproject.gamestates.debug.DebugState;
import com.example.gameproject.gamestates.invenory.InventoryState;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.gamestates.setting.Setting;
import com.example.gameproject.gamestates.shop.ShopState;

public class Game {

    private final SurfaceHolder holder;
    private final DatabaseHelper dbHelper;

    private Playing playing;
    private Setting setting;
    private DeathScreen deathScreen;
    private InventoryState inventoryState;
    private ShopState shopState;
    private DebugState debugState;

    private final GameLoop gameLoop;
    private GameState currentGameState = GameState.PLAYING;

    public Game(SurfaceHolder holder) {
        this.holder = holder;
        gameLoop = new GameLoop(this);
        dbHelper = MainActivity.getDbHelper();

        initGameStates();
    }

    public void update(double delta) {
        switch (currentGameState) {
            case PLAYING -> playing.update(delta);
            case DEATH_SCREEN -> deathScreen.update(delta);
            case SETTINGS -> setting.update(delta);
            case INVENTORY -> inventoryState.update(delta);
            case SHOP -> shopState.update(delta);
            case DEBUG -> debugState.update(delta);
        }
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;
        canvas.drawColor(Color.BLACK);

        switch (currentGameState) {
            case PLAYING -> playing.render(canvas);
            case DEATH_SCREEN -> deathScreen.render(canvas);
            case SETTINGS -> setting.render(canvas);
            case INVENTORY -> inventoryState.render(canvas);
            case SHOP -> shopState.render(canvas);
            case DEBUG -> debugState.render(canvas);
        }

        holder.unlockCanvasAndPost(canvas);
    }


    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case PLAYING -> playing.touchEvents(event);
            case DEATH_SCREEN -> deathScreen.touchEvents(event);
            case SETTINGS -> setting.touchEvents(event);
            case INVENTORY -> inventoryState.touchEvents(event);
            case SHOP -> shopState.touchEvents(event);
            case DEBUG -> debugState.touchEvents(event);
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public void setCurrentGameState(GameState gameState) {
        this.currentGameState = gameState;
    }

    public InventoryState getInventoryState() {
        return inventoryState;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Player getPlayer() {
        return playing.getPlayer();
    }

    private void initGameStates() {
        deathScreen = new DeathScreen(this);
        setting = new Setting(this);
        inventoryState = new InventoryState(this);
        shopState = new ShopState(this);
        debugState = new DebugState(this);
        playing = new Playing(this);
    }

    public enum GameState {
        PLAYING, DEATH_SCREEN, SETTINGS, INVENTORY, SHOP, DEBUG
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }
}
