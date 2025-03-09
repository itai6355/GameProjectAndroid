package com.example.gameproject.gamestates.shop;

import com.example.gameproject.gamestates.invenory.InventorySloth;

public class ShopSloth extends InventorySloth {

    private final boolean bought = false;
    private int slothType = 1;

    public ShopSloth(int xSpot, int ySpot, int x, int y) {
        super(xSpot, ySpot, x, y);
    }


    public ShopImages getSlothImage() {
        return switch (slothType) {
            case 2 -> ShopImages.SHOP_SLOTH_2;
            case 3 -> ShopImages.SHOP_SLOTH_3;
            default -> ShopImages.SHOP_SLOTH_1;
        };
    }

    public int getSlothType() {
        return slothType;
    }

    public boolean hasItem() {
        return super.getItem() != null;
    }
}
