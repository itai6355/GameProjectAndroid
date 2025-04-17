package com.example.gameproject.entities.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

import java.util.Random;


public enum Weapons implements BitmapMethods {

    BIG_SWORD(R.drawable.big_sword, 1),
    SHURIKEN(R.drawable.shuriken, 2, 2, 13, 13),
    SHURIKEN_SPIN(R.drawable.shuriken, 17, 2, 14, 13),
    SHADOW(R.drawable.shadow, 1),
    FIREBALL(R.drawable.fireball, 49, 1, 15, 15),
    FIREBALL1(R.drawable.fireball, 33, 1, 15, 15),
    FIREBALL2(R.drawable.fireball, 16, 1, 15, 15),
    FIREBALL3(R.drawable.fireball, 0, 1, 15, 15);

    final Bitmap weaponImg;

    Weapons(int resId, float multiplier) {
        options.inScaled = false;
        weaponImg = getMultiplierBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options), multiplier, multiplier);
    }

    Weapons(int resId, int x, int y, int width, int height) {
        options.inScaled = false;
        weaponImg = getScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options), x, y, width, height));
    }

    public static Bitmap getSuriken(boolean isSHorikenTilted) {
        if (isSHorikenTilted) {
            return SHURIKEN_SPIN.getWeaponImg();
        } else {
            return SHURIKEN.getWeaponImg();
        }
    }
    public static Bitmap getFireball() {
        return switch (new Random().nextInt(4)) {
            case 1 -> FIREBALL1.getWeaponImg();
            case 2 -> FIREBALL2.getWeaponImg();
            case 3 -> FIREBALL3.getWeaponImg();
            default -> FIREBALL.getWeaponImg();
        };
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
