package com.example.gameproject.entities.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;


public enum GameCharacters implements BitmapMethods {


    VILLAGER_DAD(R.drawable.villager_dad),
    VILLAGER_MOM(R.drawable.villager_woman),
    VILLAGER_BOY(R.drawable.villager_boy),
    VILLAGER_GREEN(R.drawable.villager_green),
    VILLAGER_BLACK(R.drawable.villager_black),
    VILLAGER_OLIVE(R.drawable.villager_olive),
    BOY(R.drawable.boy_sprite_sheet),
    EGG_BOY(R.drawable.eggboy_sprite_sheet),
    EGG_GIRL(R.drawable.eggirl_spritesheet),
    ESKIMOS(R.drawable.eskimo_spritesheet),
    INSPECTOR(R.drawable.inspector_spritesheet),
    FIGHTER(R.drawable._fighter_spritesheet),
    HUNTER(R.drawable.hunter_spritesheet),
    RED_NINJA(R.drawable.red_ninja_spritesheets),
    KNIGHT(R.drawable.knight_sprite_sheet),
    MASTER(R.drawable.master_sprite_sheet),
    MONK(R.drawable.monk_sprite_sheet),
    NINJABLUE2(R.drawable.ninjablue2_sprite_sheet),
    NINJABLUE(R.drawable.ninjablue_sprite_sheet),
    NINJABOMB(R.drawable.ninjabomb_sprite_sheet),
    NINJADARK(R.drawable.ninjadark_sprite_sheet),
    NINJAESKIMO(R.drawable.ninjaeskimo_sprite_sheet),
    NINJAGRAY(R.drawable.ninjagray_sprite_sheet),
    NINJAGREEN(R.drawable.ninjagreen_sprite_sheet),
    NINJAMASKED(R.drawable.ninjamasked_sprite_sheet),
    NINJARED(R.drawable.ninjared_sprite_sheet),
    NINJAYELLOW(R.drawable.ninjayellow_sprite_sheet),
    NOBLE(R.drawable.noble_sprite_sheet),
    OLDMAN2(R.drawable.oldman2_sprite_sheet),
    OLDMAN3(R.drawable.oldman3_sprite_sheet),
    OLDMAN(R.drawable.oldman_sprite_sheet),
    PRINCESS(R.drawable.princess_sprite_sheet),
    REDNINJA3(R.drawable.redninja3_sprite_sheet),
    ROBOTGREEN(R.drawable.robotgreen_sprite_sheet),
    ROBOTGREY(R.drawable.robotgrey_sprite_sheet),
    SAMURAIBLUE(R.drawable.samuraiblue_sprite_sheet),
    SAMURAI(R.drawable.samurai_sprite_sheet),
    SORCERERBLACK(R.drawable.sorcererblack_sprite_sheet),
    SORCERERORANGE(R.drawable.sorcererorange_sprite_sheet),
    STATUE(R.drawable.statue_sprite_sheet),
    SULTAN2(R.drawable.sultan2_sprite_sheet),
    SULTAN(R.drawable.sultan_sprite_sheet),
    VAMPIRE(R.drawable.vampire_sprite_sheet);


    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites = new Bitmap[7][4];

    public static int getValCharacter() {
        int count = 0;
        for (GameCharacters character : GameCharacters.values()){
            if (character != null && !character.name().startsWith("VILLAGER"))  {
                count++;
            }

        }
        return count;
    }

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