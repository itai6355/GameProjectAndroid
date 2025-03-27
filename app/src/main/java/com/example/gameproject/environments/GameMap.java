package com.example.gameproject.environments;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.helpers.GameConstants;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMap {

    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final CopyOnWriteArrayList<Character> enemysArrayList;
    private final CopyOnWriteArrayList<Item> itemArrayList;
    private final int[][] spriteIds;
    private final Tiles tilesType;
    private final ArrayList<Doorway> doorwayArrayList;
    private final int MAX_ENEMIES;

    public GameMap(int[][] spriteIds, int maxEnemies, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, CopyOnWriteArrayList<Character> enemysArrayList, CopyOnWriteArrayList<Item> itemArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList != null ? buildingArrayList : new ArrayList<>();
        this.gameObjectArrayList = gameObjectArrayList != null ? gameObjectArrayList : new ArrayList<>();
        this.enemysArrayList = enemysArrayList != null ? enemysArrayList : new CopyOnWriteArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = itemArrayList != null ? itemArrayList : new CopyOnWriteArrayList<>();
        MAX_ENEMIES = maxEnemies;
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

        for (Item item : itemArrayList) {
            list[i++] = item;
        }


        return list;
    }

    private int getDrawableAmount() {
        int amount = 0;
        amount += buildingArrayList.size();
        amount += gameObjectArrayList.size();
        amount += enemysArrayList.size();
        amount += itemArrayList.size();
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
}
