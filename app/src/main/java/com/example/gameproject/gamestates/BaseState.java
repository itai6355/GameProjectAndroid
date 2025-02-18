package com.example.gameproject.gamestates;

import android.view.MotionEvent;

import com.example.gameproject.main.Game;
import com.example.gameproject.ui.CustomButton;


public abstract class BaseState {
    protected Game game;

    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }
}
