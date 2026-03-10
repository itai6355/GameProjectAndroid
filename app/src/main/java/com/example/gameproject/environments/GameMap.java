package com.example.gameproject.environments;

import android.os.Build;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.particals.Particle;
import com.example.gameproject.helpers.var.GameConstants;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMap {

    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final CopyOnWriteArrayList<Character> enemysArrayList;
    private final CopyOnWriteArrayList<Item> itemArrayList;

    private final int[][] spriteIds;


    private final int[][] originalSpriteIds;

    private final Tiles tilesType;
    private final ArrayList<Doorway> doorwayArrayList;
    private final int MAX_ENEMIES;

    public GameMap(int[][] spriteIds, Tiles tilesType,
                   ArrayList<GameObject> gameObjectArrayList,
                   ArrayList<Particle> particlesArrayList) {
        this.spriteIds = spriteIds;
        this.originalSpriteIds = deepCopy(spriteIds);
        this.tilesType = tilesType;
        this.gameObjectArrayList = gameObjectArrayList;
        this.buildingArrayList = new ArrayList<>();
        this.enemysArrayList = new CopyOnWriteArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = new CopyOnWriteArrayList<>();
        MAX_ENEMIES = 0;
    }

    public GameMap(Tiles tilesType) {
        this.spriteIds = new int[50][50];
        this.tilesType = tilesType;
        this.buildingArrayList = new ArrayList<>();
        this.gameObjectArrayList = new ArrayList<>();
        this.enemysArrayList = new CopyOnWriteArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = new CopyOnWriteArrayList<>();
        MAX_ENEMIES = 0;

        for (int[] row : spriteIds)
            for (int i = 0; i < row.length; i++)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    row[i] = new Random().nextInt(275, 280);
                }

        this.originalSpriteIds = deepCopy(spriteIds);
    }
    public boolean paintTile(float worldX, float worldY, int spriteId) {
        int col = (int) (worldX / GameConstants.Sprite.SIZE);
        int row = (int) (worldY / GameConstants.Sprite.SIZE);
        if (row < 0 || row >= spriteIds.length || col < 0 || col >= spriteIds[0].length)
            return false;
        spriteIds[row][col] = spriteId;
        return true;
    }

    public int[][] getOriginalSpriteIds() {
        return originalSpriteIds;
    }

    public Entity[] getDrawableList() {
        Entity[] list = new Entity[getDrawableAmount()];
        int i = 0;

        for (Building b : buildingArrayList)
            list[i++] = b;

        for (Character c : enemysArrayList)
            list[i++] = c;

        for (GameObject go : gameObjectArrayList)
            list[i++] = go;

        for (Item item : itemArrayList)
            list[i++] = item;

        for (Building b : buildingArrayList)
            for (Villager v : b.getVillagers())
                if (v != null)
                    list[i++] = v;

        return list;
    }

    private int getDrawableAmount() {
        int amount = 0;
        amount += buildingArrayList.size();
        amount += gameObjectArrayList.size();
        amount += enemysArrayList.size();
        amount += itemArrayList.size();
        for (var b : buildingArrayList)
            for (var v : b.getVillagers())
                if (v != null)
                    amount++;
        amount++;

        return amount;
    }

    public void addDoorway(Doorway doorway) {
        this.doorwayArrayList.add(doorway);
    }

    public ArrayList<Doorway> getDoorwayArrayList() {
        return doorwayArrayList;
    }

    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }

    public ArrayList<GameObject> getGameObjectArrayList() {
        return gameObjectArrayList;
    }

    public CopyOnWriteArrayList<Character> getEnemysArrayList() {
        return enemysArrayList;
    }

    public CopyOnWriteArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public Tiles getFloorType() {
        return tilesType;
    }

    public int getSpriteID(int xIndex, int yIndex) {
        return spriteIds[yIndex][xIndex];
    }

    public int[][] getSpritesID() {
        return spriteIds;
    }

    public int getArrayWidth() {
        return spriteIds[0].length;
    }

    public int getArrayHeight() {
        return spriteIds.length;
    }

    public int getMapWidth() {
        return getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMapHeight() {
        return getArrayHeight() * GameConstants.Sprite.SIZE;
    }

    public int getMaxEnemies() {
        return MAX_ENEMIES;
    }

    private static int[][] deepCopy(int[][] src) {
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++)
            copy[i] = src[i].clone();
        return copy;
    }
}