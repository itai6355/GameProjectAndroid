package com.example.gameproject.gamestates.playing;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;
import com.example.gameproject.ui.HealthIcons;

public class PlayingUI {


    private final PointF joystickCenterPos = new PointF((float) GAME_WIDTH / 4, (float) GAME_HEIGHT / 4 * 3);
    private final PointF attackBtnCenterPos = new PointF((float) GAME_WIDTH / 4 * 3, (float) GAME_HEIGHT / 4 * 3);
    private final float radius = 150;
    private final Paint circlePaint;
    private final Playing playing;
    private final int healthIconX = 250, healthIconY = 25;
    private final CustomButton btnSetting;
    private final CustomButton btnInventory;
    private final CustomButton btnShop;
    private final Paint BlackPaint;
    private int joystickPointerId = -1;
    private int attackBtnPointerId = -1;
    private boolean touchDown;


    public PlayingUI(Playing playing) {
        this.playing = playing;

        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        BlackPaint = new Paint();
        BlackPaint.setColor(Color.BLACK);
        BlackPaint.setTextSize(BlackPaint.getTextSize() + 15);
        BlackPaint.setStrokeWidth(3);
        BlackPaint.setStyle(Paint.Style.STROKE);


        btnSetting = new CustomButton(GAME_WIDTH - 230, 50, ButtonImages.PLAYING_SETTING.getWidth(), ButtonImages.PLAYING_SETTING.getHeight());
        btnInventory = new CustomButton(GAME_WIDTH - 230, 70 + ButtonImages.PLAYING_MENU.getHeight(), ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
        btnShop = new CustomButton(GAME_WIDTH - 230 - ButtonImages.PLAYING_MENU.getWidth() - 20, 50, ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());


    }

    public void draw(Canvas canvas) {
        drawUI(canvas);
    }

    private void drawUI(Canvas canvas) {
        canvas.drawCircle(joystickCenterPos.x, joystickCenterPos.y, radius, circlePaint);
        canvas.drawCircle(attackBtnCenterPos.x, attackBtnCenterPos.y, radius, circlePaint);


        canvas.drawBitmap(GameImages.PLAYER_BOX.getImage(), 0, 0, null);
        canvas.drawBitmap(GameImages.ICON_BOX.getImage(), 25, 25, null);
        canvas.drawBitmap(playing.getPlayer().getIcon().getImage(), 65, 65, null);


        drawButtons(canvas);
        drawHealth(canvas);
        drawHungerBar(canvas);
        drawItemBar(canvas);

    }

    private void drawHungerBar(Canvas canvas) {
        int hunger = playing.getPlayer().getCurrHunger();
        int maxHunger = playing.getPlayer().getMaxHunger();
        int y = healthIconY + HealthIcons.HEART_FULL.getIcon().getWidth() + GameConstants.Sprite.Y_DRAW_OFFSET;
        int x = healthIconX - 50;
        for (int i = 0; i < maxHunger - hunger; i++) {
            canvas.drawBitmap(GameImages.HUNGER_EMPTY.getImage(), x + 70 * i, y, null);
        }
        for (int i = 0; i < hunger; i++) {
            canvas.drawBitmap(GameImages.HUNGER_FULL.getImage(), x + 70 * (i + maxHunger - hunger), y, null);
        }


    }

    private void drawItemBar(Canvas canvas) {
        var inventory = playing.getPlayer().getInventory();
        for (InventorySloth[] inventorySloths : inventory) {
            InventorySloth inventorySloth = inventorySloths[inventorySloths.length - 1];
            if (inventorySloth != null) {
                canvas.drawBitmap(inventorySloth.getImage().getImage(), inventorySloth.getX(), inventorySloth.getY(), null);
                if (inventorySloth.getAmount() > 0) drawItem(canvas, inventorySloth);
            }
        }
    }

    private void drawItem(Canvas canvas, InventorySloth IS) {
        Items itemType = IS.getItem();
        int imageX = (int) (IS.getX() + (float) IS.getImage().getImage().getWidth() / 2 - (float) itemType.getSmallImage().getWidth() / 2);
        int imageY = (int) (IS.getY() + (float) IS.getImage().getImage().getHeight() / 2 - (float) itemType.getSmallImage().getHeight() / 2);
        canvas.drawBitmap(itemType.getSmallImage(), imageX, imageY, null);
        canvas.drawText(String.valueOf(IS.getAmount()), IS.getX() + InventorySloth.SLOT_SIZE, IS.getY() + InventorySloth.SLOT_SIZE, BlackPaint);
    }


    private void drawButtons(Canvas canvas) {
        canvas.drawBitmap(ButtonImages.PLAYING_SETTING.getBtnImg(btnSetting.isPushed(btnSetting.getPointerId())), btnSetting.getHitbox().left, btnSetting.getHitbox().top, null);


        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btnInventory.isPushed(btnInventory.getPointerId())), btnInventory.getHitbox().left, btnInventory.getHitbox().top, null);

