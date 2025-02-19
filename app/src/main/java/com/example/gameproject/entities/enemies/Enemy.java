package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.items.Item;

import java.util.ArrayList;

public abstract class Enemy extends Character {

    private final ArrayList<Item> KilledLoot;

    public Enemy(PointF pos, Enemies EnemyType) {
        super(pos, EnemyType);
        KilledLoot = new ArrayList<>();
    }

    protected abstract void AddLoot(ArrayList<Item> KilledLoot);

    public ArrayList<Item> getKilledLoot() {
        return KilledLoot;
    }
}
