package com.example.gameproject.environments;

import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.DOWN;
import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.UP;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.Buildings;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.GameObjects;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.List;
import java.util.Objects;

public class MapManager {

    private GameMap currentMap;
    private final GameMap outsideMap;
    private float cameraX, cameraY;
    private final Playing playing;
    private final RectF spawnZone;

    float protectedRadius = GameConstants.Sprite.SIZE * 1.5f;

    public MapManager(Playing playing) {
        this.playing = playing;
        outsideMap = new GameMap(Tiles.OUTSIDE);
        currentMap = outsideMap;

        float spawnX = outsideMap.getMapWidth() / 2f;
        float spawnY = outsideMap.getMapHeight() / 2f;
        spawnZone = new RectF(
                spawnX - protectedRadius, spawnY - protectedRadius,
                spawnX + protectedRadius, spawnY + protectedRadius);
    }

    public void loadWorldFromDatabase(int playerId) {
        var db = MainActivity.getDbHelper();

        List<GameObject> savedObjects = db.getAllObjects(playerId);
        outsideMap.getGameObjectArrayList().addAll(savedObjects);

        List<Building> savedBuildings = db.getAllBuildings(playerId, outsideMap);
        outsideMap.getBuildingArrayList().addAll(savedBuildings);

        var savedEnemies = db.getAllEnemies(playerId);
        if (!savedEnemies.isEmpty()) {
            outsideMap.getEnemysArrayList().clear();
            outsideMap.getEnemysArrayList().addAll(savedEnemies);
        }

        db.loadTileMap(playerId, outsideMap.getSpritesID());
    }

