package com.example.gameproject.entities.objects;

import android.graphics.PointF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Villager;

public class Building extends Entity {

    private final Buildings buildingType;
    private Villager villager1;
    private Villager villager2;
    private Villager villager3;

    public Building(PointF pos, Buildings buildingType) {
        super(new PointF(pos.x, pos.y + buildingType.hitboxRoof),
                buildingType.hitboxWidth,
                buildingType.hitboxHeight
        );
        this.buildingType = buildingType;
    }

    public Buildings getBuildingType() {
        return buildingType;
    }

    public PointF getPos() {
        return new PointF(hitbox.left, hitbox.top);
    }

    public void addVillager(Villager villager) {
        if (villager1 == null) villager1 = villager;
        else if (villager2 == null) villager2 = villager;
        else if (villager3 == null) villager3 = villager;
    }
    public Villager[] getVillagers() {
        return new Villager[]{villager1, villager2, villager3};
    }

    public void removeVillager(Villager villager) {
        if (villager1 == villager) {
            villager1 = null;
        } else if (villager2 == villager) {
            villager2 = null;
        } else if (villager3 == villager) {
            villager3 = null;
        }
    }
}
