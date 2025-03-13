package com.example.gameproject.gamestates.playing;

import static com.example.gameproject.helpers.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.helpers.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.enemies.Enemy;
import com.example.gameproject.entities.enemies.MaskedRaccoon;
import com.example.gameproject.entities.enemies.Skeleton;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.environments.Doorway;
import com.example.gameproject.environments.MapManager;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {

    private final Paint redPaint, healthBarRed, healthBarBlack;
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private final Player player;
    private final PlayingUI playingUI;
    private boolean doorwayJustPassed;
    private Entity[] listOfDrawables;
    private boolean listOfEntitiesMade;

    private static MapManager mapManager;

    public Playing(Game game) {
        super(game);
        mapManager = new MapManager(this);
        calcStartCameraValues();



        player = new Player();

        playingUI = new PlayingUI(this);


        redPaint = new Paint();
        redPaint.setStrokeWidth(3);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);

        healthBarRed = new Paint();
        healthBarBlack = new Paint();
        initHealthBars();
    }

    private void initHealthBars() {
        healthBarRed.setStrokeWidth(10);
        healthBarRed.setStyle(Paint.Style.STROKE);
        healthBarRed.setColor(Color.RED);
        healthBarBlack.setStrokeWidth(14);
        healthBarBlack.setStyle(Paint.Style.STROKE);
        healthBarBlack.setColor(Color.BLACK);

    }

    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f - mapManager.getMaxWidthCurrentMap() / 2f;
        cameraY = GAME_HEIGHT / 2f - mapManager.getMaxHeightCurrentMap() / 2f;
    }


    @Override
    public void update(double delta) {
        buildEntityList();
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForDoorway();
        if (player.isAttacking()) if (!player.isAttackChecked()) checkPlayerAttack();

        if (mapManager.getCurrentMap().getEnemysArrayList() != null)
            for (Character character : mapManager.getCurrentMap().getEnemysArrayList()) {
                if (character instanceof Enemy enemy) {
                    if (enemy instanceof Skeleton skeleton) updateSkeleton(delta, skeleton);
                    if (enemy instanceof MaskedRaccoon maskedRaccoon)
                        updateMaskedRakoon(delta, maskedRaccoon);
                }
            }

        updatePickedItems();


        sortArray();

    }

    private void updatePickedItems() {
        for (Item item : mapManager.getCurrentMap().getItemArrayList())
            if (isNear(player.getHitbox(), item.getHitbox())) pickItem(player, item);

    }

    private void pickItem(Player player, Item item) {
        switch (item.getItemType()) {
            case COIN -> {
                player.updateSQL(item);
                mapManager.getCurrentMap().getItemArrayList().remove(item);
            }

        }
        game.getInventoryState().SyncInventories(player);
    }

    private boolean isNear(RectF in, RectF out) {
        RectF playerHitbox = new RectF(in.left - cameraX, in.top - cameraY, in.right - cameraX, in.bottom - cameraY);
        float close = 50;

        float closestX = Math.max(out.left - close, Math.min(playerHitbox.centerX(), out.right + close));
        float closestY = Math.max(out.top - close, Math.min(playerHitbox.centerY(), out.bottom + close));

        float distanceX = playerHitbox.centerX() - closestX;
        float distanceY = playerHitbox.centerY() - closestY;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY) < close;
    }


    private void updateMaskedRakoon(double delta, MaskedRaccoon maskedRaccoon) {
        if (maskedRaccoon.isActive()) {
            maskedRaccoon.update(delta, mapManager.getCurrentMap());
        }

    }

    private void updateSkeleton(double delta, Skeleton skeleton) {
        if (skeleton.isActive()) {
            skeleton.update(delta, mapManager.getCurrentMap());
            if (skeleton.isAttacking()) {
                if (!skeleton.isAttackChecked()) {
                    checkEnemyAttack(skeleton);
                }
            } else if (!skeleton.isPreparingAttack()) {
                if (HelpMethods.IsPlayerCloseForAttack(skeleton, player, cameraY, cameraX)) {
                    skeleton.prepareAttack(player, cameraX, cameraY);
                }
            }
        }
    }


    private void buildEntityList() {
        listOfDrawables = mapManager.getCurrentMap().getDrawableList();
        listOfDrawables[listOfDrawables.length - 1] = player;
        listOfEntitiesMade = true;
    }

    private void sortArray() {
        player.setLastCameraYValue(cameraY);
        Arrays.sort(listOfDrawables);
    }

    public void setCameraValues(PointF cameraPos) {
        this.cameraX = cameraPos.x;
        this.cameraY = cameraPos.y;
    }

    private void checkForDoorway() {
        Doorway doorwayPlayerIsOn = mapManager.isPlayerOnDoorway(player.getHitbox());

        if (doorwayPlayerIsOn != null) {
            if (!doorwayJustPassed) mapManager.changeMap(doorwayPlayerIsOn.getDoorwayConnectedTo());
        } else doorwayJustPassed = false;

    }

    public void setDoorwayJustPassed(boolean doorwayJustPassed) {
        this.doorwayJustPassed = doorwayJustPassed;
    }


    private void checkEnemyAttack(Character character) {
        character.updateWepHitbox();
        RectF playerHitbox = new RectF(player.getHitbox());
        playerHitbox.left -= cameraX;
        playerHitbox.top -= cameraY;
        playerHitbox.right -= cameraX;
        playerHitbox.bottom -= cameraY;
        if (RectF.intersects(character.getAttackBox(), playerHitbox)) {
            player.damageCharacter(character.getDamage());
            checkPlayerDead();
        }
        character.setAttackChecked(true);
    }

    private void checkPlayerDead() {
        if (player.getCurrentHealth() > 0) return;

        game.setCurrentGameState(Game.GameState.DEATH_SCREEN);
        player.resetCharacterHealth();

    }

    private void checkPlayerAttack() {

        RectF attackBoxWithoutCamera = new RectF(player.getAttackBox());
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        if (mapManager.getCurrentMap().getEnemysArrayList() != null)
            for (Character character : mapManager.getCurrentMap().getEnemysArrayList())
                if (attackBoxWithoutCamera.intersects(character.getHitbox().left, character.getHitbox().top, character.getHitbox().right, character.getHitbox().bottom)) {
                    character.damageCharacter(player.getDamage());
                    if (character instanceof Enemy enemy) {
                        if (enemy.getCurrentHealth() <= 0) {
                            enemy.setInactive();
                            if (!enemy.isAddedLoot()) {
                                mapManager.getCurrentMap().getItemArrayList().addAll(enemy.getLoot(new PointF(enemy.getHitbox().left, enemy.getHitbox().top)));
                                enemy.setAddedLoot(true);
                            }
                        }
                    }

                }

        player.setAttackChecked(true);
    }


    @Override
    public void render(Canvas canvas) {
        mapManager.drawTiles(canvas);
        if (listOfEntitiesMade) drawSortedEntities(canvas);

        playingUI.draw(canvas);
    }

    private void drawSortedEntities(Canvas canvas) {
        for (Entity e : listOfDrawables) {
            if (e instanceof Enemy enemy) {
                if (enemy instanceof Skeleton skeleton) {
                    if (skeleton.isActive()) drawCharacter(canvas, skeleton);
                } else if (enemy instanceof MaskedRaccoon maskedRaccoon) {
                    if (maskedRaccoon.isActive()) drawCharacter(canvas, maskedRaccoon);
                }
            } else if (e instanceof GameObject gameObject) {
                mapManager.drawObject(canvas, gameObject);
            } else if (e instanceof Building building) {
                mapManager.drawBuilding(canvas, building);
            } else if (e instanceof Item item) {
                mapManager.drawItem(canvas, item);
            } else if (e instanceof Player) {
                drawPlayer(canvas);
            }
        }
    }


    private void drawPlayer(Canvas canvas) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left, player.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        canvas.drawBitmap(player.getSkin().getSprite(player.getAniIndex(), player.getFaceDir()), player.getHitbox().left - X_DRAW_OFFSET, player.getHitbox().top - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.getDrawHitbox()) canvas.drawRect(player.getHitbox(), redPaint);
        if (player.isAttacking()) drawWeapon(canvas, player);
    }


    private void drawWeapon(Canvas canvas, Character character) {
        canvas.rotate(character.getWepRot(), character.getAttackBox().left, character.getAttackBox().top);
        canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + character.wepRotAdjustLeft(), character.getAttackBox().top + character.wepRotAdjustTop(), null);
        canvas.rotate(character.getWepRot() * -1, character.getAttackBox().left, character.getAttackBox().top);
        if (GameActivity.getDrawHitbox()) canvas.drawRect(character.getAttackBox(), redPaint);
    }

    private void drawEnemyWeapon(Canvas canvas, Character character) {
        canvas.rotate(character.getWepRot(), character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
        canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + cameraX + character.wepRotAdjustLeft(), character.getAttackBox().top + cameraY + character.wepRotAdjustTop(), null);
        canvas.rotate(character.getWepRot() * -1, character.getAttackBox().left + cameraX, character.getAttackBox().top + cameraY);
        if (GameActivity.getDrawHitbox())
            // TODO:      not true ): need fix!
            // when weapon is facing left or up, the hitbox is bigger.
            // not effecting the game and real hitbox IDK why.
            canvas.drawRect(character.getAttackBox().left + cameraX + character.wepRotAdjustLeft(), character.getAttackBox().top + cameraY + character.wepRotAdjustTop(), character.getAttackBox().right + cameraX, character.getAttackBox().bottom + cameraY, redPaint);
    }


    public void drawCharacter(Canvas canvas, Character character) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), character.getHitbox().left + cameraX, character.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(character.getEnemyType().getSprite(character.getAniIndex(), character.getFaceDir()), character.getHitbox().left + cameraX - X_DRAW_OFFSET, character.getHitbox().top + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.getDrawHitbox())
            canvas.drawRect(character.getHitbox().left + cameraX, character.getHitbox().top + cameraY, character.getHitbox().right + cameraX, character.getHitbox().bottom + cameraY, redPaint);
        if (character.isAttacking()) drawEnemyWeapon(canvas, character);

        if (character.getCurrentHealth() < character.getMaxHealth())
            drawHealthBar(canvas, character);


    }

    private void drawHealthBar(Canvas canvas, Character character) {
        canvas.drawLine(character.getHitbox().left + cameraX, character.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, character.getHitbox().right + cameraX, character.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, healthBarBlack);

        float fullBarWidth = character.getHitbox().width();
        float percentOfMaxHealth = (float) character.getCurrentHealth() / character.getMaxHealth();
        float barWidth = fullBarWidth * percentOfMaxHealth;
        float xDelta = (fullBarWidth - barWidth) / 2.0f;


        canvas.drawLine(character.getHitbox().left + cameraX + xDelta, character.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, character.getHitbox().left + cameraX + xDelta + barWidth, character.getHitbox().top + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, healthBarRed);
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer) return;

        float baseSpeed = (float) (delta * 300);
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

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, mapManager.getCurrentMap())) {
            cameraX += deltaX;
            cameraY += deltaY;
        } else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, cameraX * -1, mapManager.getCurrentMap()))
                cameraY += deltaY;

            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, cameraY * -1, mapManager.getCurrentMap()))
                cameraX += deltaX;
        }
    }


    public void setPlayerMoveTrue(PointF lastTouchDiff) {
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse() {
        movePlayer = false;
        player.resetAnimation();
    }

    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayingUI getPlayingUI() {
        return playingUI;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public void setGameStateToSettings() {
        game.setCurrentGameState(Game.GameState.SETTINGS);
    }

    public void setGameStateToInventory() {
        game.setCurrentGameState(Game.GameState.INVENTORY);
    }

    public void setGameStateToShop() {
        game.setCurrentGameState(Game.GameState.SHOP);
    }

    public void setGameStateToDebug() {
        game.setCurrentGameState(Game.GameState.DEBUG);
    }
}
