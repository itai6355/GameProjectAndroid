package com.example.gameproject.entities.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum Icons implements BitmapMethods {

    BOY_ICON(R.drawable.boy_icon),
    EGG_BOY_ICON(R.drawable.eggboy_faceset),
    EGG_GIRL_ICON(R.drawable.eggirl_faceset),
    ESKIMOS_ICON(R.drawable.eskimo_faceset),
    INSPECTOR_ICON(R.drawable.inspector_faceset),
    FIGHTER_ICON(R.drawable.red_fighter_faceset),
    HUNTER_ICON(R.drawable.hunter_faceset),
    RED_NINJA_ICON(R.drawable.red_ninja_faceset),
    MASTER_ICON(R.drawable.master_faceset),
    MONK_ICON(R.drawable.monk_faceset),
    NINJABLUE2_ICON(R.drawable.ninjablue2_faceset),
    NINJABLUE_ICON(R.drawable.ninjablue_faceset),
    NINJABOMB_ICON(R.drawable.ninjabomb_faceset),
    NINJADARK_ICON(R.drawable.ninjadark_faceset),
    NINJAESKIMO_ICON(R.drawable.ninjaeskimo_faceset),
    NINJAGRAY_ICON(R.drawable.ninjagray_faceset),
    NINJAGREEN_ICON(R.drawable.ninjagreen_faceset),
    NINJAMASKED_ICON(R.drawable.ninjamasked_faceset),
    NINJARED_ICON(R.drawable.ninjared_faceset),
    NINJAYELLOW_ICON(R.drawable.ninjayellow_faceset),
    NOBLE_ICON(R.drawable.noble_faceset),
    OLDMAN2_ICON(R.drawable.oldman2_faceset),
    OLDMAN3_ICON(R.drawable.oldman3_faceset),
    OLDMAN_ICON(R.drawable.oldman_faceset),
    PRINCESS_ICON(R.drawable.princess_faceset),
    REDNINJA3_ICON(R.drawable.redninja3_faceset),
    KNIGHT_ICON(R.drawable.res_faceset),
    ROBOTGREEN_ICON(R.drawable.robotgreen_faceset),
    ROBOTGREY_ICON(R.drawable.robotgrey_faceset),
    SAMURAIBLUE_ICON(R.drawable.samuraiblue_faceset),
    SAMURAIRED_ICON(R.drawable.samuraired_faceset),
    SAMURAI_ICON(R.drawable.samurai_faceset),
    SORCERERBLACK_ICON(R.drawable.sorcererblack_faceset),
    SORCERERORANGE_ICON(R.drawable.sorcererorange_faceset),
    STATUE_ICON(R.drawable.statue_faceset),
    SULTAN2_ICON(R.drawable.sultan2_faceset),
    SULTAN_ICON(R.drawable.sultan_faceset),
    VAMPIRE_ICON(R.drawable.vampire_faceset);

    private final Bitmap image;

    Icons(int resID) {
        options.inScaled = false;
        image = deScaledBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options), 150, 150, false), 0.8f);
    }

    public Bitmap getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
