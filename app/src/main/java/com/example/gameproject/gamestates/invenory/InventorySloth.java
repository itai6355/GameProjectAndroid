package com.example.gameproject.gamestates.invenory;

import static com.example.gameproject.ui.GameImages.INVENTORY_SLOTH;

import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class InventorySloth extends CustomButton {
    public static final int SLOT_SIZE = INVENTORY_SLOTH.getImage().getWidth() / 4 * 3;
    int xSpot, ySpot;
    int x, y;
    private Items item;
    private int amount = 0;

    public InventorySloth(int xSpot, int ySpot, int x, int y) {
        super(x, y, SLOT_SIZE, SLOT_SIZE);
        this.xSpot = xSpot;
        this.ySpot = ySpot;
        this.x = x;
        this.y = y;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        if (this.item == null) {
            this.item = item;
            amount = 1;
        }else if (item == null){
            this.item = null;
            amount = 0;
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int i) {
        amount = i;
    }

    public GameImages getImage() {
        return INVENTORY_SLOTH;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isIn(MotionEvent event) {
        return event.getX() >= getX() && event.getX() <= getX() + SLOT_SIZE &&
                event.getY() >= getY() && event.getY() <= getY() + SLOT_SIZE;
    }


    public void addAmount() {
        amount++;
    }

    public void reduceAmount() {
        if (amount > 0) {
            amount--;
        } else {
            item = null;
            amount = 0;
        }
    }

}
