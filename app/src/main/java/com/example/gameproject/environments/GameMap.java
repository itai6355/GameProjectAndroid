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

    private int[][] spriteIds;
    private Tiles tilesType;
    private final ArrayList<Building> buildingArrayList;
    private ArrayList<Doorway> doorwayArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final ArrayList<Character> skeletonArrayList;
    private final CopyOnWriteArrayList<Item> itemArrayList;

    public GameMap(int[][] spriteIds, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, ArrayList<Character> skeletonArrayList, CopyOnWriteArrayList<Item> itemArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList != null ? buildingArrayList : new ArrayList<>();
        this.gameObjectArrayList = gameObjectArrayList != null ? gameObjectArrayList : new ArrayList<>();
        this.skeletonArrayList = skeletonArrayList != null ? skeletonArrayList : new ArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = itemArrayList != null ? itemArrayList : new CopyOnWriteArrayList<>();
    }


    public Entity[] getDrawableList() {
        Entity[] list = new Entity[getDrawableAmount()];
        int i = 0;

        for (Building b : buildingArrayList)
            list[i++] = b;

        for (Character c : skeletonArrayList)
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
            amount += skeletonArrayList.size();
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

    public ArrayList<Character> getSkeletonArrayList() {
        return skeletonArrayList;
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

}
