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

public class DarkWizard extends AttackingEnemy {

    private int xFireBall;
    private int yFireBall;
    private final RectF FireBallHitbox;
    private boolean FireBallInFlight;

    private int FireBallFaceDir;
    Paint redPaint;


    public DarkWizard(PointF pos) {
        super(pos, Enemies.DARK_WIZARD, Weapons.FIREBALL);
        this.timeForAttackDuration = 2000;
        setStartHealth(420);
        FireBallHitbox = new RectF(getHitbox());
        AddLootTypes();
        redPaint = Paints.HITBOX_PAINT;

    }

    public void setFireBallFaceDir(int fireBallFaceDir) {
        this.FireBallFaceDir = fireBallFaceDir;
    }

    public void updateFireBallHitbox() {
        FireBallHitbox.left = xFireBall;
        FireBallHitbox.top = yFireBall;
        FireBallHitbox.right = xFireBall + Weapons.SHURIKEN.getWeaponImg().getWidth();
        FireBallHitbox.bottom = yFireBall + Weapons.SHURIKEN.getWeaponImg().getHeight();
    }

    public void drawFireBall(Canvas canvas, float xCamera, float yCamera) {
        float screenX = xFireBall + xCamera;
        float screenY = yFireBall + yCamera;

        float rotationAngle = 0;
        switch (FireBallFaceDir) {
            case GameConstants.Face_Dir.UP -> rotationAngle = 0;
            case GameConstants.Face_Dir.DOWN -> rotationAngle = 180;
            case GameConstants.Face_Dir.LEFT -> rotationAngle = 90;
            case GameConstants.Face_Dir.RIGHT -> rotationAngle = 270;
        }

        canvas.save();
        canvas.rotate(-rotationAngle, screenX + this.getWeapon().getWeaponImg().getWidth() / 2.0f, screenY + this.getWeapon().getWeaponImg().getHeight() / 2.0f);
        canvas.drawBitmap(Weapons.getFireball(), screenX, screenY, null);
        canvas.restore();

        if (GameActivity.isDrawHitbox()) {
            RectF screenHitbox = new RectF(FireBallHitbox.left + xCamera, FireBallHitbox.top + yCamera, FireBallHitbox.right + xCamera, FireBallHitbox.bottom + yCamera);
            canvas.drawRect(screenHitbox, redPaint);
        }
    }

    public void updtaeShuriken() {
        if (isAttacking()) {
            FireBallInFlight = true;
            switch (FireBallFaceDir) {
                case GameConstants.Face_Dir.UP -> yFireBall -= 10;
                case GameConstants.Face_Dir.DOWN -> yFireBall += 10;
                case GameConstants.Face_Dir.LEFT -> xFireBall -= 10;
                case GameConstants.Face_Dir.RIGHT -> xFireBall += 10;
            }
        } else if (isPreparingAttack()) {
            resetFireBall();
            setFireBallFaceDir(getFaceDir());
            FireBallInFlight = false;
        } else if (isAttackChecked() && isFireBallOffScreen()) {
            resetFireBall();
            FireBallInFlight = false;
            setAttacking(false);
            setAttackChecked(false);
        } else if (moving) {
            resetFireBall();
            FireBallInFlight = false;
        }

        updateFireBallHitbox();
    }

    private boolean isFireBallOffScreen() {
        return xFireBall < 0 || yFireBall < 0 || xFireBall > MainActivity.GAME_WIDTH || yFireBall > MainActivity.GAME_HEIGHT;
    }


    public boolean isFireBallInFlight() {
        return FireBallInFlight;
    }


    private void resetFireBall() {
        switch (FireBallFaceDir) {
            case GameConstants.Face_Dir.UP -> {
                xFireBall = (int) getHitbox().centerX();
                yFireBall = (int) (getHitbox().top - getHitbox().height());
                FireBallFaceDir = -1;
            }
            case GameConstants.Face_Dir.DOWN -> {
                xFireBall = (int) getHitbox().centerX();
                yFireBall = (int) getHitbox().bottom;
                FireBallFaceDir = -1;
            }
            case GameConstants.Face_Dir.LEFT -> {
                xFireBall = (int) (getHitbox().left - getHitbox().width());
                yFireBall = (int) getHitbox().centerY();
                FireBallFaceDir = -1;
            }
            case GameConstants.Face_Dir.RIGHT -> {
                xFireBall = (int) getHitbox().right;
                yFireBall = (int) getHitbox().centerY();
                FireBallFaceDir = -1;
            }
            default -> {
            }
        }


    }

    public RectF getFireBallHitbox() {
        return FireBallHitbox;
    }

    @Override
    protected void AddLootTypes() {
        if (new Random().nextBoolean())
            KilledLoot.add(Items.POTION_BLUE);
        else KilledLoot.add(Items.POTION_PURPLE);
    }

    public void setMoving(boolean moving) {
        super.moving = moving;
    }
}
