package com.example.gameproject.gamestates.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class SettingView extends SurfaceView implements SurfaceHolder.Callback {

    private SettingState settingState;
    SurfaceHolder holder;

    public SettingView(Context context, SettingActivity.Settingloop settingLoop) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        settingState = new SettingState(context,settingLoop);
    }

    public void update(double delta) {
        settingState.update();
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;
        canvas.drawColor(Color.BLACK);
        settingState.render(canvas);


        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        settingState.touchEvents(event);
        return super.onTouchEvent(event);
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    public void setSettingloop(SettingActivity.Settingloop settingloop) {
        this.settingState.setSettingloop(settingloop);
    }
}
