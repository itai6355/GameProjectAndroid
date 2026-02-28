package com.example.gameproject.entities.objects;

import android.graphics.PointF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.environments.Doorway;
import com.example.gameproject.environments.GameMap;

public class Building extends Entity {

    private final Buildings buildingType;
    private Villager[] villagers;
    private int villagerAmount;

    private final GameMap insideMap;
    private final Doorway outsideDoorway;
    private final Doorway insideDoorway;

    public Building(PointF pos, Buildings buildingType, int villagerAmount, GameMap outsideMap) {
        super(
                new PointF(pos.x, pos.y + buildingType.hitboxRoof),
                buildingType.hitboxWidth,
                buildingType.hitboxHeight
        );
        this.villagerAmount = villagerAmount;
        this.villagers = new Villager[villagerAmount];
        this.buildingType = buildingType;

        this.insideMap = buildingType.createInsideMap();

        PointF outsideDoorPos = new PointF(
                pos.x + buildingType.getDoorwayPoint().x,
                pos.y + buildingType.getDoorwayPoint().y
        );
        this.outsideDoorway = new Doorway(outsideDoorPos, outsideMap);

        PointF insideDoorPos = buildingType.getInsideDoorwayPoint();
        this.insideDoorway = new Doorway(insideDoorPos, insideMap);

        outsideDoorway.connectDoorway(insideDoorway);
        insideDoorway.connectDoorway(outsideDoorway);
    }


    public Buildings getBuildingType() {
        return buildingType;
    }

    public PointF getPos() {
        return new PointF(hitbox.left, hitbox.top);
    }

    public GameMap getInsideMap() {
        return insideMap;
    }

    public Doorway getOutsideDoorway() {
        return outsideDoorway;
    }

    public Doorway getInsideDoorway() {
        return insideDoorway;
    }


    public void addVillager(Villager villager) {
        for (int i = 0; i < villagers.length; i++) {
            if (villagers[i] == null) {
                villagers[i] = villager;
                return;
            }
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