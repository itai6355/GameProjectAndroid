package com.example.gameproject.helpers.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameStateInterface {

    void update(double delta);
    void render(Canvas canvas);
    void touchEvents(MotionEvent event);
}
