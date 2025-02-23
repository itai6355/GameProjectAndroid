package com.example.gameproject.ui;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameImages implements BitmapMethods {

    SETTING_MENU(R.drawable.setting_menu, 0, 0, 118, 144),
    MENU(R.drawable.setting_menu, 138, 0, 118, 144),
    SOUND_ICON(R.drawable.button_ui, 99, 162, 11, 11),
    SILENT_ICON(R.drawable.button_ui, 115, 162, 11, 11),
    BACKGRAWND(R.drawable.backgrawnd);

    private final Bitmap atlas;
    private Bitmap image;

    GameImages(int resID, int x, int y, int width, int height) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    GameImages(int resID) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        image = Bitmap.createScaledBitmap(atlas, GAME_WIDTH, GAME_HEIGHT, false);
    }

    public Bitmap getImage() {
        return image;
    }
}
