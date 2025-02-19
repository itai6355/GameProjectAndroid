package com.example.gameproject.entities.enemies;

import android.graphics.PointF;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

public class MaskedRaccoon extends Character {


    public MaskedRaccoon(PointF pos) {
        super(pos, GameCharacters.MASKED_RAKKON);
        setStartHealth(300);
    }

    public void update(double delta, GameMap gameMap) {

    }

    private void updateMove(double delta, GameMap gameMap) {

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


}
