package com.example.gameproject.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.gameproject.gamestates.DeathScreen;
import com.example.gameproject.gamestates.Menu;
import com.example.gameproject.gamestates.Playing;
import com.example.gameproject.gamestates.Setting;

public class Game {

    private SurfaceHolder holder;

    private Menu menu;
    private Playing playing;
    private Setting setting;
    private DeathScreen deathScreen;

    private GameLoop gameLoop;
    private GameState currentGameState = GameState.MENU;

    public Game(SurfaceHolder holder) {
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();
    }

    public void update(double delta) {
        switch (currentGameState) {
            case MENU -> menu.update(delta);
            case PLAYING -> playing.update(delta);
            case DEATH_SCREEN -> deathScreen.update(delta);
            case SETTINGS -> setting.update(delta);
        }
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        switch (currentGameState) {
            case MENU -> menu.render(canvas);
            case PLAYING -> playing.render(canvas);
            case DEATH_SCREEN -> deathScreen.render(canvas);
            case SETTINGS -> setting.render(canvas);
        }

        holder.unlockCanvasAndPost(canvas);
    }

    private void initGameStates() {
        menu = new Menu(this);
        playing = new Playing(this);
        deathScreen = new DeathScreen(this);
        setting = new Setting(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case MENU -> menu.touchEvents(event);
            case PLAYING -> playing.touchEvents(event);
            case DEATH_SCREEN -> deathScreen.touchEvents(event);
            case SETTINGS -> setting.touchEvents(event);
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState {
        MENU, PLAYING, DEATH_SCREEN, SETTINGS;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public DeathScreen getDeathScreen() {
        return deathScreen;
    }
}
