package com.example.gameproject.gamestates.playing;

import static com.example.gameproject.helpers.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.helpers.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.enemies.AttackingEnemy;
import com.example.gameproject.entities.enemies.DarkNinja;
import com.example.gameproject.entities.enemies.DarkWizard;
import com.example.gameproject.entities.enemies.Enemy;
import com.example.gameproject.entities.enemies.MaskedRaccoon;
import com.example.gameproject.entities.enemies.Skeleton;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.entities.particals.Particle;
import com.example.gameproject.environments.Doorway;
import com.example.gameproject.environments.MapManager;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;
import com.example.gameproject.helpers.Paints;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {

    private static MapManager mapManager;
    private final Paint redPaint, healthBarRed, healthBarBlack;
    private final Player player;
    private final PlayingUI playingUI;
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private boolean doorwayJustPassed;
    private Entity[] listOfDrawables;
    private boolean listOfEntitiesMade;
    private InventorySloth lastItem;

    public Playing(Game game) {
        super(game);
        mapManager = new MapManager(this);
        calcStartCameraValues();


        player = new Player(game);

        playingUI = new PlayingUI(this);


        redPaint = Paints.HITBOX_PAINT;
        healthBarBlack = Paints.HELTH_BAR_BLACK_PAINT;
        healthBarRed = Paints.HELTH_BAR_RED_PAINT;

    }


    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f - mapManager.getMaxWidthCurrentMap() / 2f;
        cameraY = GAME_HEIGHT / 2f - mapManager.getMaxHeightCurrentMap() / 2f;
    }

    @Override
    public void update(double delta) {
        buildEntityList();
        updatePlayerMove(delta);
        player.update(movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        checkForDoorway();
        if (player.isAttacking()) if (!player.isAttackChecked()) checkPlayerAttack();

        if (mapManager.getCurrentMap().getEnemysArrayList() != null)
            for (Character character : mapManager.getCurrentMap().getEnemysArrayList()) {
                if (character instanceof Enemy enemy) {
                    if (enemy instanceof Skeleton skeleton) updateSkeleton(delta, skeleton);
                    else if (enemy instanceof MaskedRaccoon maskedRaccoon)
                        updateMaskedRakoon(delta, maskedRaccoon);
                    else if (enemy instanceof DarkNinja darkNinja)
                        updateDarkNinja(delta, darkNinja);
                    else if (enemy instanceof DarkWizard darkWizard)
                        updateDarkWizard(delta, darkWizard);
                }
            }

        for (Building building : mapManager.getCurrentMap().getBuildingArrayList())
            for (Villager villager : building.getVillagers())
                if (villager != null) updateVillager(delta, villager);

        for (var effect : mapManager.getCurrentMap().getParticlesArrayList())
            effect.update(player);


        updateItems();
        sortArray();
        generateEnemies();

    }

    private void updateDarkWizard(double delta, DarkWizard darkWizard) {
        if (!darkWizard.isActive()) return;

        darkWizard.updtaeShuriken();
        darkWizard.update(delta, mapManager.getCurrentMap());

        if (darkWizard.isAttacking()) {
            if (!darkWizard.isAttackChecked()) {
                RectF playerHitbox = new RectF(player.getHitbox());
                playerHitbox.offset(-cameraX, -cameraY);
                if (RectF.intersects(darkWizard.getFireBallHitbox(), playerHitbox)) {
                    player.damageCharacter(darkWizard.getDamage());
                    checkPlayerDead();
                    darkWizard.setAttackChecked(true);
                }
            }
        } else if (!darkWizard.isPreparingAttack()) {
            boolean playerClose = HelpMethods.IsPlayerCloseForAttack(darkWizard, player, cameraY, cameraX);

            if (playerClose) {
                darkWizard.setMoving(false);
                darkWizard.prepareAttack(player, cameraX, cameraY);
            } else if (!darkWizard.isFireBallInFlight()) {
                darkWizard.setMoving(true);
            }
        }
    }

    private void updateDarkNinja(double delta, DarkNinja darkNinja) {
        if (!darkNinja.isActive()) return;

        darkNinja.updtaeShuriken();
        darkNinja.update(delta, mapManager.getCurrentMap());

        if (darkNinja.isAttacking()) {
            if (!darkNinja.isAttackChecked()) {
                RectF playerHitbox = new RectF(player.getHitbox());
                playerHitbox.offset(-cameraX, -cameraY);
                if (RectF.intersects(darkNinja.getShurikenHitbox(), playerHitbox)) {
                    player.damageCharacter(darkNinja.getDamage());
                    checkPlayerDead();
                    darkNinja.setAttackChecked(true);
                }
            }
        } else if (!darkNinja.isPreparingAttack()) {
            boolean playerClose = HelpMethods.IsPlayerCloseForAttack(darkNinja, player, cameraY, cameraX);

            if (playerClose) {
                darkNinja.setMoving(false);
                darkNinja.prepareAttack(player, cameraX, cameraY);
            } else if (!darkNinja.isShurikenInFlight()) {
                darkNinja.setMoving(true);
            }
        }
    }


    private void generateEnemies() {
        var map = mapManager.getCurrentMap();
        var enemies = map.getEnemysArrayList();
        int max = map.getMaxEnemies();
        if (enemies.size() == max) return;
        var temp = HelpMethods.SpawnEnemies(max - enemies.size(), map.getSpritesID(), map.getBuildingArrayList(), map.getGameObjectArrayList());
        enemies.addAll(temp);
        buildEntityList();


    }

    private void updateVillager(double delta, Villager villager) {
        if (villager.isActive()) {
            villager.update(delta, mapManager.getCurrentMap());
            if (MainActivity.getGeminiAPI().isShowText())
                if (isNearTalk(player, villager.getHitbox())) villager.startConversation();
                else villager.endConversation();
            else villager.endConversation();
        }
    }

    private boolean isNearTalk(Player player, RectF villager) {
        if (player.isInvisible()) return false;
        RectF playerHitbox = new RectF(player.getHitbox().left - cameraX, player.getHitbox().top - cameraY, player.getHitbox().right - cameraX, player.getHitbox().bottom - cameraY);
        float close = 65;

        float closestX = Math.max(villager.left - close, Math.min(playerHitbox.centerX(), villager.right + close));
        float closestY = Math.max(villager.top - close, Math.min(playerHitbox.centerY(), villager.bottom + close));

        float distanceX = playerHitbox.centerX() - closestX;
        float distanceY = playerHitbox.centerY() - closestY;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY) <= close;
    }

    private void updateItems() {
        for (Item item : mapManager.getCurrentMap().getItemArrayList()) {
            item.updateAni();
            if (isNear(player.getHitbox(), item.getHitbox()) && item.updatePickUp())
                pickItem(player, item);
        }

    }

    private void pickItem(Player player, Item item) {
        GameActivity.getMpHelper().playPickItemSound();
        player.addToInventory(item.getItemType());
        mapManager.getCurrentMap().getItemArrayList().remove(item);
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
        if (maskedRaccoon.isActive()) maskedRaccoon.update(delta, mapManager.getCurrentMap());
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
        if (character instanceof Skeleton skeleton) checkSkeletonAttack(skeleton);
        else if (character instanceof DarkNinja darkNinja) checkDarkNinjaAttack(darkNinja);
    }

    private void checkDarkNinjaAttack(DarkNinja darkNinja) {
        darkNinja.updateWepHitbox();
        RectF playerHitbox = new RectF(player.getHitbox());

        if (RectF.intersects(darkNinja.getShurikenHitbox(), playerHitbox)) {
            player.damageCharacter(darkNinja.getDamage());
            checkPlayerDead();
        }
        darkNinja.setAttackChecked(true);
    }

    private void checkSkeletonAttack(Skeleton skeleton) {
        skeleton.updateWepHitbox();
        RectF playerHitbox = new RectF(player.getHitbox());
        playerHitbox.left -= cameraX;
        playerHitbox.top -= cameraY;
        playerHitbox.right -= cameraX;
        playerHitbox.bottom -= cameraY;
        if (RectF.intersects(skeleton.getAttackBox(), playerHitbox)) {
            player.damageCharacter(skeleton.getDamage());
            checkPlayerDead();
        }
        skeleton.setAttackChecked(true);
    }

    private void checkPlayerDead() {
        if (player.getCurrentHealth() > 0) return;

        game.setCurrentGameState(Game.GameState.DEATH_SCREEN);
        player.resetCharacterHealth();
        player.resetHungerBar();
        GameActivity.getMpHelper().playGameOverSound();

    }

    private void checkPlayerAttack() {
        RectF attackBoxWithoutCamera = new RectF(player.getAttackBox());
        attackBoxWithoutCamera.left -= cameraX;
        attackBoxWithoutCamera.top -= cameraY;
        attackBoxWithoutCamera.right -= cameraX;
        attackBoxWithoutCamera.bottom -= cameraY;

        float attackBoxPadding = 13.0f;
        attackBoxWithoutCamera.inset(-attackBoxPadding, -attackBoxPadding);

        if (mapManager.getCurrentMap().getEnemysArrayList() != null) {
            for (Character character : mapManager.getCurrentMap().getEnemysArrayList()) {
                if (attackBoxWithoutCamera.intersects(character.getHitbox().left, character.getHitbox().top, character.getHitbox().right, character.getHitbox().bottom)) {
                    character.damageCharacter(player.getDamage());
                    if (character instanceof Enemy enemy) {
                        if (enemy.getCurrentHealth() <= 0) {
                            enemy.setInactive();
                            if (!enemy.isAddedLoot()) {
                                mapManager.getCurrentMap().getItemArrayList().addAll(enemy.getLoot(new PointF(enemy.getHitbox().left, enemy.getHitbox().top)));
                                enemy.setAddedLoot(true);
                            }
                            mapManager.getCurrentMap().getEnemysArrayList().remove(enemy);
                        }
                    }
                }
            }
        }
        for (Building building : mapManager.getCurrentMap().getBuildingArrayList())
            for (Villager villager : building.getVillagers()) {
                if (villager == null) continue;
                if (attackBoxWithoutCamera.intersects(villager.getHitbox().left, villager.getHitbox().top, villager.getHitbox().right, villager.getHitbox().bottom)) {
                    villager.damageCharacter(player.getDamage());
                    if (villager.getCurrentHealth() <= 0) {
                        villager.setInactive();
                        building.removeVillager(villager);
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

    private void drawVillager(Canvas canvas, Villager villager) {
        if (villager.isActive()) {
            canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), villager.getHitbox().left + cameraX, villager.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
            canvas.drawBitmap(villager.getGameCharType().getSprite(villager.getAniIndex(), villager.getFaceDir()), villager.getHitbox().left + cameraX - X_DRAW_OFFSET, villager.getHitbox().top + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
            if (GameActivity.isDrawHitbox())
                canvas.drawRect(villager.getHitbox().left + cameraX, villager.getHitbox().top + cameraY, villager.getHitbox().right + cameraX, villager.getHitbox().bottom + cameraY, redPaint);

            if (villager.getCurrentHealth() < villager.getMaxHealth())
                drawHealthBar(canvas, villager);

            if (villager.isTalking()) villager.drawTalk(canvas, cameraX, cameraY);
        }
    }

    private void drawSortedEntities(Canvas canvas) {
        for (Entity e : listOfDrawables) {
            if (e instanceof Enemy enemy) {
                if (enemy instanceof Skeleton skeleton) {
                    if (skeleton.isActive()) drawEnemy(canvas, skeleton);
                } else if (enemy instanceof MaskedRaccoon maskedRaccoon) {
                    if (maskedRaccoon.isActive()) drawEnemy(canvas, maskedRaccoon);
                } else if (enemy instanceof DarkNinja darkNinja) {
                    if (darkNinja.isActive()) drawEnemy(canvas, darkNinja);
                } else if (enemy instanceof DarkWizard darkWizard) {
                    if (darkWizard.isActive()) drawEnemy(canvas, darkWizard);
                }
            } else if (e instanceof GameObject gameObject) {
                mapManager.drawObject(canvas, gameObject);
            } else if (e instanceof Building building) {
                mapManager.drawBuilding(canvas, building);
            } else if (e instanceof Item item) {
                mapManager.drawItem(canvas, item);
            } else if (e instanceof Particle particle) {
                particle.draw(canvas);
            } else if (e instanceof Villager villager) {
                drawVillager(canvas, villager);
            } else if (e instanceof Player) {
                drawPlayer(canvas);
            }
        }
    }


    private void drawPlayer(Canvas canvas) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left, player.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        canvas.drawBitmap(player.getSkin().getSprite(player.getAniIndex(), player.getFaceDir()), player.getHitbox().left - X_DRAW_OFFSET, player.getHitbox().top - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.isDrawHitbox()) canvas.drawRect(player.getHitbox(), redPaint);
        if (player.isAttacking()) drawWeapon(canvas, player);
    }

    private void drawWeapon(Canvas canvas, Character character) {
        canvas.rotate(character.getWepRot(), character.getAttackBox().left, character.getAttackBox().top);
        canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), character.getAttackBox().left + character.wepRotAdjustLeft(), character.getAttackBox().top + character.wepRotAdjustTop(), null);
        canvas.rotate(character.getWepRot() * -1, character.getAttackBox().left, character.getAttackBox().top);
        if (GameActivity.isDrawHitbox()) canvas.drawRect(character.getAttackBox(), redPaint);
    }

    private void drawEnemyWeapon(Canvas canvas, AttackingEnemy attackingEnemy) {
        if (attackingEnemy instanceof Skeleton) {
            canvas.rotate(attackingEnemy.getWepRot(), attackingEnemy.getAttackBox().left + cameraX, attackingEnemy.getAttackBox().top + cameraY);
            canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(), attackingEnemy.getAttackBox().left + cameraX + attackingEnemy.wepRotAdjustLeft(), attackingEnemy.getAttackBox().top + cameraY + attackingEnemy.wepRotAdjustTop(), null);
            canvas.rotate(attackingEnemy.getWepRot() * -1, attackingEnemy.getAttackBox().left + cameraX, attackingEnemy.getAttackBox().top + cameraY);
            if (GameActivity.isDrawHitbox())
                //  not true ): need fix!
                // when weapon is facing left or up, the hitbox is bigger.
                // not effecting the game and real hitbox IDK why.
                // I FIXED IT! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //Just didnt need to add the wep adjustment to the hitbox becose it was already applying when i create the wep hitbox..
                canvas.drawRect(attackingEnemy.getAttackBox().left + cameraX, attackingEnemy.getAttackBox().top + cameraY, attackingEnemy.getAttackBox().right + cameraX, attackingEnemy.getAttackBox().bottom + cameraY, redPaint);
        } else if (attackingEnemy instanceof DarkNinja darkNinja) {
            darkNinja.drawShuriken(canvas, cameraX, cameraY);
        } else if (attackingEnemy instanceof DarkWizard darkWizard) {
            darkWizard.drawFireBall(canvas, cameraX, cameraY);
        }
    }

    public void drawEnemy(Canvas canvas, Character enemy) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), enemy.getHitbox().left + cameraX, enemy.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(enemy.getEnemyType().getSprite(enemy.getAniIndex(), enemy.getFaceDir()), enemy.getHitbox().left + cameraX - X_DRAW_OFFSET, enemy.getHitbox().top + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(enemy.getHitbox().left + cameraX, enemy.getHitbox().top + cameraY, enemy.getHitbox().right + cameraX, enemy.getHitbox().bottom + cameraY, redPaint);

        if (enemy instanceof AttackingEnemy attackingEnemy) {
            if (enemy.isAttacking())
                drawEnemyWeapon(canvas, attackingEnemy);
        }

        if (enemy.getCurrentHealth() < enemy.getMaxHealth())
            drawHealthBar(canvas, enemy);


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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (int i = 0; i < player.getInventory().length; i++)
                if (player.getInventory()[i][player.getInventory()[i].length - 1].getItem() != null)
                    if (player.getInventory()[i][player.getInventory()[i].length - 1].isIn(event))
                        if (lastItem == null)
                            lastItem = player.getInventory()[i][player.getInventory()[i].length - 1];
                        else if (lastItem == player.getInventory()[i][player.getInventory()[i].length - 1])
                            player.UseItem(lastItem);
                        else
                            lastItem = player.getInventory()[i][player.getInventory()[i].length - 1];

        }

        playingUI.touchEvents(event);
    }

    public void resetLastItem() {
        lastItem = null;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayingUI getPlayingUI() {
        return playingUI;
    }

    public void setGameStateToSettings() {
        game.setCurrentGameState(Game.GameState.SETTING);
    }

    public void setGameStateToInventory() {
        game.setCurrentGameState(Game.GameState.INVENTORY);
    }

    public void setGameStateToShop() {
        game.setCurrentGameState(Game.GameState.SHOP);
    }
}