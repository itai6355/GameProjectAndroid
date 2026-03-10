package com.example.gameproject.gamestates.shop;

import com.example.gameproject.gamestates.invenory.InventorySloth;

public class ShopSloth extends InventorySloth {

    private int slothType = 1;
    private int customSpriteId = -1;

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

    public void setSlothType(int slothType) {
        this.slothType = slothType;
    }

    public void setCustomSpriteId(int id) {
        this.customSpriteId = id;
    }

    public int getCustomSpriteId() {
        return customSpriteId;
    }

    public boolean hasCustomSprite() {
        return customSpriteId != -1;
    }

    public boolean hasItem() {
        return super.getItem() != null || hasCustomSprite();
    }
}