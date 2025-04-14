package com.example.gameproject.entities.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum Weapons implements BitmapMethods {

    BIG_SWORD(R.drawable.big_sword,1),
    SHURIKEN(R.drawable.shuriken,0.7f),
    SHADOW(R.drawable.shadow,1);

    final Bitmap weaponImg;

    Weapons(int resId, float multiplier) {
        options.inScaled = false;
        weaponImg = getMultiplierBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options), multiplier,multiplier);
    }

    public Bitmap getWeaponImg() {
        return weaponImg;
    }

    public int getWidth() {
        return weaponImg.getWidth();
    }

    public int getHeight() {
        return weaponImg.getHeight();
    }
}
