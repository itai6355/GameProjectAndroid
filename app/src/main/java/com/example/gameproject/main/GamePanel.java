package com.example.gameproject.main;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.Context;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.gameproject.NetworkReceiver;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final Game game;
    private final NetworkReceiver networkReceiver;

    public GamePanel(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        game = new Game(holder);

        networkReceiver = new NetworkReceiver(game);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(networkReceiver, filter);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        game.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        MainActivity.updateSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}