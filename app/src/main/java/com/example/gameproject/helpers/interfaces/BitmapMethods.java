package com.example.gameproject.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.helpers.GameConstants;

public interface BitmapMethods {

    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER, bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER, false);
    }

    default Bitmap getMultiplierBitmap(Bitmap bitmap, float multiplier) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER * multiplier), (int) (bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER * multiplier), false);
    }
}
