package com.example.gameproject.gamestates.invenory;

import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class InventoryState extends BaseState implements GameStateInterface {

    private final CustomButton btnBack = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());

    public static final int inventoryWidth = 8;
    public static final int inventoryHeight = 4;

    //every time to change, change in player the itemBar too.
    int xCurr = 550;
    int yCurr = 200;
    int space = 10 * GameConstants.Sprite.SCALE_MULTIPLIER;

    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    private final InventorySloth[][] inventory = new InventorySloth[inventoryWidth][inventoryHeight + 1];
    private final Paint BlackPaint;
    private InventorySloth lstItem;


    public InventoryState(Game game) {
        super(game);
        BlackPaint = new Paint();
        BlackPaint.setColor(Color.BLACK);
        BlackPaint.setTextSize(BlackPaint.getTextSize() + 15);
        BlackPaint.setStrokeWidth(3);
        BlackPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                if (j == inventory[i].length - 1) {
                    inventory[i][j] = new InventorySloth(i, j, xCurr + (i * (InventorySloth.SLOT_SIZE + space)), GAME_HEIGHT - InventorySloth.SLOT_SIZE - GameConstants.Sprite.Y_DRAW_OFFSET - space);
                } else {
                    inventory[i][j] = new InventorySloth(i, j, xCurr + (i * (InventorySloth.SLOT_SIZE + space)), yCurr + (j * (InventorySloth.SLOT_SIZE + space)));
                }
            }
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(GameImages.BACKGRAWND.getImage(), 0, 0, null);
        for (InventorySloth[] inventorySloths : inventory)
            for (InventorySloth slot : inventorySloths)
                canvas.drawBitmap(slot.getImage().getImage(), slot.getX(), slot.getY(), null);


        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);

        for (InventorySloth[] ISs : inventory)
            for (InventorySloth IS : ISs)
                if (IS != null && IS.getAmount() > 0) drawItem(canvas, IS);

        canvas.drawBitmap(GameImages.INVENTORY_MOUSE.getImage(), inventory[xCurrIndex][yCurrIndex].getX() - GameConstants.Sprite.SCALE_MULTIPLIER, inventory[xCurrIndex][yCurrIndex].getY() - GameConstants.Sprite.SCALE_MULTIPLIER, null);
    }

    private void drawItem(Canvas canvas, InventorySloth IS) {
        Items itemType = IS.getItem();
        int imageX = (int) (IS.getX() + (float) IS.getImage().getImage().getWidth() / 2 - (float) itemType.getSmallImage().getWidth() / 2);
        int imageY = (int) (IS.getY() + (float) IS.getImage().getImage().getHeight() / 2 - (float) itemType.getSmallImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getSmallImage(), imageX, imageY, null);
        canvas.drawText(String.valueOf(IS.getAmount()), IS.getX() + InventorySloth.SLOT_SIZE, IS.getY() + InventorySloth.SLOT_SIZE, BlackPaint);
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
                    if (lstItem != null && inventory[i][j].getItem() == null) {
                        moveItem(lstItem, inventory[i][j]);
                    } else {
                        lstItem = inventory[i][j];
                    }
                }
            }
        }
    }

    private void moveItem(InventorySloth lst, InventorySloth curr) {
        curr.setItem(lst.getItem());
        curr.setAmount(lst.getAmount());
        lst.setItem(null);
        lst.setAmount(0);
        lstItem = null;
    }


    public InventorySloth[][] getInventory() {
        return inventory;
    }
}
