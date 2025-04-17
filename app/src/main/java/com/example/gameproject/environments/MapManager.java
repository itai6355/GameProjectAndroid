package com.example.gameproject.environments;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

import java.util.Random;

public class MapManager {

    private GameMap currentMap;
    private float cameraX, cameraY;
    private final Playing playing;


    public MapManager(Playing playing) {
        this.playing = playing;
        initTestMaps();

    }

    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canMoveHere(float x, float y) {
        if (x < 0 || y < 0)
            return false;

        return !(x >= getMaxWidthCurrentMap()) && !(y >= getMaxHeightCurrentMap());
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
        canvas.drawBitmap(gameObject.getObjectType().getObjectImg(), gameObject.getHitbox().left + cameraX, gameObject.getHitbox().top - gameObject.getObjectType().getHitboxRoof() + cameraY, null);
    }

    public void drawBuilding(Canvas canvas, Building building) {
        canvas.drawBitmap(building.getBuildingType().getHouseImg(), building.getPos().x + cameraX, building.getPos().y - building.getBuildingType().getHitboxRoof() + cameraY, null);
    }

    public void drawTiles(Canvas canvas) {
        for (int j = 0; j < currentMap.getArrayHeight(); j++)
            for (int i = 0; i < currentMap.getArrayWidth(); i++)
                canvas.drawBitmap(currentMap.getFloorType().getSprite(currentMap.getSpriteID(i, j)), i * GameConstants.Sprite.SIZE + cameraX, j * GameConstants.Sprite.SIZE + cameraY, null);
    }


    public Doorway isPlayerOnDoorway(RectF playerHitbox) {
        for (Doorway doorway : currentMap.getDoorwayArrayList())
            if (doorway.isPlayerInsideDoorway(playerHitbox, cameraX, cameraY))
                return doorway;

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

    public GameMap getCurrentMap() {
        return currentMap;
    }

    private void initTestMaps() {
        //TODO: add paricals

        int[][] outside = MapHelper.getMapArrayFinal();
        var buildings = MapHelper.getBuildings();
        var objects = MapHelper.getGameObjects();

        var items = MapHelper.getItems();


        int MAX_ENEMIES = 15;
        GameMap outsideMap = new GameMap(outside, MAX_ENEMIES, Tiles.OUTSIDE, buildings, objects,
                HelpMethods.SpawnStartedEnemies(MAX_ENEMIES, outside, buildings, objects), items);


        int[][] insideArray = MapHelper.getInsideMapArray();

        int[][] insideFlatHouseArray = MapHelper.getInsideFlatHouseArray();

        int[][] insideGreenRoofHouseArr = MapHelper.getInsideGreenRoofHouseArr();

        GameMap insideMap1 = new GameMap(insideArray, Tiles.INSIDE);
        GameMap insideMap2 = new GameMap(insideArray, Tiles.INSIDE);
        GameMap insideMap3 = new GameMap(insideArray, Tiles.INSIDE);
        GameMap insideFlatRoofHouseMap1 = new GameMap(insideFlatHouseArray, Tiles.INSIDE);
        GameMap insideFlatRoofHouseMap2 = new GameMap(insideFlatHouseArray, Tiles.INSIDE);
        GameMap insideFlatRoofHouseMap3 = new GameMap(insideFlatHouseArray, Tiles.INSIDE);
        GameMap insideGreenRoofHouseMap1 = new GameMap(insideGreenRoofHouseArr, Tiles.INSIDE);
        GameMap insideGreenRoofHouseMap2 = new GameMap(insideGreenRoofHouseArr, Tiles.INSIDE);
        GameMap insideGreenRoofHouseMap3 = new GameMap(insideGreenRoofHouseArr, Tiles.INSIDE);

        MapHelper.connectDoorways(outsideMap,
                insideMap1,
                insideMap2,
                insideMap3,
                insideFlatRoofHouseMap1,
                insideFlatRoofHouseMap2,
                insideFlatRoofHouseMap3,
                insideGreenRoofHouseMap1,
                insideGreenRoofHouseMap2,
                insideGreenRoofHouseMap3);

        HelpMethods.AddVillagersToBuildings(buildings);

        currentMap = outsideMap;
    }

}