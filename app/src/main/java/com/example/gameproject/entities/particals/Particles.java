package com.example.gameproject.entities.particals;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Particles implements BitmapMethods {

    POTION_EFFECT1(R.drawable.particles2, 7),
    POTION_EFFECT2(R.drawable.particles2, 6, new Rect(49, 183, 2, 2), new Rect(56, 182, 4, 4), new Rect(71, 181, 6, 6), new Rect(80, 182, 4, 4), new Rect(56, 182, 4, 4), new Rect(49, 183, 2, 2));


    final Bitmap atlas;
    final Bitmap[] images;
    final int amount;

    //                                not rect!!. left - x , top - y, right - width, bottom - height;
    Particles(int resID, int amount, Rect... hitboxes) {
        options.inScaled = false;
        this.atlas = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID), 64, 64, false);
        this.amount = amount;
        this.images = new Bitmap[amount];
        for (int i = 0; i < amount; i++)
            images[i] = getScaledBitmap(Bitmap.createBitmap(atlas, hitboxes[i].left, hitboxes[i].top, hitboxes[i].right, hitboxes[i].bottom));
    }

    public Bitmap getImages(int index) {
        return images[index];
    }

    public int getAmount() {
        return amount;
    }

    public float getMaxWidth() {
        float maxWidth = 0;
        for (Bitmap image : images)
            if (image.getWidth() > maxWidth)
                maxWidth = image.getWidth();
        return maxWidth;
    }
    public float getMaxHeight() {
        float maxHeight = 0;
        for (Bitmap image : images)
            if (image.getHeight() > maxHeight)
                maxHeight = image.getHeight();
        return maxHeight;
    }
}
