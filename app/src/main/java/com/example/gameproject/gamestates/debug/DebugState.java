package com.example.gameproject.gamestates.debug;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.ItemHelper;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;

public class DebugState extends BaseState implements GameStateInterface {

    DatabaseHelper dbHelper = MainActivity.getDbHelper();

    public DebugState(Game game) {
        super(game);
    }

    @Override
    public void update(double delta) {
        game.setCurrentGameState(Game.GameState.DEATH_SCREEN);
        ItemHelper.PrintAll();
    }

    @Override
    public void render(Canvas canvas) {

    }

    @Override
    public void touchEvents(MotionEvent event) {

    }

    public static void getBug(Exception e){

    }
}