    public void saveWorldToDatabase(int playerId) {
        var db = MainActivity.getDbHelper();
        db.saveAllBuildings(playerId, outsideMap.getBuildingArrayList());
        db.saveAllObjects(playerId, outsideMap.getGameObjectArrayList());
        db.saveAllEnemies(playerId, outsideMap.getEnemysArrayList());

        db.saveTileMap(playerId, outsideMap.getSpritesID(), outsideMap.getOriginalSpriteIds());
    }

    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public int getMaxWidthCurrentMap() {
        return currentMap.getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMaxHeightCurrentMap() {
        return currentMap.getArrayHeight() * GameConstants.Sprite.SIZE;
    }

    public void drawItem(Canvas canvas, Item item) {
        if (!item.getItemType().isAni())
            canvas.drawBitmap(item.getItemType().getImage(),
                    item.getHitbox().left + cameraX, item.getHitbox().top + cameraY, null);
        else
            canvas.drawBitmap(item.getItemType().getImage(item.getAniIndex()),
                    item.getHitbox().left + cameraX, item.getHitbox().top + cameraY, null);
    }

    public void drawObject(Canvas canvas, GameObject gameObject) {
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(
                    gameObject.getHitbox().left + cameraX, gameObject.getHitbox().top + cameraY,
                    gameObject.getHitbox().right + cameraX, gameObject.getHitbox().bottom + cameraY,
                    Paints.HITBOX_PAINT);
        canvas.drawBitmap(gameObject.getObjectType().getObjectImg(),
                gameObject.getHitbox().left + cameraX,
                gameObject.getHitbox().top - gameObject.getObjectType().getHitboxRoof() + cameraY, null);
    }

    public void drawBuilding(Canvas canvas, Building building) {
        canvas.drawBitmap(building.getBuildingType().getHouseImg(),
                building.getPos().x + cameraX,
                building.getPos().y - building.getBuildingType().getHitboxRoof() + cameraY, null);
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(
                    building.getHitbox().left + cameraX, building.getHitbox().top + cameraY,
                    building.getHitbox().right + cameraX, building.getHitbox().bottom + cameraY,
                    Paints.HITBOX_PAINT);
    }

    public void drawTiles(Canvas canvas) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                canvas.drawBitmap(
                        currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)),
                        i * GameConstants.Sprite.SIZE + cameraX,
                        j * GameConstants.Sprite.SIZE + cameraY, null);

        if (GameActivity.isDrawHitbox()) {
            canvas.drawRect(
                    spawnZone.left + cameraX, spawnZone.top + cameraY,
                    spawnZone.right + cameraX, spawnZone.bottom + cameraY,
                    Paints.HITBOX_PAINT);
        }
    }

    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox)) return doorway;
        return null;
    }

    public void changeMap(Doorway doorwayTarget) {
        Doorway destination = doorwayTarget.getDoorwayConnectedTo();
        if (destination == null) return;

        this.currentMap = destination.getGameMapLocatedIn();

        float cX = GAME_WIDTH / 2f - destination.getPosOfDoorway().x + GameConstants.Sprite.HITBOX_SIZE / 2f;
        float cY = GAME_HEIGHT / 2f - destination.getPosOfDoorway().y + GameConstants.Sprite.HITBOX_SIZE / 2f;

        playing.setCameraValues(new PointF(cX, cY));
        cameraX = cX;
        cameraY = cY;

        playing.setDoorwayJustPassed(true);
    }

    public void drawDoorways(Canvas canvas, float cameraX, float cameraY) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.getGameMapLocatedIn() == currentMap) {
                float x = doorway.getPosOfDoorway().x + cameraX;
                float y = doorway.getPosOfDoorway().y + cameraY;
                canvas.drawCircle(x, y, 15, Paints.RED_PAINT);
            }
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public GameMap getOutsideMap() {
        return outsideMap;
    }

    public boolean paintTile(int spriteId, int playerId) {
        if (currentMap != outsideMap) return false;

        Player player = playing.getPlayer();
        float worldX = player.getHitbox().centerX() - cameraX;
        float worldY = player.getHitbox().centerY() - cameraY;

        boolean painted = outsideMap.paintTile(worldX, worldY, spriteId);

        if (painted) {
            MainActivity.getDbHelper().saveTileMap(
                    playerId,
                    outsideMap.getSpritesID(),
                    outsideMap.getOriginalSpriteIds());
        }
        return painted;
    }


    public boolean build(Items item, int playerId) {
        Player player = playing.getPlayer();

        float playerWorldCenterX = player.getHitbox().centerX() - cameraX;
        float playerWorldFeetY   = player.getHitbox().bottom    - cameraY;

        if (item.isBuilding() && currentMap != outsideMap) return false;

        final float hbW, hbH, hbRoof;
        if (item.isBuilding()) {
            Buildings bType = Objects.requireNonNull(item.getBuildingType());
            hbW   = bType.getHitboxWidth();
            hbH   = bType.getHitboxHeight();
            hbRoof = bType.getHitboxRoof();
        } else if (item.isObject()) {
            GameObjects oType = Objects.requireNonNull(item.getObjectType());
            hbW   = oType.getHitboxWidth();
            hbH   = oType.getHitboxHeight();
            hbRoof = oType.getHitboxRoof();
        } else {
            hbW   = item.getImage().getWidth();
            hbH   = item.getImage().getHeight();
            hbRoof = 0;
        }

        final float gap = GameConstants.Sprite.SIZE;
        float buildHitboxX, buildHitboxY;
        switch (player.getFaceDir()) {
            case UP -> {
                buildHitboxX = playerWorldCenterX - hbW / 2f;
                buildHitboxY = playerWorldFeetY - gap - hbH;
            }
            case DOWN -> {
                buildHitboxX = playerWorldCenterX - hbW / 2f;
                buildHitboxY = playerWorldFeetY + gap;
            }
            case LEFT -> {
                buildHitboxX = playerWorldCenterX - gap - hbW;
                buildHitboxY = playerWorldFeetY - hbH;
            }
            default -> {
                buildHitboxX = playerWorldCenterX + gap;
                buildHitboxY = playerWorldFeetY - hbH;
            }
        }

        float rawX = buildHitboxX;
        float rawY = buildHitboxY - hbRoof;

        if (buildHitboxX < 0 || buildHitboxY < 0
                || buildHitboxX + hbW > currentMap.getMapWidth()
                || buildHitboxY + hbH > currentMap.getMapHeight()) {
            return false;
        }

        RectF buildHitbox = new RectF(
                buildHitboxX, buildHitboxY,
                buildHitboxX + hbW, buildHitboxY + hbH);

        if (currentMap == outsideMap)
            if (RectF.intersects(spawnZone, buildHitbox)) return false;

        final float DOORWAY_MARGIN = GameConstants.Sprite.SIZE * 2f;
        for (Building b : currentMap.getBuildingArrayList()) {
            if (b.getHitbox() != null && RectF.intersects(b.getHitbox(), buildHitbox)) return false;
            PointF door = b.getOutsideDoorway().getPosOfDoorway();
            RectF doorMargin = new RectF(
                    door.x - DOORWAY_MARGIN, door.y - DOORWAY_MARGIN,
                    door.x + DOORWAY_MARGIN, door.y + DOORWAY_MARGIN);
            if (RectF.intersects(doorMargin, buildHitbox)) return false;
        }

        for (GameObject go : currentMap.getGameObjectArrayList())
            if (go.getHitbox() != null && RectF.intersects(go.getHitbox(), buildHitbox))
                return false;

        if (item.isBuilding()) {
            Buildings bType = Objects.requireNonNull(item.getBuildingType());
            Building newBuilding = new Building(new PointF(rawX, rawY), bType, 0, currentMap);
            currentMap.getBuildingArrayList().add(newBuilding);

            if (currentMap == outsideMap)
                MainActivity.getDbHelper().saveAllBuildings(playerId, outsideMap.getBuildingArrayList());
        }

        if (item.isObject()) {
            GameObjects oType = Objects.requireNonNull(item.getObjectType());
            GameObject newObject = new GameObject(new PointF(rawX, rawY), oType);
            currentMap.getGameObjectArrayList().add(newObject);

            if (currentMap == outsideMap)
                MainActivity.getDbHelper().saveAllObjects(playerId, outsideMap.getGameObjectArrayList());
            else
                MainActivity.getDbHelper().saveAllBuildings(playerId, outsideMap.getBuildingArrayList());
        }

        return true;
    }
}