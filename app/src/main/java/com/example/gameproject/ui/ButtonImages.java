package com.example.gameproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameproject.R;
import com.example.gameproject.helpers.interfaces.BitmapMethods;
import com.example.gameproject.main.MainActivity;

public enum ButtonImages implements BitmapMethods {

    MENU_START(R.drawable.icons_ui, 275, 242, 275, 276, 90, 27, 90, 25),

    PLAYING_MENU(R.drawable.icons_ui, 773, 100, 805, 102, 22, 24, 22, 22),
    PLAYING_SETTING(R.drawable.icons_ui, 837, 228, 869, 230, 22, 24, 22, 22),
    SETTINGS_BACK(R.drawable.icons_ui, 837, 132, 869, 134, 22, 24, 22, 22),

    SHOP_APPROVE(R.drawable.icons_ui, 837, 100, 869, 102, 22, 24, 22, 22),
    SHOP_ADD(R.drawable.icons_ui, 837, 164, 869, 166, 22, 24, 22, 22),
    SHOP_REDUCE(R.drawable.icons_ui, 837, 196, 869, 198, 22, 24, 22, 22),

    SETTINGS_VOLUMES(R.drawable.icons_ui, 406, 98, 406, 114, 4, 12, 4, 12),
    PLAYING_INVENTORY(R.drawable.items_basic, 33, 84, 49, 81, 14, 11, 14, 14),

    SHOP_SET_SKIN(R.drawable.icons_ui, 610, 167, 610, 135, 28, 18, 28, 18),

    EMPTY(R.drawable.icons_ui, 275, 178, 275, 212, 90, 27, 90, 25),
    EMPTY_SMALL(R.drawable.icons_ui, 837, 4, 869, 6, 22, 24, 22, 22),
    EMPTY_SUPER_SMALL(R.drawable.icons_ui, 711, 37, 742, 39, 18, 20, 18, 18),

    PLAYING_DEBUG(R.drawable.icons_ui, 709, 132, 741, 134, 22, 24, 22, 22),
    DOOR_IMAGE(R.drawable.shop_backgrawnd_items, 67, 29, 128, 29, 51, 56, 51, 56),
    CHEST(R.drawable.shop_backgrawnd_items, 303, 70, 330, 65, 23, 15, 23, 20);


    private final int width;
    private final int height;
    private final Bitmap normal;
    private final Bitmap pushed;

    ButtonImages(int resID, int x1, int y1, int x2, int y2, int width1, int height1, int width2, int height2) {
        options.inScaled = false;
        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        normal = getScaledBitmap(Bitmap.createBitmap(buttonAtlas, x1, y1, width1, height1));
        pushed = getScaledBitmap(Bitmap.createBitmap(buttonAtlas, x2, y2, width2, height2));
        this.width = normal.getWidth();
        this.height = normal.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBtnImg(boolean isBtnPushed) {
        return isBtnPushed ? pushed : normal;
    }

}
