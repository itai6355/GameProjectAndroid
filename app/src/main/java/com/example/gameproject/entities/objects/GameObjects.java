package com.example.gameproject.entities.objects;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum GameObjects implements BitmapMethods {

    PILLAR_YELLOW(0, 6, 16, 42, 32, 38),
    STATUE_ANGRY_YELLOW(16, 1, 32, 47, 24, 35),
    MONK_STATUE_BALL_YELLOW(49, 2, 30, 30, 16, 26),
    MONK_STATUE_YELLOW(81, 2, 30, 30, 16, 26),
    SOLDIER_SPEAR_YELLOW(112, 1, 16, 31, 23, 28),
    PLANTER_STICKS_YELLOW(128, 11, 16, 20, 12, 17),
    CUBE_YELLOW(32, 48, 16, 16, 3, 13),
    FROG_YELLOW(48, 38, 32, 26, 16, 24),
    SOLDIER_SWORD_YELLOW(81, 32, 31, 32, 20, 29),
    PILLAR_SHORT_YELLOW(112, 32, 16, 32, 21, 30),
    PILLAR_SNOW_YELLOW(128, 32, 16, 32, 21, 30),
    PILLAR_GREEN(0, 70, 16, 42, 32, 38),
    STATUE_ANGRY_GREEN(16, 65, 32, 47, 24, 35),
    MONK_STATUE_BALL_GREEN(49, 66, 30, 30, 16, 26),
    MONK_STATUE_GREEN(81, 66, 30, 30, 16, 26),
    SOLDIER_SPEAR_GREEN(112, 65, 16, 31, 23, 28),
    PLANTER_STICKS_GREEN(128, 75, 16, 20, 12, 17),
    CUBE_GREEN(32, 112, 16, 16, 3, 13),
    FROG_GREEN(48, 102, 32, 26, 16, 24),
    SOLDIER_SWORD_GREEN(81, 96, 31, 32, 20, 29),
    PILLAR_SHORT_GREEN(112, 96, 16, 32, 21, 30),
    PILLAR_SNOW_GREEN(128, 96, 16, 32, 21, 30),
    POT_ONE_FULL(144, 0, 16, 19, 10, 17),

    POT_ONE_EMPTY(160, 0, 16, 19, 10, 17),
    POT_TWO_FULL(144, 19, 16, 21, 12, 19),
    POT_TWO_EMPTY(160, 20, 16, 20, 12, 19),
    BASKET_FULL_RED_FRUIT(144, 40, 16, 16, 5, 14),
    BASKET_FULL_CHICKEN(160, 40, 16, 16, 5, 14),
    BASKET_EMPTY(144, 56, 16, 16, 5, 14),
    BASKET_FULL_BREAD(160, 56, 16, 16, 5, 14),
    OVEN_SNOW_YELLOW(144, 73, 28, 39, 20, 35),
    OVEN_YELLOW(0, 129, 28, 28, 10, 24),
    OVEN_GREEN(28, 128, 30, 29, 10, 24),
    STOMP(58, 128, 16, 22, 10, 18),
    SMALL_POT_FULL(0, 112, 16, 13, 4, 10),
    SMALL_POT_EMPTY(16, 12, 16, 13, 4, 10),

    //TODO: fix hotbox width.

    PLANT(R.drawable.tileset_element, 0, 120, 15, 24, 14, 23),
    DRAWERS(R.drawable.tileset_element, 32, 120, 16, 24, 24, 23),
    BOOK_SHELF_SMALL(R.drawable.tileset_element, 48, 113, 16, 29, 29, 29),
    BOOK_SHELF_SMALL_EMPTY(R.drawable.tileset_element, 64, 113, 16, 29, 29, 29),
    DRAWERS_BIG(R.drawable.tileset_element, 96, 121, 32, 23, 22, 23),
    BOOK_SHELF(R.drawable.tileset_element, 128, 112, 32, 30, 28, 30),
    BOOK_SHELF_EMPTY(R.drawable.tileset_element, 160, 113, 32, 29, 29, 29),
    CHAIR(R.drawable.tileset_element, 7, 147, 8, 11, 11, 11),
    BLUE_POT(R.drawable.tileset_element, 1, 160, 14, 15, 10, 14),
    PAINTING(R.drawable.tileset_element, 97, 168, 14, 6, 5, 6),
    BASEMENT_OAK(R.drawable.tileset_element, 97, 178, 14, 13, 11, 13),
    BASEMENT_BIRCH(R.drawable.tileset_element, 113, 178, 14, 13, 11, 13),
    BASEMENT_ACACIA(R.drawable.tileset_element, 129, 178, 14, 13, 11, 13),
    BASEMENT_PRISMARIN(R.drawable.tileset_element, 145, 178, 14, 13, 11, 13),
    POT_EMPTY(R.drawable.tileset_element, 33, 146, 14, 14, 14, 14),
    TABLE(R.drawable.tileset_element, 48, 145, 16, 15, 14, 15),
    TABLE2(R.drawable.tileset_element, 224, 2, 16, 14, 10, 14),
    TABLE3(R.drawable.tileset_element, 80, 119, 16, 12, 9, 12),
    WELL(R.drawable.tileset_element, 112, 27, 17, 21, 20, 20),
    PIGENS(R.drawable.tileset_element, 97, 48, 14, 29, 29, 29),

    CABINET(R.drawable.furniture, 96, 192, 80, 43, 0, 43, 0.85f),
    OVEN(R.drawable.furniture, 65, 192, 30, 43, 0, 43, 0.85f),
    REFRIGERATOR(R.drawable.furniture, 35, 196, 26, 55, 0, 55, 0.85f),
    SOFA_RIGHT(R.drawable.furniture, 0, 197, 22, 55, 0, 55, 0.85f),
    SOFA_DOWN(R.drawable.furniture, 88, 161, 49, 31, 31, 31, 0.85f),
    SMALL_SOFA_DOWN(R.drawable.furniture, 144, 160, 32, 30, 30, 30, 0.85f),
    SMALL_SOFA_UP(R.drawable.furniture, 176, 164, 32, 28, 28, 28, 0.85f),
    OAK_BOOKSHELF(R.drawable.furniture, 97, 66, 46, 44, 44, 44, 0.85f),
    BIRCH_BOOKSHELF(R.drawable.furniture, 33, 64, 46, 47, 47, 47, 0.85f),
    CHAIR_BIRCH_DOWN(R.drawable.furniture, 81, 5, 14, 26, 0, 26, 0.85f),
    CHAIR_BIRCH_RIGHT(R.drawable.furniture, 96, 4, 15, 27, 27, 27, 0.85f),
    TABLE_BIG(R.drawable.furniture, 0, 64, 31, 46, 0, 46, 0.85f),
    CARPET(R.drawable.furniture, 160, 32, 48, 33, 0, 0, 1.5f),//TODO: fix the hitbox
    DRAWERS_OAK(R.drawable.furniture, 4, 117, 25, 35, 35, 35, 0.85f);


    Bitmap objectImg;
    int width, height;
    int hitboxRoof, hitboxFloor, hitboxHeight;

    GameObjects(int x, int y, int width, int height, int hitboxRoof, int hitboxFloor) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.world_objects, options);
        objectImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    GameObjects(int resID, int x, int y, int width, int height, int hitboxRoof, int hitboxFloor) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        objectImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    GameObjects(int resID, int x, int y, int width, int height, int hitboxRoof, int hitboxFloor, float scale) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.hitboxRoof = hitboxRoof;
        this.hitboxFloor = hitboxFloor;
        this.hitboxHeight = (hitboxFloor - hitboxRoof) * GameConstants.Sprite.SCALE_MULTIPLIER;
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
}
