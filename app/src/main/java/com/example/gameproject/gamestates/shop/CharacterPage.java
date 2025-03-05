package com.example.gameproject.gamestates.shop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.gameproject.entities.entities.GameCharacters;
import com.example.gameproject.entities.entities.Icons;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.main.MainActivity;

public class CharacterPage {

    private final GameCharacters skin;
    private final Icons icon;
    private final String name;
    private boolean bought = false;

    private final Paint textPaint = new Paint();


    int xIndex = 0, yIndex = 0;

    public CharacterPage(GameCharacters skin, Icons icon, String name) {
        this.skin = skin;
        this.icon = icon;
        this.name = name;
        initPaint();
    }


    public void draw(Canvas canvas) {
        //TODO: need fix and add animation. new some rework to the places
        float xStart = (float) (MainActivity.GAME_WIDTH / 2 - ShopImages.CHARACTER_SHOP_BOOk.getWidth() / 2);
        float yStart = (float) (MainActivity.GAME_HEIGHT / 2 - ShopImages.CHARACTER_SHOP_BOOk.getHeight() / 2);
        canvas.drawBitmap(icon.getImage(), xStart + icon.getWidth(), yStart + icon.getWidth(), null);

        canvas.drawBitmap(ShopImages.SHOP_BAR_3.getImage(), xStart + 2 * icon.getWidth() + 2 * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + GameConstants.Sprite.SCALE_MULTIPLIER, null);
        canvas.drawText(name, xStart + 2 * icon.getWidth() + 7 * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + 10 * GameConstants.Sprite.SCALE_MULTIPLIER, textPaint);
        canvas.drawBitmap(skin.getSprite(yIndex, xIndex), xStart + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, yStart + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public boolean isBought() {
        return bought;
    }

    public String getName() {
        return name;
    }

    private void initPaint() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textPaint.getTextSize() + 50);
        textPaint.setStrokeWidth(5);
        textPaint.setStyle(Paint.Style.STROKE);
    }


}
