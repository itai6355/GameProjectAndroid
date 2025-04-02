package com.example.gameproject.gamestates.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum ShopImages implements BitmapMethods {

    SHOP_SLOTH_1(R.drawable.ui_1, 6, 3, 36, 36, 1),
    SHOP_SLOTH_2(R.drawable.ui_1, 54, 3, 36, 36, 1),
    SHOP_SLOTH_3(R.drawable.ui_1, 99, 0, 42, 42, 0.9f),

    SHOP_BAR_1(R.drawable.ui_1, 0, 47, 48, 13, 1.5f, 1.5f),
    SHOP_BAR_2(R.drawable.ui_1, 50, 47, 44, 14, 1),
    SHOP_BAR_3(R.drawable.ui_1, 97, 47, 46, 13, 1),

    SHOP_INVENTORY_MOUSE(R.drawable.icons_ui, 388, 4, 24, 25, 1.37f),

    SHOP_BUY_BACKGRAWND(R.drawable.ui_2, 0, 96, 48, 32, 5f),
    SHOP_BUY_BLOCK(R.drawable.ui_1, 54, 3, 36, 36, 2f),
    SHOP_BAR_2_SCALED(R.drawable.ui_1, 50, 47, 44, 14, 1.7f),


    SHOP_BRICK_BOX_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap, 639, 159, 97, 98, 0.5f),
    SHOP_BRICK_BOX_DOUBLED_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap, 640, 625, 97, 96, 0.6f),
    SHOP_BRICK_LINE_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap, 304, 526, 143, 14, 1),

    SHOP_DOOR_CLOSED_BACKGRAWND(R.drawable.shop_backgrawnd_items, 68, 28, 47, 59, 1),
    SHOP_WINDOW_BACKGRAWND(R.drawable.shop_backgrawnd_items, 28, 22, 30, 56, 1),
    SHOP_BARREL_BACKGRAWND(R.drawable.shop_backgrawnd_items, 444, 59, 23, 27, 1),
    SHOP_TREASURE_BOX_BACKGRAWND(R.drawable.shop_backgrawnd_items, 303, 70, 23, 15, 1),
    SHOP_LADDER_BACKGRAWND(R.drawable.shop_backgrawnd_items, 196, 95, 24, 75, 1),
    SHOP_LAMP(R.drawable.shop_backgrawnd_items, 432, 113, 26, 31, 0.7f),

    SHOP_ARROW_LEFT(R.drawable.arrow_left, 1, 1, 32, 35, 0.4f),
    SHOP_ARROW_RIGHT(R.drawable.arrow_right, 0, 0, 32, 35, 0.4f),

    CHARACTER_SHOP_BOOK(R.drawable.wooden_ui, 38, 1666, 249, 280, 0.6f);


    final int width, height;
    private final Bitmap atlas;
    private final Bitmap image;


    ShopImages(int resID, int x, int y, int width, int height, float multiplier) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), multiplier, multiplier);
        this.width = (int) (width * multiplier * GameConstants.Sprite.SCALE_MULTIPLIER);
        this.height = (int) (height * multiplier * GameConstants.Sprite.SCALE_MULTIPLIER);
    }


    ShopImages(int resID, int x, int y, int width, int height, float multiplierX, float multiplierY) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), multiplierX, multiplierY);
        this.width = (int) (width * multiplierX * GameConstants.Sprite.SCALE_MULTIPLIER);
        this.height = (int) (height * multiplierY * GameConstants.Sprite.SCALE_MULTIPLIER);
    }

    public Bitmap getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
