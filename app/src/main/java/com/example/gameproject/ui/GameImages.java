package com.example.gameproject.ui;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameImages implements BitmapMethods {

    SETTING_MENU(R.drawable.setting_menu, 0, 0, 118, 144, 1),
    MENU(R.drawable.setting_menu, 138, 0, 118, 144, 1),

    SOUND_ICON(R.drawable.button_ui, 99, 162, 11, 11, 1),
    SILENT_ICON(R.drawable.button_ui, 115, 162, 11, 11, 1),
    SHOP_ICON(R.drawable.button_ui, 130, 163, 12, 10, 1),

    //TODO: add backgrounds to each one that using this and remove this.
    BACKGRAWND(R.drawable.backgrawnd, GAME_WIDTH, GAME_HEIGHT),

    INVENTORY_SLOTH(R.drawable.icons_ui, 59, 107, 26, 26, 1),
    INVENTORY_MOUSE(R.drawable.icons_ui, 388, 4, 24, 25, 1.37f),

    COIN_SMALL(R.drawable.coins, 0,0,16, 16, 0.7f),

    ICON_BOX(R.drawable.icons_ui, 245, 101, 38, 38, 1),
    PLAYER_BOX(R.drawable.icons_ui, 275, 212, 90, 25, 2);


    private final Bitmap atlas;
    private final Bitmap image;

    GameImages(int resID, int x, int y, int width, int height, float multiplier) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getMultiplierBitmap(Bitmap.createBitmap(atlas, x, y, width, height), multiplier,multiplier);
    }

    GameImages(int resID, int width, int height) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = Bitmap.createScaledBitmap(atlas, width, height, false);
    }

    public Bitmap getImage() {
        return image;
    }
}
