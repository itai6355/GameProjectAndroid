package com.example.gameproject.entities.objects;

import android.graphics.PointF;

import com.example.gameproject.entities.Entity;

public class Building extends Entity {

    private final Buildings buildingType;

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

}
