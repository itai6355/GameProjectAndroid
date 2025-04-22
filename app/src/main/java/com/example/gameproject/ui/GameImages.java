package com.example.gameproject.ui;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameImages implements BitmapMethods {

    SETTING_MENU(R.drawable.setting_menu, 0, 0, 118, 144, 1), MENU(R.drawable.setting_menu, 138, 0, 118, 144, 1),

    SOUND_ICON(R.drawable.button_ui, 99, 162, 11, 11, 1), SILENT_ICON(R.drawable.button_ui, 115, 162, 11, 11, 1), SHOP_ICON(R.drawable.button_ui, 130, 163, 12, 10, 1),

    BACKGRAWND(R.drawable.backgrawnd2, GAME_WIDTH, GAME_HEIGHT),

    INVENTORY_SLOTH(R.drawable.icons_ui, 59, 107, 26, 26, 1), INVENTORY_MOUSE(R.drawable.icons_ui, 388, 4, 24, 25, 1.2f),

    HUNGER_FULL(R.drawable.meat, 0.7f), HUNGER_EMPTY(R.drawable.meat_empty, 0.7f),

    TALKING_BUBBLE(R.drawable.talk_buble, 0, 0, 108, 35, 0.7f),

    LOADING11(R.drawable.loading, 2, 2, 44, 44, 2), LOADING12(R.drawable.loading, 50, 2, 44, 44, 2), LOADING13(R.drawable.loading, 98, 2, 44, 44, 2), LOADING14(R.drawable.loading, 146, 2, 44, 44, 2), LOADING15(R.drawable.loading, 194, 2, 44, 44, 2),

    LOADING21(R.drawable.loading, 3, 51, 42, 42, 2), LOADING22(R.drawable.loading, 51, 51, 42, 42, 2), LOADING23(R.drawable.loading, 99, 51, 42, 42, 2), LOADING24(R.drawable.loading, 147, 51, 42, 42, 2), LOADING25(R.drawable.loading, 195, 51, 42, 42, 2),

    LOADING31(R.drawable.loading, 193, 97, 46, 46, 2), LOADING32(R.drawable.loading, 145, 97, 46, 46, 2), LOADING33(R.drawable.loading, 97, 97, 46, 46, 2), LOADING34(R.drawable.loading, 49, 97, 46, 46, 2), LOADING35(R.drawable.loading, 1, 97, 46, 46, 2),


    COIN_SMALL(R.drawable.coins, 0, 0, 16, 16, 0.7f),

    ICON_BOX(R.drawable.icons_ui, 245, 101, 38, 38, 1), PLAYER_BOX(R.drawable.icons_ui, 275, 212, 90, 25, 2);


    private final Bitmap atlas;
    private final Bitmap image;

    GameImages(int resID, int x, int y, int width, int height, float multiplier) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), multiplier, multiplier);
    }

    GameImages(int resID, int width, int height) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = Bitmap.createScaledBitmap(atlas, width, height, false);
    }

    GameImages(int resID, float multiplier) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(atlas, multiplier, multiplier);
    }

    public static Bitmap getLoadingImage(int sprite, int index) {
        if (sprite == 1) {
            return switch (index) {
                case 0 -> LOADING11.getImage();
                case 1 -> LOADING12.getImage();
                case 2 -> LOADING13.getImage();
                case 3 -> LOADING14.getImage();
                default -> LOADING15.getImage();
            };
        } else if (sprite == 2) {
            return switch (index) {
                case 0 -> LOADING21.getImage();
                case 1 -> LOADING22.getImage();
                case 2 -> LOADING23.getImage();
                case 3 -> LOADING24.getImage();
                default -> LOADING25.getImage();
            };
        } else if (sprite == 3) {
            return switch (index) {
                case 0 -> LOADING31.getImage();
                case 1 -> LOADING32.getImage();
                case 2 -> LOADING33.getImage();
                case 3 -> LOADING34.getImage();
                default -> LOADING35.getImage();
            };
        } else {
            return LOADING11.getImage();
        }
    }

    public Bitmap getImage() {
        return image;
    }
    public Bitmap getSmallImage() {
        return Bitmap.createScaledBitmap(image, (int) (image.getWidth() * 0.2), (int) (image.getHeight() * 0.2), false);
    }
}
