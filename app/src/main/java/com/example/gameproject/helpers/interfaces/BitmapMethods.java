package com.example.gameproject.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.helpers.GameConstants;

public interface BitmapMethods {

    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER, bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER, false);
    }

    default Bitmap getMultiplierBitmap(Bitmap bitmap, float Xmultiplier, float Ymultiplier) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER * Xmultiplier), (int) (bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER * Ymultiplier), false);
    }

    default Bitmap getSmallItemSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE, GameConstants.Sprite.SIZE, false);
    }

    default Bitmap getItemSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 24 * GameConstants.Sprite.SCALE_MULTIPLIER, 24 * GameConstants.Sprite.SCALE_MULTIPLIER, false);
    }
}
