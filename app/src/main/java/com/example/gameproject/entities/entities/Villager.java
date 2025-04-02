package com.example.gameproject.entities.entities;

import static com.example.gameproject.entities.entities.GameCharacters.VILLAGER_DAD;

import android.graphics.PointF;


public class Villager extends Character {


    //TODO: add villager to the MapManager.
    public Villager(PointF pos, VillagerType villagerType) {
        super(pos, getCharacterType(villagerType));
    }

    private static GameCharacters getCharacterType(VillagerType villagerType) {
        return switch (villagerType) {
            case VILLAGER_DAD -> VILLAGER_DAD;
            case VILLAGER_MOM -> GameCharacters.VILLAGER_MOM;
            case VILLAGER_BOY -> GameCharacters.VILLAGER_BOY;
            case VILLAGER_GREEN -> GameCharacters.VILLAGER_GREEN;
            case VILLAGER_BLACK -> GameCharacters.VILLAGER_BLACK;
            case VILLAGER_OLIVE -> GameCharacters.VILLAGER_OLIVE;
        };
    }


    public enum VillagerType {
        VILLAGER_DAD,
        VILLAGER_MOM,
        VILLAGER_BOY,
        VILLAGER_GREEN,
        VILLAGER_BLACK,
        VILLAGER_OLIVE;
    }
}
