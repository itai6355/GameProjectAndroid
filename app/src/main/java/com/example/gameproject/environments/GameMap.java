package com.example.gameproject.environments;

import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Entity;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.helpers.GameConstants;

import java.util.ArrayList;

public class GameMap {

    private int[][] spriteIds;
    private Tiles tilesType;
    private ArrayList<Building> buildingArrayList;
    private ArrayList<Doorway> doorwayArrayList;
    private ArrayList<GameObject> gameObjectArrayList;
    private ArrayList<Character> skeletonArrayList;
    private ArrayList<Item> itemArrayList;

    public GameMap(int[][] spriteIds, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, ArrayList<Character> skeletonArrayList, ArrayList<Item> itemArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList;
        this.gameObjectArrayList = gameObjectArrayList;
        this.skeletonArrayList = skeletonArrayList;
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = itemArrayList;
    }

    public Entity[] getDrawableList() {
        Entity[] list = new Entity[getDrawableAmount()];
        int i = 0;

        if (buildingArrayList != null)
            for (Building b : buildingArrayList)
                list[i++] = b;
        if (skeletonArrayList != null)
            for (Character c : skeletonArrayList)
                list[i++] = c;
        if (gameObjectArrayList != null)
            for (GameObject go : gameObjectArrayList)
                list[i++] = go;
        if (itemArrayList != null)
            for (Item item : itemArrayList)
                list[i++] = item;

        return list;
    }

    private int getDrawableAmount() {
        int amount = 0;
        if (buildingArrayList != null)
            amount += buildingArrayList.size();
        if (gameObjectArrayList != null)
            amount += gameObjectArrayList.size();
        if (skeletonArrayList != null)
            amount += skeletonArrayList.size();
        if (itemArrayList != null)
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

    public ArrayList<Item> getItemArrayList() {
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
