package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

import java.util.Random;

public class Skeleton extends Enemy {
    private final Random rand = new Random();
    private final long timeToAttack = 500;
    private final long timeForAttackDuration = 250;
    private long lastDirChange = System.currentTimeMillis();
    private boolean moving = true, preparingAttack;
    private long timerBeforeAttack, timerAttackDuration;


    public Skeleton(PointF pos) {
        super(pos, Enemies.SKELETON);
        setStartHealth(100);
        AddLootTypes();
    }

    public void update(double delta, GameMap gameMap) {
        if (moving) {
            updateMove(delta, gameMap);
            updateAnimation();
        }
        if (preparingAttack) {
            checkTimeToAttackTimer();
        }
        if (attacking) {
            updateAttackTimer();
        }
    }


    public void prepareAttack(Player player, float cameraX, float cameraY) {
        timerBeforeAttack = System.currentTimeMillis();
        preparingAttack = true;
        moving = false;
        turnTowardsPlayer(player, cameraX, cameraY);
    }

    private void turnTowardsPlayer(Player player, float cameraX, float cameraY) {
        float xDelta = hitbox.left - (player.getHitbox().left - cameraX);
        float yDelta = hitbox.top - (player.getHitbox().top - cameraY);

        if (Math.abs(xDelta) > Math.abs(yDelta)) {
            if (hitbox.left < (player.getHitbox().left - cameraX))
                faceDir = GameConstants.Face_Dir.RIGHT;
            else faceDir = GameConstants.Face_Dir.LEFT;
        } else {
            if (hitbox.top < (player.getHitbox().top - cameraY))
                faceDir = GameConstants.Face_Dir.DOWN;
            else faceDir = GameConstants.Face_Dir.UP;
        }
    }

    private void updateAttackTimer() {
        if (timerAttackDuration + timeForAttackDuration < System.currentTimeMillis()) {
            setAttacking(false);
            resetAnimation();
            moving = true;
        }
    }

    private void checkTimeToAttackTimer() {
        if (timerBeforeAttack + timeToAttack < System.currentTimeMillis()) {
            setAttacking(true);
            preparingAttack = false;
            timerAttackDuration = System.currentTimeMillis();
        }
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

    public boolean isPreparingAttack() {
        return preparingAttack;
    }

    @Override
    public void AddLootTypes() {
        if (new Random().nextInt(3) == 0)
            KilledLoot.add(Items.MEDIPCK);
    }
}
