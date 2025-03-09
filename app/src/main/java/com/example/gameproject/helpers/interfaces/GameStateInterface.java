package com.example.gameproject.helpers.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.ui.CustomButton;

public interface GameStateInterface {

    void update(double delta);

    void render(Canvas canvas);

    void touchEvents(MotionEvent event);

    default boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(), e.getY());
    }
}
