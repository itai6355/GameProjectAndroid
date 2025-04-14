package com.example.gameproject.helpers;

import android.graphics.Color;
import android.graphics.Paint;

public final class Paints {
    public static final Paint BLACK_PAINT = new Paint();
    public static final Paint WHITE_PAINT = new Paint();
    public static final Paint HITBOX_PAINT = new Paint();
    public static final Paint GREEN_PAINT = new Paint();
    public static final Paint GOLD_PAINT = new Paint();
    public static final Paint BLUE_PAINT = new Paint();
    public static final Paint TEXT_PAINT = new Paint();
    public static final Paint BIG_TEXT_PAINT = new Paint();
    public static final Paint HELTH_BAR_RED_PAINT = new Paint();
    public static final Paint HELTH_BAR_BLACK_PAINT = new Paint();
    public static final Paint CIRCLE_PAINT = new Paint();
    public static final Paint MEDIOM_BLACK_PAINT = new Paint();

    static {
        initPaint();
    }

    private static void initPaint() {
        BLACK_PAINT.setColor(android.graphics.Color.BLACK);
        BLACK_PAINT.setTextSize(30);
        BLACK_PAINT.setStyle(Paint.Style.FILL);
        BLACK_PAINT.setStrokeWidth(3);

        BIG_TEXT_PAINT.setColor(Color.BLACK);
        BIG_TEXT_PAINT.setTextSize(55);
        BIG_TEXT_PAINT.setStrokeWidth(3);
        BIG_TEXT_PAINT.setStyle(Paint.Style.STROKE);

        WHITE_PAINT.setTextSize(100);
        WHITE_PAINT.setColor(0xFFFFFFFF);

        GOLD_PAINT.setColor(Color.YELLOW);
        GOLD_PAINT.setTextSize(45);
        GOLD_PAINT.setStrokeWidth(3);
        GOLD_PAINT.setStyle(Paint.Style.STROKE);

        GREEN_PAINT.setColor(Color.GREEN);
        GREEN_PAINT.setTextSize(45);
        GREEN_PAINT.setStrokeWidth(3);
        GREEN_PAINT.setStyle(Paint.Style.STROKE);

        MEDIOM_BLACK_PAINT.setColor(0xFF000000);
        MEDIOM_BLACK_PAINT.setTextSize(10 * GameConstants.Sprite.SCALE_MULTIPLIER);

        HELTH_BAR_RED_PAINT.setStrokeWidth(10);
        HELTH_BAR_RED_PAINT.setStyle(Paint.Style.STROKE);
        HELTH_BAR_RED_PAINT.setColor(Color.RED);

        HELTH_BAR_BLACK_PAINT.setStrokeWidth(14);
        HELTH_BAR_BLACK_PAINT.setStyle(Paint.Style.STROKE);
        HELTH_BAR_BLACK_PAINT.setColor(Color.BLACK);

        CIRCLE_PAINT.setColor(Color.RED);
        CIRCLE_PAINT.setStrokeWidth(5);
        CIRCLE_PAINT.setStyle(Paint.Style.STROKE);

        TEXT_PAINT.setColor(Color.BLACK);
        TEXT_PAINT.setTextSize(55);
        TEXT_PAINT.setStrokeWidth(5);
        TEXT_PAINT.setStyle(Paint.Style.STROKE);

        HITBOX_PAINT.setColor(android.graphics.Color.RED);
        HITBOX_PAINT.setTextSize(20);
        HITBOX_PAINT.setStyle(Paint.Style.STROKE);
        HITBOX_PAINT.setStrokeWidth(2);


        BLUE_PAINT.setColor(Color.parseColor("#151328"));
        BLUE_PAINT.setStyle(Paint.Style.FILL);
    }
}
