package com.example.gameproject.entities.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Items implements BitmapMethods {

    EMPTY_POT(R.drawable.empty_pot),
    MEDIPACK(R.drawable.medipack),
    FISH(R.drawable.fish);

    final Bitmap image;

    Items(int resID) {
        options.inScaled = false;
        image = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID));
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
