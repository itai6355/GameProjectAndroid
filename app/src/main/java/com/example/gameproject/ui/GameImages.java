package com.example.gameproject.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameImages implements BitmapMethods {


    MAINMENU_MENUBG(R.drawable.mainmenu_menubackground),
    DEATH_MENU_MENUBG(R.drawable.menu_youdied_background);

    private final Bitmap image;

    GameImages(int resID) {
        options.inScaled = false;
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
    }

    public Bitmap getImage() {
        return image;
    }
}
