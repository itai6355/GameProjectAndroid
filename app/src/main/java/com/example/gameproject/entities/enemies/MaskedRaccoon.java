package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

import java.util.ArrayList;
import java.util.Random;

public class MaskedRaccoon extends Enemy {

    private long lastDirChange = System.currentTimeMillis();
    private Random rand = new Random();


    public MaskedRaccoon(PointF pos) {
        super(pos, Enemies.MASKED_RAKKON);
        setStartHealth(50);
        AddLoot(getKilledLoot());
    }

    public void update(double delta, GameMap gameMap) {
        updateMove(delta, gameMap);
        updateAnimation();

    }

    private void updateMove(double delta, GameMap gameMap) {

        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDir = rand.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }

        float deltaChange = (float) (delta * 300);

        switch (faceDir) {
            case GameConstants.Face_Dir.DOWN:
                if (HelpMethods.CanWalkHere(hitbox, 0, deltaChange, gameMap)) {
                    hitbox.top += deltaChange;
                    hitbox.bottom += deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.UP;
                break;

            case GameConstants.Face_Dir.UP:
                if (HelpMethods.CanWalkHere(hitbox, 0, -deltaChange, gameMap)) {
                    hitbox.top -= deltaChange;
                    hitbox.bottom -= deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.DOWN;
                break;

            case GameConstants.Face_Dir.RIGHT:
                if (HelpMethods.CanWalkHere(hitbox, deltaChange, 0, gameMap)) {
                    hitbox.left += deltaChange;
                    hitbox.right += deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.LEFT;
                break;

            case GameConstants.Face_Dir.LEFT:
                if (HelpMethods.CanWalkHere(hitbox, -deltaChange, 0, gameMap)) {
                    hitbox.left -= deltaChange;
                    hitbox.right -= deltaChange;
                } else
                    faceDir = GameConstants.Face_Dir.RIGHT;
                break;
        }
    }


    @Override
    public void AddLoot(ArrayList<Item> KilledLoot) {
        KilledLoot.add(new Item(Items.COIN, new PointF(this.hitbox.left, this.hitbox.top)));
        KilledLoot.add(new Item(Items.COIN, new PointF(this.hitbox.left, this.hitbox.top)));
        KilledLoot.add(new Item(Items.COIN, new PointF(this.hitbox.left, this.hitbox.top)));
        KilledLoot.add(new Item(Items.COIN, new PointF(this.hitbox.left, this.hitbox.top)));
    }
}
