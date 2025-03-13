package com.example.gameproject.entities.items;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.helpers.GameConstants;

public class Item extends Entity {


    private final Items itemType;
    int aniIndex = 0;

    public Item(Items itemType, PointF pos) {
        super(pos, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE);
        this.itemType = itemType;
    }

    public Items getItemType() {
        return itemType;
    }

    @Override
    public RectF getHitbox() {
        return super.getHitbox();
    }

    public int getAniIndex(Items itemType) {
        if (aniIndex >= itemType.getAmount() - 1)
            aniIndex = 0;
        else aniIndex++;
        return aniIndex;
    }


}
