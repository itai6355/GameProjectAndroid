package com.example.gameproject.entities.enemies;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.Paints;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Random;

public class DarkNinja extends AttackingEnemy {

    private int xShuriken;
    private int yShuriken;
    private final RectF shurikenHitbox;
    private boolean shurikenInFlight;

    private int ShurikenFaceDir;
    private boolean isSHorikenTilted = false;

    Paint redPaint;


    public DarkNinja(PointF pos) {
        super(pos, Enemies.DARK_NINJA, Weapons.SHURIKEN);
        this.timeForAttackDuration = 2000;
        setStartHealth(420);
        shurikenHitbox = new RectF(getHitbox());
        AddLootTypes();
        redPaint = Paints.HITBOX_PAINT;

    }

    public void setShurikenFaceDir(int shurikenFaceDir) {
        this.ShurikenFaceDir = shurikenFaceDir;
    }

    public void updateShurikenHitbox() {
        shurikenHitbox.left = xShuriken;
        shurikenHitbox.top = yShuriken;
        shurikenHitbox.right = xShuriken + Weapons.SHURIKEN.getWeaponImg().getWidth();
        shurikenHitbox.bottom = yShuriken + Weapons.SHURIKEN.getWeaponImg().getHeight();
    }

    public void drawShuriken(Canvas canvas, float xCamera, float yCamera) {
        float screenX = xShuriken + xCamera;
        float screenY = yShuriken + yCamera;

        canvas.drawBitmap(Weapons.getSuriken(isSHorikenTilted), screenX, screenY, null);

        if (GameActivity.isDrawHitbox()) {
            RectF screenHitbox = new RectF(
                    shurikenHitbox.left + xCamera,
                    shurikenHitbox.top + yCamera,
                    shurikenHitbox.right + xCamera,
                    shurikenHitbox.bottom + yCamera
            );
            canvas.drawRect(screenHitbox, redPaint);
        }
    }

    public void updtaeShuriken() {
        if (isAttacking()) {
            shurikenInFlight = true;
            isSHorikenTilted = !isSHorikenTilted;
            switch (ShurikenFaceDir) {
                case GameConstants.Face_Dir.UP -> yShuriken -= 10;
                case GameConstants.Face_Dir.DOWN -> yShuriken += 10;
                case GameConstants.Face_Dir.LEFT -> xShuriken -= 10;
                case GameConstants.Face_Dir.RIGHT -> xShuriken += 10;
            }
        } else if (isPreparingAttack()) {
            resetShuriken();
            setShurikenFaceDir(getFaceDir());
            shurikenInFlight = false;
        } else if (isAttackChecked() && isShurikenOffScreen()) {
            resetShuriken();
            shurikenInFlight = false;
            setAttacking(false);
            setAttackChecked(false);
        } else if (moving) {
            resetShuriken();
            shurikenInFlight = false;
        }

        updateShurikenHitbox();
    }

    private boolean isShurikenOffScreen() {
        return xShuriken < 0 || yShuriken < 0 ||
                xShuriken > MainActivity.GAME_WIDTH ||
                yShuriken > MainActivity.GAME_HEIGHT;
    }


    public boolean isShurikenInFlight() {
        return shurikenInFlight;
    }


    private void resetShuriken() {
        switch (ShurikenFaceDir) {
            case GameConstants.Face_Dir.UP -> {
                xShuriken = (int) getHitbox().centerX();
                yShuriken = (int) (getHitbox().top - getHitbox().height());
                ShurikenFaceDir = -1;
            }
            case GameConstants.Face_Dir.DOWN -> {
                xShuriken = (int) getHitbox().centerX();
                yShuriken = (int) getHitbox().bottom;
                ShurikenFaceDir = -1;
            }
            case GameConstants.Face_Dir.LEFT -> {
                xShuriken = (int) (getHitbox().left - getHitbox().width());
                yShuriken = (int) getHitbox().centerY();
                ShurikenFaceDir = -1;
            }
            case GameConstants.Face_Dir.RIGHT -> {
                xShuriken = (int) getHitbox().right;
                yShuriken = (int) getHitbox().centerY();
                ShurikenFaceDir = -1;
            }
            default -> {
            }
        }

    }

    public RectF getShurikenHitbox() {
        return shurikenHitbox;
    }

    @Override
    protected void AddLootTypes() {
        if (new Random().nextBoolean())
            KilledLoot.add(Items.POTION_RED);
        else KilledLoot.add(Items.POTION_WHITE);
    }

    public void setMoving(boolean moving) {
        super.moving = moving;
    }
}