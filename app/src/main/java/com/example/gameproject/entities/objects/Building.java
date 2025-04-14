package com.example.gameproject.entities.objects;

import android.graphics.PointF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Villager;

public class Building extends Entity {

    private final Buildings buildingType;
    private Villager[] villagers;

    int villagerAmount;

    public Building(PointF pos, Buildings buildingType,int villagerAmount) {
        super(new PointF(pos.x, pos.y + buildingType.hitboxRoof),
                buildingType.hitboxWidth,
                buildingType.hitboxHeight

        );
        this.villagerAmount = villagerAmount;
        villagers = new Villager[villagerAmount];
        this.buildingType = buildingType;
    }

    public Buildings getBuildingType() {
        return buildingType;
    }

    public PointF getPos() {
        return new PointF(hitbox.left, hitbox.top);
    }

    public void addVillager(Villager villager) {
        for (int i = 0; i < villagers.length; i++)
            if (villagers[i] == null) {
                villagers[i] = villager;
                return;
            }

    }
    public Villager[] getVillagers() {
        return villagers;
    }

    public void removeVillager(Villager villager) {
        for (int i = 0; i < villagers.length; i++) {
            if (villagers[i] == villager) {
                villagers[i] = null;
                return;
            }
        }
    }

    public int getVillagerAmount() {
        return villagerAmount;
    }
}
