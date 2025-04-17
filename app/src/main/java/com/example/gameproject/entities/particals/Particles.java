package com.example.gameproject.entities.particals;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Particles implements BitmapMethods {

    POTION_EFFECT(
            R.drawable.particles2, 6,
            fromWH(49, 183, 2, 2),
            fromWH(56, 182, 4, 4),
            fromWH(71, 181, 6, 6),
            fromWH(80, 182, 4, 4),
            fromWH(56, 182, 4, 4),
            fromWH(49, 183, 2, 2)
    );

    final Bitmap atlas;
    final Bitmap[] images;
    final int amount;

    Particles(int resID, int amount, Rect... hitboxes) {
        options.inScaled = false;
        atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        this.amount = amount;
        this.images = new Bitmap[amount];

        for (int i = 0; i < amount; i++) {
            Rect rect = hitboxes[i];
            int width = rect.width();
            int height = rect.height();

            Log.d("Particles", "Rect[" + i + "] = " + rect.left + "," + rect.top + " → " + width + "x" + height);
            Log.d("Particles", "Checking bounds: top(" + rect.top + ") + height(" + height + ") = " + (rect.top + height));
            Log.d("Particles", "Compare to atlas.height = " + atlas.getHeight());

            images[i] = getMultiplierBitmap(Bitmap.createBitmap(atlas, rect.left, rect.top, rect.width(), rect.height()), 0.7f, 0.7f);
            Log.d("Particles", "Atlas size: " + atlas.getWidth() + "x" + atlas.getHeight());

        }
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

    private static Rect fromWH(int x, int y, int width, int height) {
        return new Rect(x, y, x + width, y + height);
    }

    public float getCurrWidth(int index) {
        if (index < 0 || index >= amount)
            return 0;

        return images[index].getWidth();
    }

    public float getCurrHeight(int index) {
        if (index < 0 || index >= amount)
            return 0;
        return images[index].getHeight();
    }
}
