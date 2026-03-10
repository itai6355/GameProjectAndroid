package com.example.gameproject.gamestates.playing;

import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.helpers.var.GameConstants.Sprite.X_DRAW_OFFSET;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.content.Intent;
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
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.Weapons;
import com.example.gameproject.entities.particals.Particle;
import com.example.gameproject.entities.particals.Particles;
import com.example.gameproject.environments.Doorway;
import com.example.gameproject.environments.MapManager;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.gamestates.setting.SettingActivity;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.HelpMethods;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Arrays;

public class Playing extends BaseState implements GameStateInterface {

    private static MapManager mapManager;
    private final Paint redPaint =  Paints.HITBOX_PAINT, healthBarRed= Paints.HELTH_BAR_BLACK_PAINT, healthBarBlack=Paints.HELTH_BAR_RED_PAINT;
    private final Player player;
    private final PlayingUI playingUI;
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private boolean doorwayJustPassed;
    private Entity[] listOfDrawables;
    private boolean listOfEntitiesMade;
    private InventorySloth lastItem;

    private int playerId = -1;

    private final Particle playerParticle = new Particle(Particles.POTION_EFFECT);

    public Playing(Game game) {
        super(game);
        mapManager = new MapManager(this);
        calcStartCameraValues();
        player = new Player(game);
        this.playerId = player.getId();
        playingUI = new PlayingUI(this);
        mapManager.loadWorldFromDatabase(playerId);
    }

    public void saveWorld() {
        mapManager.saveWorldToDatabase(playerId);
    }

    public boolean build(Items item) {
        return mapManager.build(item, playerId);
    }


