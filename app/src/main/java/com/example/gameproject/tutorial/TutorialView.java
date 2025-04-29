package com.example.gameproject.tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class TutorialView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;
    private Simulation simulation;

    public TutorialView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        simulation = new Simulation(context);

    }

    public void update(double delta) {
        simulation.update(delta);

    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;
        canvas.drawColor(Color.BLACK);
        simulation.render(canvas);


        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        simulation.touchEvents(event);
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

    public void setLoop(TutorialActivity.Tutorialloop loop) {
        this.simulation.setLoop(loop);
    }


}
