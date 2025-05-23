package com.example.gameproject.helpers;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.enemies.DarkNinja;
import com.example.gameproject.entities.enemies.DarkWizard;
import com.example.gameproject.entities.enemies.Enemies;
import com.example.gameproject.entities.enemies.MaskedRaccoon;
import com.example.gameproject.entities.enemies.Skeleton;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.environments.Doorway;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.environments.Tiles;
import com.example.gameproject.main.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class HelpMethods {


    public static PointF CreatePointForDoorway(GameMap gameMapLocatedIn, int buildingIndex) {
        Building building = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex);

        float x = building.getPos().x;
        float y = building.getPos().y;
        PointF point = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getDoorwayPoint();

        return new PointF(point.x + x, point.y + y - building.getBuildingType().getHitboxRoof());

    }

    public static PointF CreatePointForDoorway(int xTile, int yTile) {

        float x = xTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;
        float y = yTile * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f;

        return new PointF(x, y);
    }

    public static void ConnectTwoDoorways(GameMap gameMapOne, PointF pointOne, GameMap gameMapTwo, PointF pointTwo) {

        Doorway doorwayOne = new Doorway(pointOne, gameMapOne);
        Doorway doorwayTwo = new Doorway(pointTwo, gameMapTwo);

        doorwayOne.connectDoorway(doorwayTwo);
        doorwayTwo.connectDoorway(doorwayOne);
    }


    public static CopyOnWriteArrayList<Character> SpawnStartedEnemies(int amount, int[][] gameMapArray, ArrayList<Building> buildingArrayListFinal, ArrayList<GameObject> gameObjectArrayListFinal) {
        CopyOnWriteArrayList<Character> CharacterArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < amount; i++)
            spawnNotOnObject(gameMapArray, buildingArrayListFinal, gameObjectArrayListFinal, CharacterArrayList, Enemies.getRandomEnemy());
        return CharacterArrayList;
    }

    public static CopyOnWriteArrayList<Character> SpawnEnemies(int amount, int[][] gameMapArray, ArrayList<Building> buildingArrayListFinal, ArrayList<GameObject> gameObjectArrayListFinal) {
        CopyOnWriteArrayList<Character> CharacterArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < amount; i++)
            spawnNotOnObject(gameMapArray, buildingArrayListFinal, gameObjectArrayListFinal, CharacterArrayList, Enemies.getRandomEnemy());
        return CharacterArrayList;
    }

    private static void spawnNotOnObject(int[][] gameMapArray, ArrayList<Building> buildingArrayListFinal, ArrayList<GameObject> gameObjectArrayListFinal, CopyOnWriteArrayList<Character> CharacterArrayList, Enemies enemy) {
        int width = (gameMapArray[0].length - 1) * GameConstants.Sprite.SIZE;
        int height = (gameMapArray.length - 1) * GameConstants.Sprite.SIZE;
        do {
            float x1 = (float) (Math.random() * width);
            float y1 = (float) (Math.random() * height);
            if (isNotOnObject(x1, y1, gameMapArray, buildingArrayListFinal, gameObjectArrayListFinal)) {
                CharacterArrayList.add(createEnemy(enemy, new PointF(x1, y1)));
                break;
            }
        } while (true);
    }

    private static Character createEnemy(Enemies enemy, PointF pointF) {
        return switch (enemy) {
            case SKELETON -> new Skeleton(pointF);
            case MASKED_RAKKON, GOLDEN_MASKED_RAKKON -> new MaskedRaccoon(pointF);
            case DARK_NINJA -> new DarkNinja(pointF);
            case DARK_WIZARD -> new DarkWizard(pointF);
        };
    }

    public static void AddVillagersToBuildings(ArrayList<Building> buildingArrayList) {
        for (Building building : buildingArrayList)
            for (int i = 0; i < building.getVillagerAmount(); i++)
                ConnectVillagerToBuilding(building);
    }

    private static void ConnectVillagerToBuilding(Building building) {
        PointF point = building.getBuildingType().getDoorwayPoint();
        Villager villager = new Villager(MainActivity.getGameContext(), new PointF(point.x + building.getPos().x, point.y + building.getPos().y - building.getBuildingType().getHitboxRoof()), building.getBuildingType().getVillagerType());
        building.addVillager(villager);
        villager.setBuilding(building);
    }

    private static boolean isNotOnObject(float x, float y, int[][] gameMapArray, ArrayList<Building> buildingArrayListFinal, ArrayList<GameObject> gameObjectArrayListFinal) {
        int xTile = (int) (x / GameConstants.Sprite.SIZE);
        int yTile = (int) (y / GameConstants.Sprite.SIZE);

        if (gameMapArray[yTile][xTile] == 0) return false;
        for (Building b : buildingArrayListFinal)
            if (b.getHitbox().contains(x, y)) return false;
        for (GameObject go : gameObjectArrayListFinal)
            if (go.getHitbox().contains(x, y)) return false;
        return true;

    }


    public static boolean CanWalkHereUpDown(RectF hitbox, float deltaY, float currentCameraX, GameMap gameMap) {
        if (hitbox.top + deltaY < 0 || hitbox.bottom + deltaY >= gameMap.getMapHeight())
            return false;

        RectF tempHitbox = new RectF(hitbox.left + currentCameraX, hitbox.top + deltaY, hitbox.right + currentCameraX, hitbox.bottom + deltaY);


        Point[] tileCords = GetTileCords(hitbox, currentCameraX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType()) && isOutsideGameObject(tempHitbox, gameMap) && isOutsideBuilding(tempHitbox, gameMap);
    }

    public static boolean CanWalkHereLeftRight(RectF hitbox, float deltaX, float currentCameraY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0 || hitbox.right + deltaX >= gameMap.getMapWidth())
            return false;

        RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + currentCameraY, hitbox.right + deltaX, hitbox.bottom + currentCameraY);


        Point[] tileCords = GetTileCords(hitbox, deltaX, currentCameraY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType()) && isOutsideGameObject(tempHitbox, gameMap) && isOutsideBuilding(tempHitbox, gameMap);
    }

    public static boolean CanWalkHere(RectF hitbox, float deltaX, float deltaY, GameMap gameMap) {
        if (hitbox.left + deltaX < 0 || hitbox.top + deltaY < 0 || hitbox.right + deltaX >= gameMap.getMapWidth() || hitbox.bottom + deltaY >= gameMap.getMapHeight())
            return false;

        RectF tempHitbox = new RectF(hitbox.left + deltaX, hitbox.top + deltaY, hitbox.right + deltaX, hitbox.bottom + deltaY);

        Point[] tileCords = GetTileCords(hitbox, deltaX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType()) && isOutsideGameObject(tempHitbox, gameMap) && isOutsideBuilding(tempHitbox, gameMap);
    }

    public static boolean isOutsideBuilding(RectF hitbox, GameMap gameMap) {
        if (gameMap.getBuildingArrayList() != null)
            for (Building b : gameMap.getBuildingArrayList())
                if (RectF.intersects(b.getHitbox(), hitbox)) return false;

        return true;
    }

    public static boolean isOutsideGameObject(RectF hitbox, GameMap gameMap) {
        if (gameMap.getGameObjectArrayList() != null)
            for (GameObject go : gameMap.getGameObjectArrayList())
                if (RectF.intersects(go.getHitbox(), hitbox) && !go.getObjectType().isWalkable())
                    return false;
        return true;
    }


    private static int[] GetTileIds(Point[] tileCords, GameMap gameMap) {
        int[] tileIds = new int[4];

        for (int i = 0; i < tileCords.length; i++)
            tileIds[i] = gameMap.getSpriteID(tileCords[i].x, tileCords[i].y);

        return tileIds;
    }


    private static Point[] GetTileCords(RectF hitbox, float deltaX, float deltaY) {
        Point[] tileCords = new Point[4];

        int left = (int) ((hitbox.left + deltaX) / GameConstants.Sprite.SIZE);
        int right = (int) ((hitbox.right + deltaX) / GameConstants.Sprite.SIZE);
        int top = (int) ((hitbox.top + deltaY) / GameConstants.Sprite.SIZE);
        int bottom = (int) ((hitbox.bottom + deltaY) / GameConstants.Sprite.SIZE);

        tileCords[0] = new Point(left, top);
        tileCords[1] = new Point(right, top);
        tileCords[2] = new Point(left, bottom);
        tileCords[3] = new Point(right, bottom);

        return tileCords;

    }

    public static boolean IsTilesWalkable(int[] tileIds, Tiles tilesType) {
        for (int i : tileIds)
            if (!(IsTileWalkable(i, tilesType))) return false;
        return true;
    }

    public static boolean IsTileWalkable(int tileId, Tiles tilesType) {
        if (tilesType == Tiles.INSIDE) return (tileId == 394 || tileId < 374);

        return true;
    }

    public static boolean IsPlayerCloseForAttack(Character character, Player player, float cameraY, float cameraX) {
        if (player.isInvisible()) return false;

        float xDelta = character.getHitbox().left - (player.getHitbox().left - cameraX);
        float yDelta = character.getHitbox().top - (player.getHitbox().top - cameraY);

        float distance = (float) Math.hypot(xDelta, yDelta);

        if (character instanceof DarkNinja || character instanceof DarkWizard)
            return distance < GameConstants.Sprite.SIZE * 3f;

        return distance < GameConstants.Sprite.SIZE * 1.5f;

    }

    public static void CreateSecreteTeleport(GameMap gameMamp1, GameMap gameMap2, int xTile1, int yTile1, int xTile2, int yTile2) {
        PointF teleport1 = CreatePointForDoorway(xTile1, yTile1);
        PointF teleport2 = CreatePointForDoorway(xTile2, yTile2);

        ConnectTwoDoorways(gameMamp1, teleport1, gameMap2, teleport2);
    }
    public static void CreateSecreteTeleportFromCoordinate(GameMap gameMamp1, GameMap gameMap2, int x1, int y1, int x2, int y2) {
        PointF teleport1 = new PointF(x1, y1);
        PointF teleport2 = new PointF(x2, y2);

        ConnectTwoDoorways(gameMamp1, teleport1, gameMap2, teleport2);
    }
}
