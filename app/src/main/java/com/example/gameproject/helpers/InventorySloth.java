package com.example.gameproject.helpers;

import static com.example.gameproject.ui.GameImages.INVENTORY_SLOTH;

import android.view.MotionEvent;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.ui.GameImages;

public class InventorySloth {
    public static final int SLOT_SIZE = INVENTORY_SLOTH.getImage().getWidth();
    private Item item;
    private int amount;
    int xSpot, ySpot;
    int x, y;

    public InventorySloth(int xSpot, int ySpot,int x, int y) {
        this.xSpot = xSpot;
        this.ySpot = ySpot;
        this.x = x;
        this.y = y;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public GameImages getImage() {
        return INVENTORY_SLOTH;
    }

    public int getxSpot() {
        return xSpot;
    }

    public int getySpot() {
        return ySpot;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isIn(MotionEvent event) {
        return event.getX() >= x && event.getX() <= x + SLOT_SIZE && event.getY() >= y && event.getY() <= y + SLOT_SIZE;
    }
}
