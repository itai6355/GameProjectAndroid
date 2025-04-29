package com.example.gameproject.tutorial;

import static com.example.gameproject.helpers.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.environments.Tiles;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;
import com.example.gameproject.helpers.Paints;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;

import java.util.Random;

public class Simulation {

    private final float radius = 150;
    private final Paint circlePaint;
    GameMap currentMap;
    PlayerHolder player;
    private float cameraX, cameraY;
    private boolean movePlayer = false;
    private PointF lastTouchDiff;
    private final PointF joystickCenterPos = new PointF((float) GAME_WIDTH / 4, (float) GAME_HEIGHT / 4 * 3);
    private final PointF attackBtnCenterPos = new PointF((float) GAME_WIDTH / 4 * 3, (float) GAME_HEIGHT / 4 * 3);
    private int joystickPointerId = -1;
    private int attackBtnPointerId = -1;
    private boolean touchDown;
    private CustomButton backBtn;
    private Context context;
    private TutorialActivity.Tutorialloop tutorialloop;

    public Simulation(Context context) {
        this.context = context;
        circlePaint = Paints.HITBOX_PAINT;
        currentMap = new GameMap(getMap(), Tiles.OUTSIDE);

        player = new PlayerHolder(new PointF(GAME_WIDTH / 2f, GAME_HEIGHT / 2f));

        calcStartCameraValues();
        lastTouchDiff = new PointF(0, 0);
        backBtn = new CustomButton(20, 20, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
    }

    public void update(double delta) {
        updatePlayerMove(delta);
        player.update(movePlayer);
        if (player.isAttacking()) {
            if (!player.isAttackChecked()) player.setAttackChecked(true);
            else if (player.isAttackChecked()) player.setAttacking(false);
        }
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer) return;

        float baseSpeed = (float) (delta * 300 * player.getSPEED());
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0) player.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else player.setFaceDir(LEFT);
        } else {
            if (lastTouchDiff.y > 0) player.setFaceDir(GameConstants.Face_Dir.DOWN);
            else player.setFaceDir(GameConstants.Face_Dir.UP);
        }

        if (lastTouchDiff.x < 0) xSpeed *= -1;
        if (lastTouchDiff.y < 0) ySpeed *= -1;

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float deltaCameraX = cameraX * -1 + deltaX * -1;
        float deltaCameraY = cameraY * -1 + deltaY * -1;

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, currentMap)) {
            cameraX += deltaX;
            cameraY += deltaY;
        } else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, cameraX * -1, currentMap))
                cameraY += deltaY;

            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, cameraY * -1, currentMap))
                cameraX += deltaX;
        }
    }

    private void drawCharacter(Canvas canvas, Character character) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), character.getHitbox().left, character.getHitbox().bottom, null);
        canvas.drawBitmap(character.getGameCharType().getSprite(character.getAniIndex(), character.getFaceDir()), character.getHitbox().left, character.getHitbox().top, null);
        if (character.isAttacking()) drawWeapon(canvas, player);
    }

    private void drawWeapon(Canvas canvas, Character character) {
        canvas.rotate(character.getWepRot(), character.getAttackBox().left, character.getAttackBox().top);
        canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + character.wepRotAdjustLeft(), character.getAttackBox().top + character.wepRotAdjustTop(), null);
        canvas.rotate(character.getWepRot() * -1, character.getAttackBox().left, character.getAttackBox().top);
    }

    public void render(Canvas canvas) {
        drawTiles(canvas);
        canvas.drawCircle(joystickCenterPos.x, joystickCenterPos.y, radius, circlePaint);
        canvas.drawCircle(attackBtnCenterPos.x, attackBtnCenterPos.y, radius, circlePaint);
        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(backBtn.isPushed()), backBtn.getHitbox().left, backBtn.getHitbox().top, null);
        drawCharacter(canvas, player);
        if (!movePlayer) {
            canvas.drawText("Hold youre finger in the left circle", GAME_WIDTH/2 - 350, 100, Paints.BIG_TEXT_PAINT);
            canvas.drawText("And drug it to the direction you want to go to", GAME_WIDTH/2 - 200, 100 + Paints.BIG_TEXT_PAINT.getTextSize(), Paints.BIG_TEXT_PAINT);
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
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));

        switch (action) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (checkInsideJoyStick(eventPos, pointerId)) touchDown = true;
                else if (checkInsideAttackBtn(eventPos)) {
                    if (attackBtnPointerId < 0) {
                        player.setAttacking(true);
                        attackBtnPointerId = pointerId;
                    }
                }  else if (isIn(event, backBtn))
                    backBtn.setPushed(true);
            }

            case MotionEvent.ACTION_MOVE -> {
                if (touchDown)
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == joystickPointerId) {
                            float xDiff = event.getX(i) - joystickCenterPos.x;
                            float yDiff = event.getY(i) - joystickCenterPos.y;
                            setPlayerMoveTrue(new PointF(xDiff, yDiff));
                        }
                    }
            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (pointerId == joystickPointerId) {
                    resetJoystickButton();
                }
                if (pointerId == attackBtnPointerId) {
                    player.setAttacking(false);
                    attackBtnPointerId = -1;
                }

                if (isIn(event, backBtn) && backBtn.isPushed())
                    setPlaying();


            }
        }


    }

    private void setPlaying() {
        Intent game = new Intent(context, GameActivity.class);
        game.putExtra("username", GameActivity.getUsername());
        game.putExtra("password", GameActivity.getPassword());
        tutorialloop.stopSettingLoop();
        context.startActivity(game);
    }

    private boolean isIn(MotionEvent event, CustomButton btnBack) {
        return event.getX() > btnBack.getHitbox().left && event.getX() < btnBack.getHitbox().right
                && event.getY() > btnBack.getHitbox().top && event.getY() < btnBack.getHitbox().bottom;
    }

    private void resetJoystickButton() {
        touchDown = false;
        setPlayerMoveFalse();
        joystickPointerId = -1;
    }

    public void setPlayerMoveTrue(PointF lastTouchDiff) {
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse() {
        movePlayer = false;
        player.resetAnimation();
    }


    public void drawTiles(Canvas canvas) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                canvas.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }


    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f - currentMap.getMapWidth() / 2f;
        cameraY = GAME_HEIGHT / 2f - currentMap.getMapHeight() / 2f;
    }

    private int[][] getMap() {
        int[][] map = new int[1000][1000];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = new Random().nextInt(5) + 275;
        return map;
    }

    public void setLoop(TutorialActivity.Tutorialloop loop) {
        this.tutorialloop = loop;
    }
}
