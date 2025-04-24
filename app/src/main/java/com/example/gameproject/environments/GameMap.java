package com.example.gameproject.environments;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.particals.Particle;
import com.example.gameproject.helpers.GameConstants;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMap {

    private final ArrayList<Building> buildingArrayList;
    private final ArrayList<GameObject> gameObjectArrayList;
    private final ArrayList<Particle> particlesArrayList;
    private final CopyOnWriteArrayList<Character> enemysArrayList;
    private final CopyOnWriteArrayList<Item> itemArrayList;
    private final int[][] spriteIds;
    private final Tiles tilesType;
    private final ArrayList<Doorway> doorwayArrayList;
    private final int MAX_ENEMIES;

    public GameMap(int[][] spriteIds, int maxEnemies, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, CopyOnWriteArrayList<Character> enemysArrayList, CopyOnWriteArrayList<Item> itemArrayList, ArrayList<Particle> ParticlesArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList != null ? buildingArrayList : new ArrayList<>();
        this.gameObjectArrayList = gameObjectArrayList != null ? gameObjectArrayList : new ArrayList<>();
        this.enemysArrayList = enemysArrayList != null ? enemysArrayList : new CopyOnWriteArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = itemArrayList != null ? itemArrayList : new CopyOnWriteArrayList<>();
        this.particlesArrayList = ParticlesArrayList != null ? ParticlesArrayList : new ArrayList<>();
        MAX_ENEMIES = maxEnemies;
    }
    public GameMap(int[][] spriteIds, Tiles tilesType,ArrayList<GameObject> gameObjectArrayList) {
        this.spriteIds = spriteIds;
        this.tilesType = tilesType;
        this.gameObjectArrayList = gameObjectArrayList;
        this.buildingArrayList = new ArrayList<>();
        this.enemysArrayList = new CopyOnWriteArrayList<>();
        this.doorwayArrayList = new ArrayList<>();
        this.itemArrayList = new CopyOnWriteArrayList<>();
        this.particlesArrayList = new ArrayList<>();
        MAX_ENEMIES = 0;
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

        for (Particle p : particlesArrayList)
            list[i++] = p;

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
        amount += particlesArrayList.size();
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

    public ArrayList<Particle> getParticlesArrayList() {
        return particlesArrayList;
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
