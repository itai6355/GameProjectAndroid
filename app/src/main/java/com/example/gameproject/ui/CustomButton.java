package com.example.gameproject.ui;

import android.graphics.RectF;
import android.view.MotionEvent;

public class CustomButton {

    private final RectF hitbox;

    private boolean pushed;
    private int pointerId = -1;

    public CustomButton(float x, float y, float width, float height) {
        hitbox = new RectF(x, y, x + width, y + height);
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public boolean isPushed(int pointerId) {
        if (this.pointerId != pointerId)
            return false;
        return pushed;
    }

    public boolean isPushed() {
        return pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    public void unPush(int pointerId) {
        if (this.pointerId != pointerId)
            return;
        this.pointerId = -1;
        this.pushed = false;
    }

    public void setPushed(boolean pushed, int pointerId) {
        if (this.pushed)
            return;
        this.pushed = pushed;
        this.pointerId = pointerId;
    }

    public int getPointerId() {
        return pointerId;
    }

    public boolean isIn(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return false;
        if (event.getPointerCount() > 1)
            return false;
        if (event.getPointerId(0) != pointerId)
            return false;
        return hitbox.contains(event.getX(0), event.getY(0));
    }
}
