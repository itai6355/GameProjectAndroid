package com.example.gameproject.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

public class Player extends Character {


    public Player() {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.PLAYER);
        setStartHealth(600);
    }

    public void update(double delta, boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }


}