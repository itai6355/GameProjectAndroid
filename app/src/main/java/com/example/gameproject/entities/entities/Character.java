package com.example.gameproject.entities.entities;

import static com.example.gameproject.helpers.GameConstants.Sprite.HITBOX_SIZE;
import static com.example.gameproject.helpers.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.example.gameproject.helpers.GameConstants.Sprite.Y_DRAW_OFFSET;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.enemies.Enemies;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.main.MainActivity;

public abstract class Character extends Entity{
    protected final GameCharacters gameCharType;
    protected final Enemies EnemyType;
    private final int attackDamage;
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;
    protected boolean attacking, attackChecked;
    private RectF attackBox = null;
    private int maxHealth;
    private int currentHealth;

    public Character(PointF pos, GameCharacters gameCharType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.gameCharType = gameCharType;
        this.EnemyType = null;
        attackDamage = setAttackDamage();
        updateWepHitbox();

    }

    public Character(PointF pos, Enemies EnemyType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.EnemyType = EnemyType;
        this.gameCharType = null;
        attackDamage = setAttackDamage();
        updateWepHitbox();

    }

    protected void setStartHealth(int health) {
        maxHealth = health;
        currentHealth = maxHealth;
    }

    public void resetCharacterHealth() {
        currentHealth = maxHealth;
    }

    public void damageCharacter(int damage) {
        this.currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
        if (this instanceof Player) MainActivity.Vibrate(100);


    }

    protected void heal(int heal) {
        currentHealth += heal;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }

    public boolean isEnemy() {
        return EnemyType != null;
    }

    private int setAttackDamage() {
        if (isEnemy())
            return switch (EnemyType) {
                case SKELETON -> 25;
                case MASKED_RAKKON -> 0;
            };
        else return 50;
    }

    protected void updateAnimation() {
        aniTick++;
        if (aniTick >= GameConstants.Animation.SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GameConstants.Animation.AMOUNT)
                aniIndex = 0;
        }
    }

    public void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }

    public int getAniIndex() {
        if (attacking) return 4;
        return aniIndex;
    }

    public int getFaceDir() {
        return faceDir;
    }

    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }

    public GameCharacters getGameCharType() {
        return gameCharType;
    }

    public Enemies getEnemyType() {
        return EnemyType;
    }

    public void updateWepHitbox() {
        PointF pos = getWepPos();
        float w = getWepWidth();
        float h = getWepHeight();

        attackBox = new RectF(pos.x, pos.y, pos.x + w, pos.y + h);
    }

    public float getWepWidth() {
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidth();
            default -> -1;
        };
    }

    public float getWepHeight() {
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getHeight();

            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                    Weapons.BIG_SWORD.getWidth();

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDir());
        };
    }

    public PointF getWepPos() {
        return switch (getFaceDir()) {

            case GameConstants.Face_Dir.UP ->
                    new PointF(getHitbox().left - 0.5f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().top - Weapons.BIG_SWORD.getHeight() - Y_DRAW_OFFSET);

            case GameConstants.Face_Dir.DOWN ->
                    new PointF(getHitbox().left + 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER, getHitbox().bottom);

            case GameConstants.Face_Dir.LEFT ->
                    new PointF(getHitbox().left - Weapons.BIG_SWORD.getHeight() - X_DRAW_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            case GameConstants.Face_Dir.RIGHT ->
                    new PointF(getHitbox().right + X_DRAW_OFFSET, getHitbox().bottom - Weapons.BIG_SWORD.getWidth() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value: " + getFaceDir());
        };

    }

    public float wepRotAdjustTop() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.UP ->
                    -Weapons.BIG_SWORD.getHeight();
            default -> 0;
        };
    }

    public float wepRotAdjustLeft() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.RIGHT ->
                    -Weapons.BIG_SWORD.getWidth();
            default -> 0;
        };
    }

    public float getWepRot() {
        return switch (getFaceDir()) {
            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;
            default -> 0;
        };

    }

    public RectF getAttackBox() {
        return attackBox;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        if (!attacking) attackChecked = false;
    }

    public boolean isAttackChecked() {
        return attackChecked;
    }

    public void setAttackChecked(boolean attackChecked) {
        this.attackChecked = attackChecked;
    }

    public int getDamage() {
        return attackDamage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setInactive() {
        active = false;
    }


    public void addHealth(int health) {
        currentHealth += health;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }
}

