package com.example.gameproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum ButtonImages implements BitmapMethods {

    //TODO: need rework for it to fit to the style (;

    MENU_START(R.drawable.icons_ui, 275, 242, 275, 276, 90, 27, 90, 25),
    PLAYING_MENU(R.drawable.icons_ui, 773, 100, 805, 102, 22, 24, 22, 22),
    EMPTY(R.drawable.icons_ui, 275, 178, 275, 212, 90, 27, 90, 25);


    private int width, height;
    private Bitmap normal, pushed;

    ButtonImages(int resID, int x1, int y1, int x2, int y2, int width1, int height1, int width2, int height2) {
        options.inScaled = false;
        this.width = width1;
        this.height = height1;
        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        normal = Bitmap.createBitmap(buttonAtlas, x1, y1, width1, height1);
        pushed = Bitmap.createBitmap(buttonAtlas, x2, y2, width2, height2);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBtnImg(boolean isBtnPushed) {
        return isBtnPushed ? pushed : normal;
    }
}
