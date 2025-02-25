package com.example.gameproject.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.InventorySloth;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.ui.GameImages;

public class InventoryState extends BaseState implements GameStateInterface {

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;

    private final int inventoryWidth = 8;
    private final int inventoryHeight = 6;

    private InventorySloth[][] inventory = new InventorySloth[inventoryWidth][inventoryHeight];

    int xCurr = 750;
    int yCurr = 200;

    int space = 15;


    public InventoryState(Game game) {
        super(game);
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                inventory[i][j] = new InventorySloth(i, j, xCurr + (i * (InventorySloth.SLOT_SIZE + space)), yCurr + (j * (InventorySloth.SLOT_SIZE + space)));
            }
        }


    }

    @Override
    public void update(double delta) {
        //TODO: get the inventory from the SQL and update it to the inventory array.
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(GameImages.BACKGRAWND.getImage(), 0, 0, null);
        for (InventorySloth[] inventorySloths : inventory) {
            for (InventorySloth slot : inventorySloths) {
                canvas.drawBitmap(slot.getImage().getImage(), slot.getX(), slot.getY(), null);
            }
        }
        canvas.drawBitmap(GameImages.INVENTORY_MOUSE.getImage(), inventory[xCurrIndex][yCurrIndex].getX() - GameConstants.Sprite.SCALE_MULTIPLIER, inventory[xCurrIndex][yCurrIndex].getY() - GameConstants.Sprite.SCALE_MULTIPLIER, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                if (inventory[i][j].isIn(event)) {
                    xCurrIndex = i;
                    yCurrIndex = j;
                }
            }
        }

    }
}
