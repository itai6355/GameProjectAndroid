package com.example.gameproject.entities.items;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.entities.Entity;

public class Item extends Entity {


    private final Items itemType;

    public Item(Items itemType, PointF pos) {
        super(pos, itemType.getWidth(), itemType.getHeight());
        this.itemType = itemType;
    }

    public Items getItemType() {
        return itemType;
    }

    @Override
    public RectF getHitbox() {
        return super.getHitbox();
    }
}
