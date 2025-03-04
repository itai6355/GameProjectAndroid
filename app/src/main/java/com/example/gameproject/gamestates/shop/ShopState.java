package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class ShopState extends BaseState implements GameStateInterface {


    private final CustomButton btnBack = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
    private final int ShopWidth = 10;
    private final int ShopHeight = 4;
    int xStart = 450;
    int yStart = 200;
    int xCurr = xStart;
    int yCurr = yStart;
    int Xspace = 50;
    int Yspace = 100;
    private int xCurrIndex = 0;
    private int yCurrIndex = 0;
    private final ShopSloth[][] ShopInventory = new ShopSloth[ShopWidth][ShopHeight];
    private final Paint BlackPaint;


    public ShopState(Game game) {
        super(game);
        BlackPaint = new Paint();
        BlackPaint.setColor(Color.BLACK);
        BlackPaint.setTextSize(BlackPaint.getTextSize() + 15);
        BlackPaint.setStrokeWidth(3);
        BlackPaint.setStyle(Paint.Style.STROKE);


        for (int i = 0; i < ShopInventory.length; i++) {
            for (int j = 0; j < ShopInventory[i].length; j++) {
                ShopInventory[i][j] = new ShopSloth(i, j, xCurr + (i * (ShopSloth.SLOT_SIZE + Xspace)), yCurr + (j * (ShopSloth.SLOT_SIZE + Yspace)));
            }
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        paintBackgrawnd(canvas);

        for (ShopSloth[] shopSloths : ShopInventory) {
            for (ShopSloth slot : shopSloths) {
                canvas.drawBitmap(slot.getSlothImage().getImage(), slot.getX(), slot.getY(), null);
            }
        }

        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);

        for (ShopSloth[] SSs : ShopInventory)
            for (ShopSloth SS : SSs)
                if (SS != null && SS.getAmount() > 0) drawItem(canvas, SS);

        canvas.drawBitmap(GameImages.INVENTORY_MOUSE.getImage(), ShopInventory[xCurrIndex][yCurrIndex].getX() - GameConstants.Sprite.SCALE_MULTIPLIER, ShopInventory[xCurrIndex][yCurrIndex].getY() - GameConstants.Sprite.SCALE_MULTIPLIER, null);


    }

    private void paintBackgrawnd(Canvas canvas) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                canvas.drawBitmap(ShopImages.SHOP_WALL_BACKGRAWND.getImage(),
                        i * ShopImages.SHOP_WALL_BACKGRAWND.getImage().getWidth(),
                        j * ShopImages.SHOP_WALL_BACKGRAWND.getImage().getHeight(), null);
            }
        }

        //TODO: create the rest of the backgrawnd

        canvas.drawBitmap(ShopImages.SHOP_BARREL_BACKGRAWND.getImage(), 0, MainActivity.GAME_HEIGHT - ShopImages.SHOP_BARREL_BACKGRAWND.getImage().getHeight(), null);
        canvas.drawBitmap(ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getImage(),
                MainActivity.GAME_WIDTH - ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getImage().getWidth() - ShopImages.SHOP_BRICK_BOX_DOUBLED_BACKGRAWND.getImage().getWidth(),
                MainActivity.GAME_HEIGHT - ShopImages.SHOP_BRICK_BOX_BACKGRAWND.getImage().getHeight(), null);
        canvas.drawBitmap(ShopImages.SHOP_BRICK_BOX_DOUBLED_BACKGRAWND.getImage(),
                MainActivity.GAME_WIDTH - ShopImages.SHOP_BRICK_BOX_DOUBLED_BACKGRAWND.getImage().getWidth(),
                MainActivity.GAME_HEIGHT - ShopImages.SHOP_BRICK_BOX_DOUBLED_BACKGRAWND.getImage().getHeight(), null);

        canvas.drawBitmap(ShopImages.SHOP_WINDOW_BACKGRAWND.getImage(), 200, 230, null);

    }


    private void drawItem(Canvas canvas, ShopSloth ss) {
        Items itemType = ss.getItem();
        int imageX = (int) (ss.getX() + (float) ss.getSlothImage().getImage().getWidth() / 2 - (float) itemType.getImage().getWidth() / 2);
        int imageY = (int) (ss.getY() + (float) ss.getSlothImage().getImage().getHeight() / 2 - (float) itemType.getImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getImage(), imageX, imageY, null);
        canvas.drawText(String.valueOf(ss.getAmount()), ss.getX() + InventorySloth.SLOT_SIZE - 6 * GameConstants.Sprite.SCALE_MULTIPLIER, ss.getY() + InventorySloth.SLOT_SIZE - 6 * GameConstants.Sprite.SCALE_MULTIPLIER, BlackPaint);
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

        for (int i = 0; i < ShopInventory.length; i++) {
            for (int j = 0; j < ShopInventory[i].length; j++) {
                if (ShopInventory[i][j].isIn(event)) {
                    xCurrIndex = i;
                    yCurrIndex = j;
                }
            }
        }

    }
}
