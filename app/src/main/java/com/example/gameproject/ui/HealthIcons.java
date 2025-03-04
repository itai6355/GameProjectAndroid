package com.example.gameproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum HealthIcons implements BitmapMethods {

    HEART_FULL(0),
    HEART_3Q(1),
    HEART_HALF(2),
    HEART_1Q(3),
    HEART_EMPTY(4);

    private final Bitmap icon;
    private final int widthHeight = 16;

    HealthIcons(int xPos) {
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.health_icons, options);
        icon = getScaledBitmap(Bitmap.createBitmap(atlas, xPos * widthHeight, 0, widthHeight, widthHeight));
    }

    public Bitmap getIcon() {
        return icon;
    }
}
