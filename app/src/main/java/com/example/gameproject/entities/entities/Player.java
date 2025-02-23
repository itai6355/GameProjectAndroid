package com.example.gameproject.entities.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.ui.GameImages;

import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Character {

    private final CopyOnWriteArrayList<Item> inventory = new CopyOnWriteArrayList<>();

    public Bitmap icon = GameImages.BOY_ICON.getImage();
    public Player() {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.PLAYER);
        setStartHealth(600);
    }

    public void update(double delta, boolean movePlayer) {
        if (movePlayer)
            updateAnimation();
        updateWepHitbox();
    }

    public Bitmap getIcon() {
        return icon;
    }

    public CopyOnWriteArrayList<Item> getInventory() {
        return inventory;
    }
}