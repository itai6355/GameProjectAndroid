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

    HOUSE_ONE(0, 0, 64, 48, 23, 42, 9, 36),
    HOUSE_TWO(64, 4, 62, 44, 23, 36, 9, 31),
    HOUSE_SIX(304, 0, 64, 48, 39, 40, 15, 35),
    HOUSE_NINE(0, 48, 64, 63, 24, 62, 22, 51);


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

    /** Creates a brand-new inside GameMap for this building type, chosen randomly from the
     *  layouts defined in MapHelper. Every call returns a fresh map instance so each
     *  placed building gets its own independent interior. */
    public GameMap createInsideMap() {
        Random rng = new Random();
        switch (this) {
            case HOUSE_ONE:
            case HOUSE_TWO: {
                // Regular-sized house: pick one of the two reg layouts
                int[][] arr = MapHelper.getInsideRegHouseArr();
                ArrayList<com.example.gameproject.entities.objects.GameObject> objects =
                        rng.nextBoolean()
                                ? MapHelper.getObjectsReg1()
                                : MapHelper.getObjectsReg2();
                return new GameMap(arr, Tiles.INSIDE, objects, MapHelper.getParticles());
            }
            case HOUSE_SIX: {
                // Big house: pick one of three big layouts
                int[][] arr = MapHelper.getInsideFlatHouseArray();
                int pick = rng.nextInt(3);
                ArrayList<com.example.gameproject.entities.objects.GameObject> objects;
                switch (pick) {
                    case 0:  objects = MapHelper.getObjectsFlat1(); break;
                    case 1:  objects = MapHelper.getObjectsFlat2(); break;
                    default: objects = MapHelper.getObjectsFlat3(); break;
                }
                return new GameMap(arr, Tiles.INSIDE, objects, MapHelper.getParticles());
            }
            case HOUSE_NINE: {
                // Blacksmith / mail house
                int[][] arr = rng.nextBoolean()
                        ? MapHelper.getInsideBlacksmithHouseArray()
                        : MapHelper.getInsideMailHouseArray();
                ArrayList<com.example.gameproject.entities.objects.GameObject> objects =
                        rng.nextBoolean()
                                ? MapHelper.getObjectsMail()
                                : MapHelper.getObjectsGreen1();
                return new GameMap(arr, Tiles.INSIDE, objects, MapHelper.getParticles());
            }
            default: {
                int[][] arr = MapHelper.getInsideRegHouseArr();
                return new GameMap(arr, Tiles.INSIDE,
                        MapHelper.getObjectsReg1(), MapHelper.getParticles());
            }
        }
    }

    /** The tile-grid position of the doorway exit inside the building
     *  (bottom-center of the door tile row). Derived from the array arrays. */
    public PointF getInsideDoorwayPoint() {
        switch (this) {
            case HOUSE_SIX:
            case HOUSE_NINE:
                // 10-column arrays: door column index 4, last row
                return new PointF(4 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f,
                        9 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f);
            default:
                // 7-column arrays: door column index 3, last row
                return new PointF(3 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f,
                        6 * GameConstants.Sprite.SIZE + GameConstants.Sprite.SIZE / 2f);
        }
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
}