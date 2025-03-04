package com.example.gameproject.gamestates.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum ShopImages implements BitmapMethods {

    SHOP_SLOTH_1(R.drawable.ui_1, 6, 3, 36, 36, 1),
    SHOP_SLOTH_2(R.drawable.ui_1, 54, 3, 3, 36, 1),
    SHOP_SLOTH_3(R.drawable.ui_1, 99, 0, 42, 42, 0.9f),

    SHOP_BAR_1(R.drawable.ui_1, 0, 47, 48, 13, 1),
    SHOP_BAR_2(R.drawable.ui_1, 50, 47, 44, 14, 1),
    SHOP_BAR_3(R.drawable.ui_1, 97, 47, 46, 13, 1),

    SHOP_WALL_BOTTOM_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,64,704,147,32,1),
    SHOP_WALL_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,125,616,84,89,0.8f),
    SHOP_WALL_BRICK_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,198,588,74,70,1),


    SHOP_BRICK_BOX_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,640,159,97,98,0.6f),
    SHOP_BRICK_BOX_DOUBLED_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,640,625,97,96,0.6f),
    SHOP_BRICK_LINE_BACKGRAWND(R.drawable.shop_backgrawnd_tilemap,304,526,143,14,1),

    SHOP_DOOR_CLOSED_BACKGRAWND(R.drawable.shop_backgrawnd_items,68,28,47,59,1),
    SHOP_WINDOW_BACKGRAWND(R.drawable.shop_backgrawnd_items,27,22,32,35,1), //TODO: need fix
    SHOP_BARREL_BACKGRAWND(R.drawable.shop_backgrawnd_items,444,59,23,27,1),
    SHOP_TREASURE_BOX_BACKGRAWND(R.drawable.shop_backgrawnd_items,303,70,23,15,1),
    SHOP_LADDER_BACKGRAWND(R.drawable.shop_backgrawnd_items,196,95,24,75,1);

    private final Bitmap atlas;
    private final Bitmap image;


    ShopImages(int resID, int x, int y, int width, int height, float multiplier) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), multiplier, multiplier);
    }

    public Bitmap getImage() {
        return image;
    }
}