    private void calcStartCameraValues() {
        cameraX = GAME_WIDTH / 2f  - mapManager.getMaxWidthCurrentMap()  / 2f;
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
                    if (enemy instanceof Skeleton skeleton)           updateSkeleton(delta, skeleton);
                    else if (enemy instanceof MaskedRaccoon mr)       updateMaskedRakoon(delta, mr);
                    else if (enemy instanceof DarkNinja dn)           updateDarkNinja(delta, dn);
                    else if (enemy instanceof DarkWizard dw)          updateDarkWizard(delta, dw);
                }
            }

        for (Building building : mapManager.getCurrentMap().getBuildingArrayList())
            for (Villager villager : building.getVillagers())
                if (villager != null) updateVillager(delta, villager);

        playerParticle.update(player);
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
            if (playerClose) { darkWizard.setMoving(false); darkWizard.prepareAttack(player, cameraX, cameraY); }
            else if (!darkWizard.isFireBallInFlight()) darkWizard.setMoving(true);
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
            if (playerClose) { darkNinja.setMoving(false); darkNinja.prepareAttack(player, cameraX, cameraY); }
            else if (!darkNinja.isShurikenInFlight()) darkNinja.setMoving(true);
        }
    }

    private void generateEnemies() {
        var map     = mapManager.getCurrentMap();
        var enemies = map.getEnemysArrayList();
        int max     = map.getMaxEnemies();
        if (enemies.size() == max) return;
        var temp = HelpMethods.SpawnEnemies(max - enemies.size(), map.getSpritesID(),
                map.getBuildingArrayList(), map.getGameObjectArrayList());
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
        RectF ph = new RectF(player.getHitbox().left - cameraX, player.getHitbox().top - cameraY,
                player.getHitbox().right - cameraX, player.getHitbox().bottom - cameraY);
        float close = 65;
        float cx = Math.max(villager.left - close, Math.min(ph.centerX(), villager.right  + close));
        float cy = Math.max(villager.top  - close, Math.min(ph.centerY(), villager.bottom + close));
        return Math.sqrt((ph.centerX() - cx) * (ph.centerX() - cx) + (ph.centerY() - cy) * (ph.centerY() - cy)) <= close;
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
        RectF ph = new RectF(in.left - cameraX, in.top - cameraY, in.right - cameraX, in.bottom - cameraY);
        float close = 50;
        float cx = Math.max(out.left - close, Math.min(ph.centerX(), out.right  + close));
        float cy = Math.max(out.top  - close, Math.min(ph.centerY(), out.bottom + close));
        return Math.sqrt((ph.centerX() - cx) * (ph.centerX() - cx) + (ph.centerY() - cy) * (ph.centerY() - cy)) < close;
    }

    private void updateMaskedRakoon(double delta, MaskedRaccoon mr) {
        if (mr.isActive()) mr.update(delta, mapManager.getCurrentMap());
    }

    private void updateSkeleton(double delta, Skeleton skeleton) {
        if (skeleton.isActive()) {
            skeleton.update(delta, mapManager.getCurrentMap());
            if (skeleton.isAttacking()) {
                if (!skeleton.isAttackChecked()) checkEnemyAttack(skeleton);
            } else if (!skeleton.isPreparingAttack()) {
                if (HelpMethods.IsPlayerCloseForAttack(skeleton, player, cameraY, cameraX))
                    skeleton.prepareAttack(player, cameraX, cameraY);
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
        Arrays.sort(listOfDrawables, (a, b) -> {
            boolean aW = a instanceof GameObject && ((GameObject) a).getObjectType().isWalkable();
            boolean bW = b instanceof GameObject && ((GameObject) b).getObjectType().isWalkable();
            if (aW == bW) return a.compareTo(b);
            return aW ? -1 : 1;
        });
    }

    public void setCameraValues(PointF cameraPos) {
        this.cameraX = cameraPos.x;
        this.cameraY = cameraPos.y;
    }

    private void checkForDoorway() {
        RectF playerWorldHitbox = new RectF(
                player.getHitbox().left   - cameraX, player.getHitbox().top    - cameraY,
                player.getHitbox().right  - cameraX, player.getHitbox().bottom - cameraY);
        Doorway doorwayPlayerIsOn = mapManager.isPlayerOnDoorway(playerWorldHitbox);
        if (doorwayPlayerIsOn != null) {
            if (!doorwayJustPassed) mapManager.changeMap(doorwayPlayerIsOn);
        } else {
            doorwayJustPassed = false;
        }
    }

    public void setDoorwayJustPassed(boolean doorwayJustPassed) {
        this.doorwayJustPassed = doorwayJustPassed;
    }

    private void checkEnemyAttack(Character character) {
        if (character instanceof Skeleton skeleton) checkSkeletonAttack(skeleton);
        else if (character instanceof DarkNinja darkNinja) checkDarkNinjaAttack(darkNinja);
    }

    private void checkDarkNinjaAttack(DarkNinja dn) {
        dn.updateWepHitbox();
        if (RectF.intersects(dn.getShurikenHitbox(), player.getHitbox())) {
            player.damageCharacter(dn.getDamage());
            checkPlayerDead();
        }
        dn.setAttackChecked(true);
    }

    private void checkSkeletonAttack(Skeleton skeleton) {
        skeleton.updateWepHitbox();
        RectF ph = new RectF(player.getHitbox());
        ph.offset(-cameraX, -cameraY);
        if (RectF.intersects(skeleton.getAttackBox(), ph)) {
            player.damageCharacter(skeleton.getDamage());
            checkPlayerDead();
        }
        skeleton.setAttackChecked(true);
    }

    private void checkPlayerDead() {
        if (player.getCurrentHealth() > 0) return;
        game.getDbHelper().clearSavedEnemies(playerId);
        game.setCurrentGameState(Game.GameState.DEATH_SCREEN);
        player.resetCharacterHealth();
        player.resetHungerBar();
        GameActivity.getMpHelper().playGameOverSound();
    }

    private void checkPlayerAttack() {
        RectF atk = new RectF(player.getAttackBox());
        atk.offset(-cameraX, -cameraY);
        atk.inset(-13f, -13f);

        if (mapManager.getCurrentMap().getEnemysArrayList() != null)
            for (Character c : mapManager.getCurrentMap().getEnemysArrayList()) {
                if (atk.intersects(c.getHitbox().left, c.getHitbox().top, c.getHitbox().right, c.getHitbox().bottom)) {
                    c.damageCharacter(player.getDamage());
                    if (c instanceof Enemy enemy && enemy.getCurrentHealth() <= 0) {
                        enemy.setInactive();
                        if (!enemy.isAddedLoot()) {
                            mapManager.getCurrentMap().getItemArrayList()
                                    .addAll(enemy.getLoot(new PointF(enemy.getHitbox().left, enemy.getHitbox().top)));
                            enemy.setAddedLoot(true);
                        }
                        mapManager.getCurrentMap().getEnemysArrayList().remove(enemy);
                    }
                }
            }

        for (Building building : mapManager.getCurrentMap().getBuildingArrayList())
            for (Villager villager : building.getVillagers()) {
                if (villager == null) continue;
                if (atk.intersects(villager.getHitbox().left, villager.getHitbox().top,
                        villager.getHitbox().right, villager.getHitbox().bottom)) {
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
        if (!villager.isActive()) return;
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), villager.getHitbox().left + cameraX,
                villager.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(villager.getGameCharType().getSprite(villager.getAniIndex(), villager.getFaceDir()),
                villager.getHitbox().left + cameraX - X_DRAW_OFFSET,
                villager.getHitbox().top  + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(villager.getHitbox().left + cameraX, villager.getHitbox().top + cameraY,
                    villager.getHitbox().right + cameraX, villager.getHitbox().bottom + cameraY, redPaint);
        if (villager.getCurrentHealth() < villager.getMaxHealth()) drawHealthBar(canvas, villager);
        if (villager.isTalking()) villager.drawTalk(canvas, cameraX, cameraY);
    }

    private void drawSortedEntities(Canvas canvas) {
        for (Entity e : listOfDrawables) {
            if (e instanceof Enemy enemy) {
                if (enemy instanceof Skeleton s   && s.isActive())  drawEnemy(canvas, s);
                else if (enemy instanceof MaskedRaccoon mr && mr.isActive()) drawEnemy(canvas, mr);
                else if (enemy instanceof DarkNinja dn    && dn.isActive())  drawEnemy(canvas, dn);
                else if (enemy instanceof DarkWizard dw   && dw.isActive())  drawEnemy(canvas, dw);
            } else if (e instanceof GameObject go)  { mapManager.drawObject(canvas, go); }
            else if (e instanceof Building b)        { mapManager.drawBuilding(canvas, b); }
            else if (e instanceof Item item)         { mapManager.drawItem(canvas, item); }
            else if (e instanceof Villager v)        { drawVillager(canvas, v); }
            else if (e instanceof Player)            { drawPlayer(canvas); playerParticle.draw(canvas); }
        }
        if (GameActivity.isDrawHitbox()) mapManager.drawDoorways(canvas, cameraX, cameraY);
    }

    private void drawPlayer(Canvas canvas) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), player.getHitbox().left,
                player.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        canvas.drawBitmap(player.getSkin().getSprite(player.getAniIndex(), player.getFaceDir()),
                player.getHitbox().left - X_DRAW_OFFSET,
                player.getHitbox().top  - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.isDrawHitbox()) canvas.drawRect(player.getHitbox(), redPaint);
        if (player.isAttacking()) drawWeapon(canvas, player);
    }

    private void drawWeapon(Canvas canvas, Character c) {
        canvas.rotate(c.getWepRot(), c.getAttackBox().left, c.getAttackBox().top);
        canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(),
                c.getAttackBox().left + c.wepRotAdjustLeft(),
                c.getAttackBox().top  + c.wepRotAdjustTop(), null);
        canvas.rotate(-c.getWepRot(), c.getAttackBox().left, c.getAttackBox().top);
        if (GameActivity.isDrawHitbox()) canvas.drawRect(c.getAttackBox(), redPaint);
    }

    private void drawEnemyWeapon(Canvas canvas, AttackingEnemy ae) {
        if (ae instanceof Skeleton) {
            canvas.rotate(ae.getWepRot(), ae.getAttackBox().left + cameraX, ae.getAttackBox().top + cameraY);
            canvas.drawBitmap(Weapons.BIG_SWORD.getWeaponImg(),
                    ae.getAttackBox().left + cameraX + ae.wepRotAdjustLeft(),
                    ae.getAttackBox().top  + cameraY + ae.wepRotAdjustTop(), null);
            canvas.rotate(-ae.getWepRot(), ae.getAttackBox().left + cameraX, ae.getAttackBox().top + cameraY);
            if (GameActivity.isDrawHitbox())
                canvas.drawRect(ae.getAttackBox().left + cameraX, ae.getAttackBox().top + cameraY,
                        ae.getAttackBox().right + cameraX, ae.getAttackBox().bottom + cameraY, redPaint);
        } else if (ae instanceof DarkNinja dn)   dn.drawShuriken(canvas, cameraX, cameraY);
        else if (ae instanceof DarkWizard dw)    dw.drawFireBall(canvas, cameraX, cameraY);
    }

    public void drawEnemy(Canvas canvas, Character enemy) {
        canvas.drawBitmap(Weapons.SHADOW.getWeaponImg(), enemy.getHitbox().left + cameraX,
                enemy.getHitbox().bottom - 5 * GameConstants.Sprite.SCALE_MULTIPLIER + cameraY, null);
        canvas.drawBitmap(enemy.getEnemyType().getSprite(enemy.getAniIndex(), enemy.getFaceDir()),
                enemy.getHitbox().left + cameraX - X_DRAW_OFFSET,
                enemy.getHitbox().top  + cameraY - GameConstants.Sprite.Y_DRAW_OFFSET, null);
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(enemy.getHitbox().left + cameraX, enemy.getHitbox().top + cameraY,
                    enemy.getHitbox().right + cameraX, enemy.getHitbox().bottom + cameraY, redPaint);
        if (enemy instanceof AttackingEnemy ae && enemy.isAttacking()) drawEnemyWeapon(canvas, ae);
        if (enemy.getCurrentHealth() < enemy.getMaxHealth()) drawHealthBar(canvas, enemy);
    }

    private void drawHealthBar(Canvas canvas, Character c) {
        float left  = c.getHitbox().left   + cameraX;
        float right = c.getHitbox().right  + cameraX;
        float top   = c.getHitbox().top    + cameraY - 5 * GameConstants.Sprite.SCALE_MULTIPLIER;
        canvas.drawLine(left, top, right, top, healthBarBlack);
        float full    = c.getHitbox().width();
        float barW    = full * ((float) c.getCurrentHealth() / c.getMaxHealth());
        float xDelta  = (full - barW) / 2f;
        canvas.drawLine(left + xDelta, top, left + xDelta + barW, top, healthBarRed);
    }

    private void updatePlayerMove(double delta) {
        if (!movePlayer) return;
        float baseSpeed = (float) (delta * 300 * player.getSPEED());
        float ratio     = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle    = Math.atan(ratio);
        float xSpeed    = (float) Math.cos(angle);
        float ySpeed    = (float) Math.sin(angle);

        if (xSpeed > ySpeed) player.setFaceDir(lastTouchDiff.x > 0 ? GameConstants.Face_Dir.RIGHT : LEFT);
        else                 player.setFaceDir(lastTouchDiff.y > 0 ? GameConstants.Face_Dir.DOWN  : GameConstants.Face_Dir.UP);

        if (lastTouchDiff.x < 0) xSpeed *= -1;
        if (lastTouchDiff.y < 0) ySpeed *= -1;

        float deltaX      = xSpeed * baseSpeed * -1;
        float deltaY      = ySpeed * baseSpeed * -1;
        float deltaCameraX = -cameraX + deltaX * -1;
        float deltaCameraY = -cameraY + deltaY * -1;

        if (HelpMethods.CanWalkHere(player.getHitbox(), deltaCameraX, deltaCameraY, mapManager.getCurrentMap())) {
            cameraX += deltaX;
            cameraY += deltaY;
        } else {
            if (HelpMethods.CanWalkHereUpDown(player.getHitbox(), deltaCameraY, -cameraX, mapManager.getCurrentMap()))
                cameraY += deltaY;
            if (HelpMethods.CanWalkHereLeftRight(player.getHitbox(), deltaCameraX, -cameraY, mapManager.getCurrentMap()))
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
            outer:
            for (InventorySloth[] slotRow : player.getInventory()) {
                for (InventorySloth slot : slotRow) {
                    if (slot.getItem() != null && slot.isIn(event)) {
                        if (lastItem == null) {
                            lastItem = slot;
                        } else if (lastItem == slot) {
                            if (slot.getItem() != null && slot.getAmount() > 0) player.UseItem(lastItem);
                            lastItem = null;
                        } else {
                            lastItem = slot;
                        }
                        break outer;
                    }
                }
            }
        }
        playingUI.touchEvents(event);
    }

    public void resetLastItem() { lastItem = null; }
    public Player getPlayer()   { return player; }
    public MapManager getMapManager() { return mapManager; }
    public int getPlayerId()    { return playerId; }

    public void setGameStateToSettings() {
        game.getGameLoop().pauseGameLoop();
        MainActivity.getGameContext().startActivity(
                new Intent(MainActivity.getGameContext(), SettingActivity.class));
    }

    public void setGameStateToInventory() { game.setCurrentGameState(Game.GameState.INVENTORY); }
    public void setGameStateToShop()      { game.setCurrentGameState(Game.GameState.SHOP); }
}