package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

import java.util.Random;

public class Skeleton extends AttackingEnemy {

    public Skeleton(PointF pos) {
        super(pos, Enemies.SKELETON, Weapons.BIG_SWORD);
        setStartHealth(100);
        AddLootTypes();
    }

    @Override
    public void AddLootTypes() {
        if (new Random().nextInt(3) == 0)
            KilledLoot.add(Items.MEDIPCK);
    }
}
