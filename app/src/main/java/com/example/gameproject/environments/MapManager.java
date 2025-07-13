package com.example.gameproject.environments;


import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.DOWN;
import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.LEFT;
import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.RIGHT;
import static com.example.gameproject.helpers.var.GameConstants.Face_Dir.UP;
import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.HelpMethods;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.main.GameActivity;

public class MapManager {

    private GameMap currentMap;
    private float cameraX, cameraY;
    private final Playing playing;


    public MapManager(Playing playing) {
        this.playing = playing;
        initMaps();
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
            canvas.drawBitmap(item.getItemType().getImage(), item.getHitbox().left + cameraX, item.getHitbox().top + cameraY, null);
        else
            canvas.drawBitmap(item.getItemType().getImage(item.getAniIndex()), item.getHitbox().left + cameraX, item.getHitbox().top + cameraY, null);
    }

    public void drawObject(Canvas canvas, GameObject gameObject) {
        if (GameActivity.isDrawHitbox())
            canvas.drawRect(gameObject.getHitbox().left + cameraX, gameObject.getHitbox().top + cameraY, gameObject.getHitbox().right + cameraX, gameObject.getHitbox().bottom + cameraY, Paints.HITBOX_PAINT);
        canvas.drawBitmap(gameObject.getObjectType().getObjectImg(), gameObject.getHitbox().left + cameraX, gameObject.getHitbox().top - gameObject.getObjectType().getHitboxRoof() + cameraY, null);
    }

    public void drawBuilding(Canvas canvas, Building building) {
        canvas.drawBitmap(building.getBuildingType().getHouseImg(), building.getPos().x + cameraX, building.getPos().y - building.getBuildingType().getHitboxRoof() + cameraY, null);

        if (GameActivity.isDrawHitbox())
            canvas.drawRect(building.getHitbox().left + cameraX, building.getHitbox().top + cameraY, building.getHitbox().right + cameraX, building.getHitbox().bottom + cameraY, Paints.HITBOX_PAINT);
    }

    public void drawTiles(Canvas canvas) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                canvas.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }


    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox, cameraX, cameraY)) return doorway;

        return null;
    }

    public void changeMap(Doorway doorwayTarget) {
        this.currentMap = doorwayTarget.getGameMapLocatedIn();

        float cX = GAME_WIDTH / 2f - doorwayTarget.getPosOfDoorway().x + GameConstants.Sprite.HITBOX_SIZE / 2f;
        float cY = GAME_HEIGHT / 2f - doorwayTarget.getPosOfDoorway().y + GameConstants.Sprite.HITBOX_SIZE / 2f;

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

    private void initMaps() {
        int[][] outside = MapHelper.getMapArrayFinal();
        var buildings = MapHelper.getBuildings();
        var objects = MapHelper.getGameObjects();

        var items = MapHelper.getItems();
        var particles = MapHelper.getParticles();


        int MAX_ENEMIES = 15;
        GameMap outsideMap = new GameMap(outside, MAX_ENEMIES, Tiles.OUTSIDE, buildings, objects, HelpMethods.SpawnStartedEnemies(MAX_ENEMIES, outside, buildings, objects), items, particles);


        int[][] insideRegHouseArray = MapHelper.getInsideRegHouseArr();

        int[][] insideMailHouseArray = MapHelper.getInsideMailHouseArray();

        int[][] insideFlatHouseArray = MapHelper.getInsideFlatHouseArray();

        int[][] insideBlacksmithHouseArr = MapHelper.getInsideBlacksmithHouseArray();


        GameMap insideMap1 = new GameMap(insideRegHouseArray, Tiles.INSIDE, MapHelper.getObjectsReg1(), particles);
        GameMap insideMap2 = new GameMap(insideRegHouseArray, Tiles.INSIDE, MapHelper.getObjectsReg2(), particles);
        GameMap MailMap = new GameMap(insideMailHouseArray, Tiles.INSIDE, MapHelper.getObjectsMail(), particles);
        GameMap insideFlatRoofHouseMap1 = new GameMap(insideFlatHouseArray, Tiles.INSIDE, MapHelper.getObjectsFlat1(), particles);
        GameMap insideFlatRoofHouseMap2 = new GameMap(insideFlatHouseArray, Tiles.INSIDE, MapHelper.getObjectsFlat2(), particles);
        GameMap insideFlatRoofHouseMap3 = new GameMap(insideFlatHouseArray, Tiles.INSIDE, MapHelper.getObjectsFlat3(), particles);
        GameMap insideGreenRoofHouseMap1 = new GameMap(insideBlacksmithHouseArr, Tiles.INSIDE, MapHelper.getObjectsGreen1(), particles);
        GameMap insideGreenRoofHouseMap2 = new GameMap(insideBlacksmithHouseArr, Tiles.INSIDE, MapHelper.getObjectsGreen2(), particles);
        GameMap insideGreenRoofHouseMap3 = new GameMap(insideBlacksmithHouseArr, Tiles.INSIDE, MapHelper.getObjectsGreen3(), particles);

        MapHelper.connectDoorways(outsideMap, insideMap1, insideMap2, MailMap, insideFlatRoofHouseMap1, insideFlatRoofHouseMap2, insideFlatRoofHouseMap3, insideGreenRoofHouseMap1, insideGreenRoofHouseMap2, insideGreenRoofHouseMap3);

        HelpMethods.AddVillagersToBuildings(buildings);
        HelpMethods.CreateSecreteTeleport(insideMap2, insideFlatRoofHouseMap1, 1, 1, 1, 1);
        HelpMethods.CreateSecreteTeleportFromCoordinate(outsideMap, outsideMap, 45, 30, 3930, 1120);

        currentMap = outsideMap;
    }

    public boolean build(Items item) {
        Player player = playing.getPlayer();

        float centerX = GAME_WIDTH / 2f;
        float centerY = GAME_HEIGHT / 2f;

        float buildX = -cameraX + centerX;
        float buildY = -cameraY + centerY;

        final float distance = GameConstants.Sprite.SIZE;
        switch (player.getFaceDir()) {
            case UP -> buildY -= distance;
            case DOWN -> buildY += distance;
            case LEFT -> buildX -= distance;
            case RIGHT -> buildX += distance;
        }

        if (buildX < 0 || buildY < 0 ||
                buildX + item.getImage().getWidth() > currentMap.getMapWidth() ||
                buildY + item.getImage().getHeight() > currentMap.getMapHeight()) {
            return false;
        }

        RectF buildHitbox = new RectF(buildX, buildY,
                buildX + item.getImage().getWidth(),
                buildY + item.getImage().getHeight());

        for (Entity entity : currentMap.getDrawableList()) {
            if (entity != null && entity.getHitbox() != null &&
                    RectF.intersects(entity.getHitbox(), buildHitbox)) {
                return false;
            }
        }

        if (item.isBuilding()) {
            Building newBuilding = new Building(new PointF(buildX, buildY), item.getBuildingType(), 0);
            currentMap.getBuildingArrayList().add(newBuilding);
            player.saveBuilding(newBuilding,currentMap.getBuildingArrayList());
        }
        if (item.isObject()) {
            GameObject newObject = new GameObject(new PointF(buildX, buildY), item.getObjectType());
            currentMap.getGameObjectArrayList().add(newObject);
        }

        return true;
    }

}