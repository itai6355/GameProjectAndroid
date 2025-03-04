package com.example.gameproject.gamestates.shop;

import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.ui.GameImages;

public class ShopSloth extends InventorySloth {

    private final boolean bought = false;
    private int slothType = 1;

    public ShopSloth(int xSpot, int ySpot, int x, int y) {
        super(xSpot, ySpot, x, y);
    }
    public ShopSloth(int xSpot, int ySpot, int x, int y, int type) {
        super(xSpot, ySpot, x, y);
        slothType = type;
    }



    public ShopImages getSlothImage() {
        return switch (slothType) {
            case 2 -> ShopImages.SHOP_SLOTH_2;
            case 3 -> ShopImages.SHOP_SLOTH_3;
            default -> ShopImages.SHOP_SLOTH_1;
        };
    }

    public boolean isBought() {
        return bought;
    }

    public int getSlothType() {
        return slothType;
    }
}
