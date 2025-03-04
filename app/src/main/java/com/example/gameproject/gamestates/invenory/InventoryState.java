package com.example.gameproject.gamestates.invenory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

import java.util.HashMap;
import java.util.Map;

public class InventoryState extends BaseState implements GameStateInterface {

    private final CustomButton btnBack = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
    private final int inventoryWidth = 8;
    private final int inventoryHeight = 6;
    int xCurr = 750;
    int yCurr = 200;
    int space = 15;
    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    private final InventorySloth[][] inventory = new InventorySloth[inventoryWidth][inventoryHeight];
    private final Paint BlackPaint;


    public InventoryState(Game game) {
        super(game);
        BlackPaint = new Paint();
        BlackPaint.setColor(Color.BLACK);
        BlackPaint.setTextSize(BlackPaint.getTextSize() + 15);
        BlackPaint.setStrokeWidth(3);
        BlackPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                inventory[i][j] = new InventorySloth(i, j, xCurr + (i * (InventorySloth.SLOT_SIZE + space)), yCurr + (j * (InventorySloth.SLOT_SIZE + space)));
            }
        }


    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(GameImages.BACKGRAWND.getImage(), 0, 0, null);
        for (InventorySloth[] inventorySloths : inventory) {
            for (InventorySloth slot : inventorySloths) {
                canvas.drawBitmap(slot.getImage().getImage(), slot.getX(), slot.getY(), null);
            }
        }

        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);

        for (InventorySloth[] ISs : inventory)
            for (InventorySloth IS : ISs)
                if (IS != null && IS.getAmount() > 0) drawItem(canvas, IS);

        canvas.drawBitmap(GameImages.INVENTORY_MOUSE.getImage(), inventory[xCurrIndex][yCurrIndex].getX() - GameConstants.Sprite.SCALE_MULTIPLIER, inventory[xCurrIndex][yCurrIndex].getY() - GameConstants.Sprite.SCALE_MULTIPLIER, null);


    }

    private void drawItem(Canvas canvas, InventorySloth IS) {
        Items itemType = IS.getItem();
        int imageX = (int) (IS.getX() + (float) IS.getImage().getImage().getWidth() / 2 - (float) itemType.getImage().getWidth() / 2);
        int imageY = (int) (IS.getY() + (float) IS.getImage().getImage().getHeight() / 2 - (float) itemType.getImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getImage(), imageX, imageY, null);
        canvas.drawText(String.valueOf(IS.getAmount()), IS.getX() + InventorySloth.SLOT_SIZE - 6 * GameConstants.Sprite.SCALE_MULTIPLIER, IS.getY() + InventorySloth.SLOT_SIZE - 6 * GameConstants.Sprite.SCALE_MULTIPLIER, BlackPaint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack)) btnBack.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnBack)) {
                if (btnBack.isPushed()) game.setCurrentGameState(Game.GameState.PLAYING);
            }

            btnBack.setPushed(false);
        }

        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                if (inventory[i][j].isIn(event)) {
                    xCurrIndex = i;
                    yCurrIndex = j;
                }
            }
        }
    }


    public void SyncInventories(Player player) {
        Map<Items, InventorySloth> itemSlotMap = new HashMap<>();

        for (int i = inventory.length - 1; i >= 0; i--) {
            for (int j = inventory[i].length - 1; j >= 0; j--) {
                if (inventory[i][j].getItem() != null) {
                    inventory[i][j].setAmount(0);
                    itemSlotMap.put(inventory[i][j].getItem(), inventory[i][j]);
                } else {
                    itemSlotMap.put(null, inventory[i][j]);
                }
            }
        }


        for (Items item : player.getInventory()) {
            InventorySloth slot = itemSlotMap.get(item);
            if (slot != null) {
                slot.addAmount();
            } else {
                slot = itemSlotMap.get(null);
                if (slot != null) {
                    slot.setItem(item);
                    slot.setAmount(1);
                    itemSlotMap.put(item, slot);
                    itemSlotMap.remove(null);
                }
            }
        }
    }
}
