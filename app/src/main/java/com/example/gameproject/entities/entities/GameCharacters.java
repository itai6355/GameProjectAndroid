package com.example.gameproject.entities.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameCharacters implements BitmapMethods {

    BOY(R.drawable.boy_sprite_sheet),
    EGG_BOY(R.drawable.eggboy_sprite_sheet),
    EGG_GIRL(R.drawable.eggirl_spritesheet),
    ESKIMOS(R.drawable.eskimo_spritesheet),
    INSPECTOR(R.drawable.inspector_spritesheet),
    FIGHTER(R.drawable._fighter_spritesheet),
    HUNTER(R.drawable.hunter_spritesheet),
    RED_NINJA(R.drawable.red_ninja_spritesheets),
    VILLAGER_DAD(R.drawable.villager_dad),
    VILLAGER_MOM(R.drawable.villager_woman),
    VILLAGER_BOY(R.drawable.villager_boy),
    VILLAGER_GREEN(R.drawable.villager_green),
    VILLAGER_BLACK(R.drawable.villager_black),
    VILLAGER_OLIVE(R.drawable.villager_olive);


    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites = new Bitmap[7][4];


    GameCharacters(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstants.Sprite.DEFAULT_SIZE * i, GameConstants.Sprite.DEFAULT_SIZE * j, GameConstants.Sprite.DEFAULT_SIZE, GameConstants.Sprite.DEFAULT_SIZE));
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos, int xPos) {
        return sprites[yPos][xPos];
    }


}