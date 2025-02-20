package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;

import java.util.ArrayList;

public abstract class Enemy extends Character {

    protected final ArrayList<Items> KilledLoot;
    protected ArrayList<Item> Loot;
    protected boolean addedLoot = false;

    public Enemy(PointF pos, Enemies EnemyType) {
        super(pos, EnemyType);
        KilledLoot = new ArrayList<>();
    }

    protected abstract void AddLootTypes();

    public ArrayList<Item> getLoot(PointF deathPos) {
        Loot = new ArrayList<>();
        for (Items item : KilledLoot)
            Loot.add(new Item(item, deathPos));
        return Loot;
    }

    public boolean isAddedLoot() {
        return addedLoot;
    }

    public void setAddedLoot(boolean addedLoot) {
        this.addedLoot = addedLoot;
    }
}
