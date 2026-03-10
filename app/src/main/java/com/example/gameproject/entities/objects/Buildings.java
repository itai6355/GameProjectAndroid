package com.example.gameproject.entities.objects;

import static com.example.gameproject.helpers.var.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.gameproject.R;
import com.example.gameproject.entities.entities.Villager;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.environments.MapHelper;
import com.example.gameproject.environments.Tiles;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.main.MainActivity;

import java.util.ArrayList;
import java.util.Random;


public enum Buildings implements BitmapMethods {

    HOUSE_ONE(0, 0, 64, 48, 23, 42, 9, 36), HOUSE_TWO(64, 4, 62, 44, 23, 36, 9, 31), HOUSE_SIX(304, 0, 64, 48, 39, 40, 15, 35), HOUSE_NINE(0, 48, 64, 63, 24, 62, 22, 51);


    final Bitmap houseImg;
    final PointF doorwayPoint;
    final int hitboxRoof, hitboxFloor, hitboxHeight, hitboxWidth;

    Buildings(int x, int y, int width, int height, int doorwayX, int doorwayY, int hitboxRoof, int hitboxFloor) {
        options.inScaled = false;

        this.hitboxRoof = hitboxRoof * SCALE_MULTIPLIER;
        this.hitboxFloor = hitboxFloor * SCALE_MULTIPLIER;
        this.hitboxHeight = this.hitboxFloor - this.hitboxRoof;
        this.hitboxWidth = width * SCALE_MULTIPLIER;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

        doorwayPoint = new PointF(doorwayX * SCALE_MULTIPLIER, doorwayY * SCALE_MULTIPLIER);
    }


    public GameMap createInsideMap() {
        return switch (this) {
            case HOUSE_ONE ->
                    new GameMap(MapHelper.getInsideRegHouseArr(), Tiles.INSIDE, new ArrayList<>(), MapHelper.getParticles());
            case HOUSE_TWO ->
                    new GameMap(MapHelper.getInsideFlatHouseArray(), Tiles.INSIDE, new ArrayList<>(), MapHelper.getParticles());
            case HOUSE_SIX ->
                    new GameMap(MapHelper.getInsideBlacksmithHouseArray(), Tiles.INSIDE, new ArrayList<>(), MapHelper.getParticles());
            case HOUSE_NINE ->
                    new GameMap(MapHelper.getInsideMailHouseArray(), Tiles.INSIDE, new ArrayList<>(), MapHelper.getParticles());
        };
    }

    public PointF getInsideDoorwayPoint() {
        if (this == HOUSE_NINE || this == HOUSE_SIX) {
            return new PointF(4 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f, 9 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f);
        } else
            return new PointF(3 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f, 6 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f);
    }



    public Villager.VillagerType getVillagerType() {
        int villagerType = new Random().nextInt(2);
        switch (this) {
            case HOUSE_ONE -> {
                if (villagerType == 0) return Villager.VillagerType.VILLAGER_MOM;
                else return Villager.VillagerType.VILLAGER_DAD;
            }
            case HOUSE_TWO -> {
                if (villagerType == 0) return Villager.VillagerType.VILLAGER_BOY;
                else return Villager.VillagerType.VILLAGER_GREEN;
            }
            case HOUSE_SIX -> {
                if (villagerType == 0) return Villager.VillagerType.VILLAGER_BLACK;
                else return Villager.VillagerType.VILLAGER_OLIVE;
            }
        }
        return Villager.VillagerType.VILLAGER_DAD;
    }

    public PointF getDoorwayPoint() {
        return doorwayPoint;
    }

    public int getHitboxRoof() {
        return hitboxRoof;
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }

    public Bitmap getHouseSmallImg() {
        return Bitmap.createScaledBitmap(houseImg, GameConstants.Sprite.DEFAULT_SIZE * 4, GameConstants.Sprite.DEFAULT_SIZE * 4, false);
    }

    public int getHitboxFloor() {
        return hitboxFloor;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }
}