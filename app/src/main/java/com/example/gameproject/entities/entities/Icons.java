package com.example.gameproject.entities.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Icons implements BitmapMethods {

    BOY_ICON(R.drawable.boy_icon),
    EGG_BOY_ICON(R.drawable.eggboy_faceset),
    EGG_GIRL_ICON(R.drawable.eggirl_faceset),
    ESKIMOS_ICON(R.drawable.eskimo_faceset),
    INSPECTOR_ICON(R.drawable.inspector_faceset),
    FIGHTER_ICON(R.drawable.red_fighter_faceset),
    HUNTER_ICON(R.drawable.hunter_faceset),
    RED_NINJA_ICON(R.drawable.red_ninja_faceset);

    private final Bitmap image;

    Icons(int resID) {
        options.inScaled = false;
        image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options), 150, 150, false);
    }

    public Bitmap getImage() {
        return image;
    }

    public int getWidth(){
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }
}
