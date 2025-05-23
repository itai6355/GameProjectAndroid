package com.example.gameproject.entities.objects;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum GameObjects implements BitmapMethods {

    PILLAR_YELLOW(0, 6, 16, 42, 32, 38, false),
    STATUE_ANGRY_YELLOW(16, 1, 32, 47, 24, 35, false),
    MONK_STATUE_BALL_YELLOW(49, 2, 30, 30, 16, 26, false),
    MONK_STATUE_YELLOW(81, 2, 30, 30, 16, 26, false),
    SOLDIER_SPEAR_YELLOW(112, 1, 16, 31, 23, 28, false),
    PLANTER_STICKS_YELLOW(128, 11, 16, 20, 12, 17, false),
    CUBE_YELLOW(32, 48, 16, 16, 3, 13, false),
    FROG_YELLOW(48, 38, 32, 26, 16, 24, false),
    SOLDIER_SWORD_YELLOW(81, 32, 31, 32, 20, 29, false),
    PILLAR_SHORT_YELLOW(112, 32, 16, 32, 21, 30, false),
    PILLAR_SNOW_YELLOW(128, 32, 16, 32, 21, 30, false),
    PILLAR_GREEN(0, 70, 16, 42, 32, 38, false),
    STATUE_ANGRY_GREEN(16, 65, 32, 47, 24, 35, false),
    MONK_STATUE_BALL_GREEN(49, 66, 30, 30, 16, 26, false),
    MONK_STATUE_GREEN(81, 66, 30, 30, 16, 26, false),
    SOLDIER_SPEAR_GREEN(112, 65, 16, 31, 23, 28, false),
    PLANTER_STICKS_GREEN(128, 75, 16, 20, 12, 17, false),
    CUBE_GREEN(32, 112, 16, 16, 3, 13, false),
    FROG_GREEN(48, 102, 32, 26, 16, 24, false),
    SOLDIER_SWORD_GREEN(81, 96, 31, 32, 20, 29, false),
    PILLAR_SHORT_GREEN(112, 96, 16, 32, 21, 30, false),
    PILLAR_SNOW_GREEN(128, 96, 16, 32, 21, 30, false),
    POT_ONE_FULL(144, 0, 16, 19, 10, 17, false),
    POT_ONE_EMPTY(160, 0, 16, 19, 10, 17, false),
    POT_TWO_FULL(144, 19, 16, 21, 12, 19, false),
    POT_TWO_EMPTY(160, 20, 16, 20, 12, 19, false),
    BASKET_FULL_RED_FRUIT(144, 40, 16, 16, 5, 14, false),
    BASKET_FULL_CHICKEN(160, 40, 16, 16, 5, 14, false),
    BASKET_EMPTY(144, 56, 16, 16, 5, 14, false),
    BASKET_FULL_BREAD(160, 56, 16, 16, 5, 14, false),
    OVEN_SNOW_YELLOW(144, 73, 28, 39, 20, 35, false),
    OVEN_YELLOW(0, 129, 28, 28, 10, 24, false),
    OVEN_GREEN(28, 128, 30, 29, 10, 24, false),
    STOMP(58, 128, 16, 22, 10, 18, false),
    SMALL_POT_FULL(0, 112, 16, 13, 4, 10, false),
    SMALL_POT_EMPTY(16, 12, 16, 13, 4, 10, false),
    PLANT(R.drawable.tileset_element, 0, 120, 15, 24, 14, 23, false),
    DRAWERS(R.drawable.tileset_element, 32, 120, 16, 24, 24, 23, false),
    BOOK_SHELF_SMALL(R.drawable.tileset_element, 48, 113, 16, 29, 29, 29, false),
    BOOK_SHELF_SMALL_EMPTY(R.drawable.tileset_element, 64, 113, 16, 29, 29, 29, false),
    DRAWERS_BIG(R.drawable.tileset_element, 96, 121, 32, 23, 22, 23, false),
    BOOK_SHELF(R.drawable.tileset_element, 128, 112, 32, 30, 28, 30, false),
    BOOK_SHELF_EMPTY(R.drawable.tileset_element, 160, 113, 32, 29, 29, 29, false),
    CHAIR(R.drawable.tileset_element, 7, 147, 8, 11, 0, 11, false),
    BLUE_POT(R.drawable.tileset_element, 1, 160, 14, 15, 0, 14, false),
    PAINTING(R.drawable.tileset_element, 97, 168, 14, 6, 0, 6, false),
    BASEMENT_OAK(R.drawable.tileset_element, 97, 178, 14, 13, 0, 13, false),
    BASEMENT_BIRCH(R.drawable.tileset_element, 113, 178, 14, 13, 0, 13, false),
    BASEMENT_ACACIA(R.drawable.tileset_element, 129, 178, 14, 13, 0, 13, false),
    BASEMENT_PRISMARIN(R.drawable.tileset_element, 145, 178, 14, 13, 0, 13, false),
    POT_EMPTY(R.drawable.tileset_element, 33, 146, 14, 14, 0, 14, false),
    TABLE(R.drawable.tileset_element, 48, 145, 16, 15, 0, 15, false),
    TABLE2(R.drawable.tileset_element, 224, 2, 16, 14, 0, 14, false),
    TABLE3(R.drawable.tileset_element, 80, 119, 16, 12, 0, 12, false),
    WELL(R.drawable.tileset_element, 112, 27, 17, 21, 0, 20, false),
    PIGENS(R.drawable.tileset_element, 97, 48, 14, 29, 0, 29, false),

    CABINET(R.drawable.furniture, 96, 192, 80, 43, 0, 43, 0.85f, false),
    OVEN(R.drawable.furniture, 65, 192, 30, 43, 0, 43, 0.85f, false),
    REFRIGERATOR(R.drawable.furniture, 35, 196, 26, 55, 0, 55, 0.85f, false),
    SOFA_RIGHT(R.drawable.furniture, 0, 197, 22, 55, 0, 55, 0.85f, false),
    SOFA_DOWN(R.drawable.furniture, 88, 161, 49, 31, 31, 31, 0.85f, false),
    SMALL_SOFA_DOWN(R.drawable.furniture, 144, 160, 32, 30, 30, 30, 0.85f, false),
    SMALL_SOFA_UP(R.drawable.furniture, 176, 164, 32, 28, 0, 28, 0.85f, false),
    OAK_BOOKSHELF(R.drawable.furniture, 97, 66, 46, 44, 24, 44, 0.85f, false),
    BIRCH_BOOKSHELF(R.drawable.furniture, 33, 64, 46, 47, 47, 47, 0.85f, false),
    CHAIR_BIRCH_DOWN(R.drawable.furniture, 81, 5, 14, 26, 0, 26, 0.85f, false),
    CHAIR_BIRCH_RIGHT(R.drawable.furniture, 96, 4, 15, 27, 0, 27, 0.85f, false),
    CHAIR_OAK_DOWN(R.drawable.furniture, 128, 4, 16, 27, 20, 27, 0.85f, false),
    TABLE_BIG(R.drawable.furniture, 0, 64, 31, 46, 0, 46, 0.85f, false),
    CARPET(R.drawable.furniture, 160, 32, 48, 33, 0, 33, 1.5f, true),
    DRAWERS_OAK(R.drawable.furniture, 4, 117, 25, 35, 0, 35, 0.85f, false),
    TOOLSET_HANGING(R.drawable.furniture, 67, 241, 25, 14, 0, 3, 0.85f, false),
    CLOCK(R.drawable.furniture, 70, 112, 22, 46, 0, 40, 0.85f, false),
    PLANT2(R.drawable.furniture, 3, 168, 10, 23, 0, 21, false),
    STOOL(R.drawable.furniture, 193, 16, 14, 16, 0, 14, 0.85f, false),
    STOOL_HIGH(R.drawable.furniture, 176, 2, 16, 24, 0, 18, 0.85f, false),
    DESK(R.drawable.furniture, 190, 263, 16, 24, 0, 20, 0.85f, false),
    CABINET_P1(R.drawable.furniture, 96, 193, 112, 42, 0, 37, 0.85f, false),
    CABINET_P2(R.drawable.furniture, 187, 235, 21, 21, 0, 17, 0.85f, false);

    private final Bitmap objectImg;
    private final int width, height;
    int hitboxRoof, hitboxFloor, hitboxHeight;
    private final boolean walkable;

    GameObjects(int x, int y, int width, int height, int hitboxRoof, int hitboxFloor, boolean walkable) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.walkable = walkable;
        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.world_objects, options);
        objectImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    GameObjects(int resID, int x, int y, int width, int height, int hitboxRoof, int hitboxFloor, boolean walkable) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.walkable = walkable;
        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        objectImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    GameObjects(int resID, int x, int y, int width, int height, int hitboxRoof, int hitboxFloor, float scale, boolean walkable) {
        options.inScaled = false;
        this.width = (int) (width * scale);
        this.height = (int) (height * scale);
        this.walkable = walkable;
        this.hitboxRoof = (int) (hitboxRoof * scale);
        this.hitboxFloor = (int) (hitboxFloor * scale);
        this.hitboxHeight = (int) ((hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER * scale);
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        objectImg = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), scale, scale);
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public int getHitboxWidth() {
        return width * GameConstants.Sprite.SCALE_MULTIPLIER;
    }

    public Bitmap getObjectImg() {
        return objectImg;
    }

    public int getWidth() {
        return width * GameConstants.Sprite.SCALE_MULTIPLIER;
    }

    public int getHeight() {
        return height * GameConstants.Sprite.SCALE_MULTIPLIER;
    }

    public int getHitboxRoof() {
        return hitboxRoof * GameConstants.Sprite.SCALE_MULTIPLIER;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
