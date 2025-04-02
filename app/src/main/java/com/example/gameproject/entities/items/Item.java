package com.example.gameproject.entities.items;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.helpers.GameConstants;

public class Item extends Entity {


    private final int MAX_TICK_HOLD = 10;
    private final Items itemType;
    int aniIndex = 0;
    private int tickHold = 0;

    public Item(Items itemType, PointF pos) {
        super(pos, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE);
        this.itemType = itemType;
    }

    public boolean updatePickUp() {
        if (tickHold < MAX_TICK_HOLD) tickHold++;
        else return true;
        return false;
    }

    public Items getItemType() {
        return itemType;
    }

    @Override
    public RectF getHitbox() {
        return super.getHitbox();
    }

    public void updateAni() {
        if (aniIndex >= itemType.getAmount() - 1)
            aniIndex = 0;
        else aniIndex++;
    }

    public int getAniIndex() {
        return aniIndex;
    }


}
