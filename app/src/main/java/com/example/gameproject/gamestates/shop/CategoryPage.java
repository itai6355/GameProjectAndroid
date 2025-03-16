package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;

public class CategoryPage implements GameStateInterface {
    private int page;
    private final int MAX_PAGES = 2;

    private final int ShopWidth = 10;
    private final int ShopHeight = 4;

    private int xCurr = 450;
    private int yCurr = 200;
    private int Xspace = 50;
    private int Yspace = 100;

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;

    ItemShop.Category thiscategory;

    private final ShopSloth[][][] ShopItems = new ShopSloth[MAX_PAGES][ShopWidth][ShopHeight];

    public CategoryPage(ItemShop.Category category) {
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
        for (ShopSloth[] shopSloths : ShopItems[page])
            for (ShopSloth slot : shopSloths)
                canvas.drawBitmap(slot.getSlothImage().getImage(), slot.getX(), slot.getY(), null);

        for (ShopSloth[] SSs : ShopItems[page])
            for (ShopSloth SS : SSs)
                if (SS != null && SS.getAmount() > 0) drawItem(canvas, SS);

        canvas.drawBitmap(ShopImages.SHOP_INVENTORY_MOUSE.getImage(), ShopItems[page][xCurrIndex][yCurrIndex].getX() + GameConstants.Sprite.SCALE_MULTIPLIER, ShopItems[page][xCurrIndex][yCurrIndex].getY() + GameConstants.Sprite.SCALE_MULTIPLIER, null);
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


    public void setXCurr(int xCurr) {
        this.xCurr = xCurr;
    }

    public void setYCurr(int yCurr) {
        this.yCurr = yCurr;
    }

    public ShopSloth[][][] getShopItems() {
        return ShopItems;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMAX_PAGES() {
        return MAX_PAGES;
    }
}