        if (btnInventory.isPushed(btnInventory.getPointerId())) {
            canvas.drawBitmap(ButtonImages.PLAYING_INVENTORY.getBtnImg(btnInventory.isPushed(btnInventory.getPointerId())), btnInventory.getHitbox().left + 20, btnInventory.getHitbox().top + 20, null);
        } else {
            canvas.drawBitmap(ButtonImages.PLAYING_INVENTORY.getBtnImg(btnInventory.isPushed(btnInventory.getPointerId())), btnInventory.getHitbox().left + 25, btnInventory.getHitbox().top + 40, null);
        }

        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(btnShop.isPushed(btnShop.getPointerId())), btnShop.getHitbox().left, btnShop.getHitbox().top, null);
        canvas.drawBitmap(GameImages.SHOP_ICON.getImage(), btnShop.getHitbox().left + 35, btnShop.getHitbox().top + 35, null);


    }


    private void drawHealth(Canvas canvas) {
        Player player = playing.getPlayer();
        for (int i = 0; i < player.getMaxHealth() / 100; i++) {
            int x = healthIconX + 100 * i;
            int heartValue = player.getCurrentHealth() - 100 * i;

            if (heartValue < 100) {
                if (heartValue <= 0)
                    canvas.drawBitmap(HealthIcons.HEART_EMPTY.getIcon(), x, healthIconY, null);
                else if (heartValue == 25)
                    canvas.drawBitmap(HealthIcons.HEART_1Q.getIcon(), x, healthIconY, null);
                else if (heartValue == 50)
                    canvas.drawBitmap(HealthIcons.HEART_HALF.getIcon(), x, healthIconY, null);
                else canvas.drawBitmap(HealthIcons.HEART_3Q.getIcon(), x, healthIconY, null);
            } else canvas.drawBitmap(HealthIcons.HEART_FULL.getIcon(), x, healthIconY, null);
        }


    }


    private boolean isInsideRadius(PointF eventPos, PointF circle) {
        float a = Math.abs(eventPos.x - circle.x);
        float b = Math.abs(eventPos.y - circle.y);
        float c = (float) Math.hypot(a, b);

        return c <= radius;
    }

    private boolean checkInsideAttackBtn(PointF eventPos) {
        return isInsideRadius(eventPos, attackBtnCenterPos);
    }

    private boolean checkInsideJoyStick(PointF eventPos, int pointerId) {
        if (isInsideRadius(eventPos, joystickCenterPos)) {
            touchDown = true;
            joystickPointerId = pointerId;
            return true;
        }
        return false;
    }


    public void touchEvents(MotionEvent event) {
        //TODO: need fix, somtimes you cant move the item to the first itemBar
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));

        switch (action) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (checkInsideJoyStick(eventPos, pointerId)) touchDown = true;
                else if (checkInsideAttackBtn(eventPos)) {
                    if (attackBtnPointerId < 0) {
                        playing.getPlayer().setAttacking(true);
                        attackBtnPointerId = pointerId;
                    }
                } else {
                    if (isIn(eventPos, btnSetting)) btnSetting.setPushed(true, pointerId);
                    else if (isIn(eventPos, btnInventory)) btnInventory.setPushed(true, pointerId);
                    else if (isIn(eventPos, btnShop)) btnShop.setPushed(true, pointerId);
                }
            }

            case MotionEvent.ACTION_MOVE -> {
                if (touchDown) for (int i = 0; i < event.getPointerCount(); i++) {
                    if (event.getPointerId(i) == joystickPointerId) {
                        float xDiff = event.getX(i) - joystickCenterPos.x;
                        float yDiff = event.getY(i) - joystickCenterPos.y;
                        playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                    }
                }
            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (pointerId == joystickPointerId) {
                    resetJoystickButton();
                } else {
                    if (isIn(eventPos, btnSetting)) {
                        if (btnSetting.isPushed(pointerId)) {
                            resetJoystickButton();
                            playing.setGameStateToSettings();
                            playing.resetLastItem();
                        }
                    } else if (isIn(eventPos, btnInventory)) {
                        if (btnInventory.isPushed(pointerId)) {
                            resetJoystickButton();
                            playing.setGameStateToInventory();
                            playing.resetLastItem();
                        }
                    } else if (isIn(eventPos, btnShop)) {
                        if (btnShop.isPushed(pointerId)) {
                            resetJoystickButton();
                            playing.setGameStateToShop();
                            playing.resetLastItem();
                        }
                    }
                    btnShop.unPush(pointerId);
                    btnSetting.unPush(pointerId);
                    btnInventory.unPush(pointerId);

                    if (pointerId == attackBtnPointerId) {
                        playing.getPlayer().setAttacking(false);
                        attackBtnPointerId = -1;
                    }
                }
            }
        }
    }

    private void resetJoystickButton() {
        touchDown = false;
        playing.setPlayerMoveFalse();
        joystickPointerId = -1;
    }

    private boolean isIn(PointF eventPos, CustomButton b) {
        return b.getHitbox().contains(eventPos.x, eventPos.y);
    }


}
