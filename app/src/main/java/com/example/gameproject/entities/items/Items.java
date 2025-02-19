package com.example.gameproject.entities.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Items implements BitmapMethods {

    EMPTY_POT(R.drawable.empty_pot),
    MEDIPACK(R.drawable.medipack),
    FISH(R.drawable.fish),
    COIN(R.drawable.coins, 16, 16, 14);

    final Bitmap atlas;
    final Bitmap[] images;
    final boolean isAni;
    final int amount;

    Items(int resID, int width, int height, int amount) {
        options.inScaled = false;
        isAni = true;
        this.amount = amount;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[amount];
        for (int i = 0; i < amount; i++)
            images[i] = getScaledBitmap(Bitmap.createBitmap(atlas, i * width, 0, width, height));
    }

    Items(int resID) {
        options.inScaled = false;
        isAni = false;
        this.amount = 1;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        images = new Bitmap[1];
        images[0] = atlas;
    }


    public Bitmap getImage(int index) {
        return images[index];
    }

    public Bitmap getImage() {
        return images[0];
    }

    public boolean isAni() {
        return isAni;
    }

    public int getAmount() {
        return amount;
    }
}
