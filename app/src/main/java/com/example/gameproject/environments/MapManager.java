package com.example.gameproject.environments;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.Buildings;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.GameObjects;
import com.example.gameproject.gamestates.playing.Playing;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapManager {

    private GameMap currentMap;
    private float cameraX, cameraY;
    private final Playing playing;


    private final Random random = new Random();

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
            canvas.drawBitmap(item.getItemType().getImage(item.getAniIndex(item.getItemType())), item.getHitbox().left + cameraX, item.getHitbox().top + cameraY, null);
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

        int[][] outsideArrayFinal = {
                {188, 189, 279, 275, 187, 189, 279, 275, 279, 276, 275, 279, 275, 275, 279, 275, 278, 276, 275, 278, 275, 279, 275},
                {188, 189, 275, 279, 187, 189, 276, 275, 279, 275, 277, 275, 275, 277, 276, 275, 279, 278, 278, 275, 275, 279, 275},
                {188, 189, 275, 276, 187, 189, 276, 279, 275, 278, 279, 279, 275, 275, 278, 278, 275, 275, 275, 276, 275, 279, 275},
                {254, 189, 275, 279, 187, 214, 166, 166, 166, 166, 166, 166, 166, 167, 275, 276, 275, 276, 279, 277, 275, 279, 275},
                {188, 189, 275, 275, 209, 210, 210, 210, 210, 195, 210, 210, 193, 189, 275, 277, 168, 275, 278, 275, 275, 276, 275},
                {188, 189, 279, 276, 279, 275, 276, 275, 277, 190, 275, 279, 187, 189, 275, 279, 190, 275, 279, 275, 275, 279, 275},
                {188, 189, 275, 275, 275, 279, 278, 275, 275, 190, 276, 277, 187, 258, 232, 232, 239, 232, 232, 232, 232, 233, 275},
                {188, 189, 275, 279, 275, 275, 231, 232, 232, 238, 275, 275, 187, 189, 275, 275, 275, 275, 275, 275, 275, 275, 275},
                {188, 189, 276, 279, 278, 275, 276, 275, 275, 275, 275, 276, 187, 189, 276, 275, 277, 275, 279, 275, 279, 275, 276},
                {188, 189, 275, 275, 279, 275, 279, 275, 276, 275, 275, 277, 187, 189, 279, 275, 275, 275, 275, 275, 275, 275, 275},
                {188, 214, 167, 276, 275, 277, 275, 275, 278, 275, 276, 275, 187, 189, 275, 275, 278, 275, 275, 276, 275, 277, 275},
                {254, 188, 214, 167, 275, 278, 275, 275, 275, 275, 279, 275, 187, 189, 275, 275, 275, 168, 275, 275, 275, 275, 278},
                {188, 188, 188, 214, 167, 279, 275, 277, 275, 277, 276, 275, 187, 258, 232, 232, 232, 238, 275, 279, 275, 275, 279},
                {188, 188, 188, 253, 214, 167, 275, 277, 168, 275, 275, 275, 187, 189, 275, 275, 275, 275, 275, 279, 275, 275, 275},
                {253, 188, 188, 188, 256, 214, 167, 275, 235, 232, 232, 232, 259, 189, 279, 275, 275, 277, 275, 275, 275, 279, 275},
                {188, 188, 188, 254, 188, 256, 214, 167, 275, 275, 277, 275, 187, 189, 275, 278, 275, 275, 279, 275, 279, 278, 275}
        };
        int[][] outsideArrayEmpty = new int[15][23];
        for (int i = 0; i < outsideArrayEmpty.length; i++)
            for (int j = 0; j < outsideArrayEmpty[i].length; j++)
                outsideArrayEmpty[i][j] = random.nextInt(5) + 275;


        int[][] outsideArrayStarter = new int[15][23];
        for (int i = 0; i < outsideArrayStarter.length; i++)
            for (int j = 0; j < outsideArrayStarter[i].length; j++)
                outsideArrayStarter[i][j] = random.nextInt(5) + 275;

        int[][] lake = {
                {462, 463, 463, 469},
                {484, 515, 485, 491},
                {484, 485, 556, 513},
                {506, 507, 507, 535}
        };

        for (int i = 0; i < lake.length; i++) {
            System.arraycopy(lake[i], 0, outsideArrayStarter[9 + i], 17, lake[i].length);

        }


        int[][] insideArray = {
                {374, 377, 377, 377, 377, 377, 378},
                {396, 0, 1, 1, 1, 2, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 22, 23, 23, 23, 24, 400},
                {396, 44, 45, 45, 45, 46, 400},
                {462, 465, 463, 394, 464, 465, 466}
        };

        int[][] insideFlatHouseArray = {
                {389, 392, 392, 392, 392, 392, 393},
                {411, 143, 144, 144, 144, 145, 415},
                {411, 165, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 167, 415},
                {411, 165, 166, 166, 166, 167, 415},
                {411, 187, 188, 188, 188, 189, 415},
                {477, 480, 478, 394, 479, 480, 481}
        };

        int[][] insideGreenRoofHouseArr = {
                {384, 387, 387, 387, 387, 387, 388},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {406, 298, 298, 298, 298, 298, 410},
                {472, 475, 473, 394, 474, 475, 476}
        };

        ArrayList<Building> buildingArrayListFinal = new ArrayList<>();
        buildingArrayListFinal.add(new Building(new PointF(1440, 160), Buildings.HOUSE_ONE));
        buildingArrayListFinal.add(new Building(new PointF(1540, 880), Buildings.HOUSE_TWO));
        buildingArrayListFinal.add(new Building(new PointF(575, 1000), Buildings.HOUSE_SIX));

        ArrayList<GameObject> gameObjectArrayListFinal = new ArrayList<>();
        gameObjectArrayListFinal.add(new GameObject(new PointF(190, 70), GameObjects.STATUE_ANGRY_YELLOW));
        gameObjectArrayListFinal.add(new GameObject(new PointF(580, 70), GameObjects.STATUE_ANGRY_YELLOW));
        gameObjectArrayListFinal.add(new GameObject(new PointF(1000, 550), GameObjects.BASKET_FULL_RED_FRUIT));
        gameObjectArrayListFinal.add(new GameObject(new PointF(620, 520), GameObjects.OVEN_SNOW_YELLOW));


        ArrayList<Building> buildingArrayListStarter = new ArrayList<>();
        buildingArrayListStarter.add(new Building(new PointF(200, 200), Buildings.HOUSE_ONE));


        CopyOnWriteArrayList<Item> itemArrayList = new CopyOnWriteArrayList<>();
        itemArrayList.add(new Item(Items.COIN, new PointF(0, 0)));


        GameMap insideMap = new GameMap(insideArray, Tiles.INSIDE,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
        GameMap insideFlatRoofHouseMap = new GameMap(insideFlatHouseArray, Tiles.INSIDE, null, null, null, null);
        GameMap insideGreenRoofHouseMap = new GameMap(insideGreenRoofHouseArr, Tiles.INSIDE, null, null, null, null);

        GameMap outsideMap =
                new GameMap(outsideArrayFinal, Tiles.OUTSIDE, buildingArrayListFinal,
                        gameObjectArrayListFinal,
                        new ArrayList<>(),
                        itemArrayList);


        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 0),
                insideMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 1),
                insideFlatRoofHouseMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreatePointForDoorway(outsideMap, 2),
                insideGreenRoofHouseMap,
                HelpMethods.CreatePointForDoorway(3, 6));

        currentMap = outsideMap;
    }
}