package com.example.gameproject.gamestates.shop.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.shop.ShopImages;
import com.example.gameproject.gamestates.shop.ShopSloth;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.helpers.var.GameConstants;

public class TileCategoryPage implements GameStateInterface {

    private final int ShopWidth = 10;
    private final int ShopHeight = 4;

    private final int MAX_PAGES;

    private final int[] tiles;

    private final ShopSloth[][][] shopSlots;

    private final int xCurr;
    private final int yCurr;

    private int page;

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;

    private final int Xspace = 13 * GameConstants.Sprite.SCALE_MULTIPLIER;
    private final int Yspace = 100;

    public TileCategoryPage(int[] tiles, int xCurr, int yCurr) {

        this.tiles = tiles;

        this.xCurr = xCurr;
        this.yCurr = yCurr;

        MAX_PAGES = (int) Math.ceil((double) tiles.length / (ShopWidth * ShopHeight));

        shopSlots = new ShopSloth[MAX_PAGES][ShopWidth][ShopHeight];

        page = 0;

        initPages();
    }

    private void initPages() {
        int index = 0;
        for (int p = 0; p < MAX_PAGES; p++)
            for (int i = 0; i < ShopWidth; i++)
                for (int j = 0; j < ShopHeight; j++) {
                    shopSlots[p][i][j] = new ShopSloth(i, j, xCurr + (i * (ShopSloth.SLOT_SIZE + Xspace)), yCurr + (j * (ShopSloth.SLOT_SIZE + Yspace)));
                    if (index < tiles.length) {
                        shopSlots[p][i][j].setCustomSpriteId(tiles[index]);
                        shopSlots[p][i][j].setItem(Items.TILE); // needed so BuyPage knows a tile is here
                        shopSlots[p][i][j].setAmount(1);
                        index++;
                    }
                }
    }

    @Override
    public void update(double delta) {}

    @Override
    public void render(Canvas canvas) {
        try {
            for (ShopSloth[] row : shopSlots[page])
                for (ShopSloth slot : row)
                    if (slot != null) {
                        canvas.drawBitmap(slot.getSlothImage().getImage(), slot.getX(), slot.getY(), null);
                        if (slot.getCustomSpriteId() != -1) drawTile(canvas, slot);
                    }
            canvas.drawBitmap(ShopImages.SHOP_INVENTORY_MOUSE.getImage(), shopSlots[page][xCurrIndex][yCurrIndex].getX() + GameConstants.Sprite.SCALE_MULTIPLIER, shopSlots[page][xCurrIndex][yCurrIndex].getY() + GameConstants.Sprite.SCALE_MULTIPLIER, null);
        } catch (Exception ignored) {}

    }

    private void drawTile(Canvas canvas, ShopSloth slot) {
        int spriteId = slot.getCustomSpriteId();
        Bitmap sprite = Items.getTileSprite(spriteId);
        int imageX = (int) (slot.getX() + slot.getSlothImage().getImage().getWidth() / 2f - sprite.getWidth() / 2f);
        int imageY = (int) (slot.getY() + slot.getSlothImage().getImage().getHeight() / 2f - sprite.getHeight() / 2f);
        canvas.drawBitmap(sprite, imageX, imageY, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {

    }

    public ShopSloth[][][] getShopSlots() {
        return shopSlots;
    }

    public int getCurrPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMAX_PAGES() {
        return MAX_PAGES;
    }

    public void setXCurr(int x) {
        this.xCurrIndex = x;
    }

    public void setYCurr(int y) {
        this.yCurrIndex = y;
    }
}