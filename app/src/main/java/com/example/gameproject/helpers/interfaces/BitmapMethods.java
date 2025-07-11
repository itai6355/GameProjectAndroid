package com.example.gameproject.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.helpers.var.GameConstants;

public interface BitmapMethods {

    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER, bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER, false);
    }

    default Bitmap getMultiplierBitmap(Bitmap bitmap, float Xmultiplier, float Ymultiplier) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * GameConstants.Sprite.SCALE_MULTIPLIER * Xmultiplier), (int) (bitmap.getHeight() * GameConstants.Sprite.SCALE_MULTIPLIER * Ymultiplier), false);
    }

    default Bitmap deScaledBitmap(Bitmap bitmap, float multiplier) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * multiplier), (int) (bitmap.getHeight() * multiplier), false);
    }

    default Bitmap getSmallItemSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE, GameConstants.Sprite.SIZE, false);
    }

    default Bitmap deSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE / 2, GameConstants.Sprite.SIZE / 2, false);
    }

    default Bitmap SmalldeSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE / 4, GameConstants.Sprite.SIZE / 4, false);
    }

    default Bitmap regSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE / 4 * 3, GameConstants.Sprite.SIZE / 4 * 3, false);
    }

    default Bitmap getSmallestItemSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.DEFAULT_SIZE * 5, GameConstants.Sprite.DEFAULT_SIZE * 5, false);
    }

    default Bitmap getItemBiggerSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, GameConstants.Sprite.SIZE * 2, GameConstants.Sprite.SIZE * 2, false);
    }

    default Bitmap getItemSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 24 * GameConstants.Sprite.SCALE_MULTIPLIER, 24 * GameConstants.Sprite.SCALE_MULTIPLIER, false);
    }
}
