package com.example.gameproject.gamestates.shop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;

public class CategoryPage implements GameStateInterface {
    private final int MAX_PAGES;
    private final int ShopWidth = 10;
    private final int ShopHeight = 4;
    private final ItemShop.Category thiscategory;
    private final ShopSloth[][][] ShopItems;
    private int page;
    private final int xCurr;
    private final int yCurr;
    private final int Xspace = 13 * GameConstants.Sprite.SCALE_MULTIPLIER;
    private final int Yspace = 100;
    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    private Bitmap icon;

    public CategoryPage(ItemShop.Category category, int xCurr, int yCurr) {
        this.xCurr = xCurr;
        this.yCurr = yCurr;
        MAX_PAGES = (int) Math.ceil((double) category.getItems().length / (ShopWidth * ShopHeight));
        ShopItems = new ShopSloth[MAX_PAGES][ShopWidth][ShopHeight];
        thiscategory = category;
        page = 0;
        initPages();
    }


    public void initPages() {
        int index = 0;
        for (int i = 0; i < MAX_PAGES; i++)
            for (int j = 0; j < ShopWidth; j++)
                for (int k = 0; k < ShopHeight; k++) {
                    ShopItems[i][j][k] = new ShopSloth(j, k, xCurr + (j * (ShopSloth.SLOT_SIZE + Xspace)), yCurr + (k * (ShopSloth.SLOT_SIZE + Yspace)));
                    if (index < thiscategory.getItems().length) {
                        ShopItems[i][j][k].setItem(Items.valueOf(thiscategory.getItems()[index].name()));
                        index++;
                    }
                }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        try {
            for (ShopSloth[] shopSloths : ShopItems[page])
                for (ShopSloth slot : shopSloths)
                    if (slot != null) {
                        canvas.drawBitmap(slot.getSlothImage().getImage(), slot.getX(), slot.getY(), null);
                        if (slot.getAmount() > 0) drawItem(canvas, slot);
                    }

            canvas.drawBitmap(ShopImages.SHOP_INVENTORY_MOUSE.getImage(), ShopItems[page][xCurrIndex][yCurrIndex].getX() + GameConstants.Sprite.SCALE_MULTIPLIER, ShopItems[page][xCurrIndex][yCurrIndex].getY() + GameConstants.Sprite.SCALE_MULTIPLIER, null);
        } catch (Exception e) {
            // somtimes when you move from 2 pages to 1 page it can throw an ArrayIndexOutOfBoundsException
        }
    }

    @Override
    public void touchEvents(MotionEvent event) {

    }

    private void drawItem(Canvas canvas, ShopSloth ss) {
        Items itemType = ss.getItem();
        int imageX = (int) (ss.getX() + (float) ss.getSlothImage().getImage().getWidth() / 2 - (float) itemType.getImage().getWidth() / 2);
        int imageY = (int) (ss.getY() + (float) ss.getSlothImage().getImage().getHeight() / 2 - (float) itemType.getImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getImage(), imageX, imageY, null);
    }


    public ShopSloth[][][] getShopItems() {
        return ShopItems;
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

    public Bitmap getIcon() {
        return icon;
    }


    public void setIcon() {
        switch (thiscategory) {
            case BASIC -> icon = Items.SLICED_BREAD_P.getSmallestImage();
            case FRUIT -> icon = Items.RED_APPLE.getSmallestImage();
            case FOOD -> icon = Items.HAMBURGER.getSmallestImage();
            case MEAT -> icon = Items.RED_APPLE_P.getSmallestImage();
            case SNACKS -> icon = Items.POTATOCHIP_BLUE.getSmallestImage();
            case CAKE -> icon = Items.TIRAMISU.getSmallestImage();
            case BAKERY_TOOLS -> icon = Items.SPATULA.getSmallestImage();
        }
    }

    public void setXCurr(int xCurrIndex) {
        this.xCurrIndex = xCurrIndex;
    }

    public void setYCurr(int yCurrIndex) {
        this.yCurrIndex = yCurrIndex;
    }
}
