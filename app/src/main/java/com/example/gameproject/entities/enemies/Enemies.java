package com.example.gameproject.entities.enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum Enemies implements BitmapMethods {

    DARK_NINJA(R.drawable.ninjamageblack_sprite_sheet), MASKED_RAKKON(R.drawable.maskedrakoon_spriteshhets), GOLDEN_MASKED_RAKKON(R.drawable.maskgoldracoon_sprite_sheet), SKELETON(R.drawable.skeleton_spritesheet);

    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites = new Bitmap[7][4];


    Enemies(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
    }

    public static Enemies getRndMaskedRaccoon() {
        return Math.random() < 0.2 ? GOLDEN_MASKED_RAKKON : MASKED_RAKKON;
    }

    public static Enemies getRandomEnemy() {
        double random = Math.random();

        if (random < 0.1) return DARK_NINJA;
        else if (random < 0.6) return getRndMaskedRaccoon();
        else return SKELETON;
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos, int xPos) {
        return sprites[yPos][xPos];
    }

}
