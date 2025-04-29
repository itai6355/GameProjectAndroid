package com.example.gameproject.tutorial;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.GameCharacters;

public class PlayerHolder extends Character {
    public PlayerHolder(PointF pos) {
        super(pos, GameCharacters.BOY);
    }

    public double getSPEED() {
        return 1.0;
    }

    public void update(boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }

}
