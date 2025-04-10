package com.example.gameproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.gameproject.main.Game;

public class NetworkReceiver extends BroadcastReceiver {
    private final Game game;
    private Game.GameState prev;

    public NetworkReceiver(Game game) {
        this.game = game;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (game.getCurrentGameState() == Game.GameState.LOST_CONNECTION)
                game.setCurrentGameState(prev);
        } else {
            prev = game.getCurrentGameState();
            game.setCurrentGameState(Game.GameState.LOST_CONNECTION);
        }
    }
}

