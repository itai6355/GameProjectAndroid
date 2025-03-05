package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.ui.GameImages;

public class ItemShop extends ShopState implements GameStateInterface {

    private int page = 0;

    private final int MAX_PAGES = 3;

    private final int ShopWidth = 10;
    private final int ShopHeight = 4;


    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    int xCurr = 450;
    int yCurr = 200;
    int Xspace = 50;
    int Yspace = 100;
    private final ShopSloth[][][] ShopItems = new ShopSloth[MAX_PAGES][ShopWidth][ShopHeight];


    public ItemShop(Game game) {
        super(game);


        for (int k = 0; k < MAX_PAGES; k++)
            for (int i = 0; i < ShopWidth; i++)
                for (int j = 0; j < ShopHeight; j++)
                    ShopItems[k][i][j] = new ShopSloth(i, j, xCurr + (i * (ShopSloth.SLOT_SIZE + Xspace)), yCurr + (j * (ShopSloth.SLOT_SIZE + Yspace)));

        initItems();
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

        canvas.drawBitmap(GameImages.INVENTORY_MOUSE.getImage(), ShopItems[page][xCurrIndex][yCurrIndex].getX() + GameConstants.Sprite.SCALE_MULTIPLIER, ShopItems[page][xCurrIndex][yCurrIndex].getY() + GameConstants.Sprite.SCALE_MULTIPLIER, null);
    }

    private void drawItem(Canvas canvas, ShopSloth ss) {
        Items itemType = ss.getItem();
        int imageX = (int) (ss.getX() + (float) ss.getSlothImage().getImage().getWidth() / 2 - (float) itemType.getImage().getWidth() / 2);
        int imageY = (int) (ss.getY() + (float) ss.getSlothImage().getImage().getHeight() / 2 - (float) itemType.getImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getImage(), imageX, imageY, null);
        canvas.drawText(String.valueOf(ss.getAmount()), ss.getX() + InventorySloth.SLOT_SIZE - GameConstants.Sprite.SCALE_MULTIPLIER, ss.getY() + InventorySloth.SLOT_SIZE - GameConstants.Sprite.SCALE_MULTIPLIER, super.BlackPaint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        for (int i = 0; i < ShopWidth; i++)
            for (int j = 0; j < ShopHeight; j++)
                if (ShopItems[page][i][j].isIn(event)) {
                    xCurrIndex = i;
                    yCurrIndex = j;
                }
    }


    private void initItems() {
        for (int i = 0; i < MAX_PAGES; i++) {
            for (ShopSloth[] shopSloths : ShopItems[i])
                for (ShopSloth slot : shopSloths)
                    switch (i) {
                        case 1:
                            slot.setItem(Items.EMPTY_POT);
                            break;
                        case 0:
                            slot.setItem(Items.COIN);
                            break;
                        default:
                            slot.setItem(Items.MEDIPACK);
                            break;
                    }
        }

    }

    public int getMAX_PAGES() {
        return MAX_PAGES;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
