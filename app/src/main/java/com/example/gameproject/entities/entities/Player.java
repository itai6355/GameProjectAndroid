package com.example.gameproject.entities.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.example.gameproject.database.DatabaseColumns;
import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.GameImages;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Character {

    private final CopyOnWriteArrayList<Items> inventory = new CopyOnWriteArrayList<>();
    public Bitmap icon = Icons.BOY_ICON.getImage();
    private final DatabaseHelper dbHelper;
    private final int id;
    private final String username, password;

    public Player() {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.BOY);
        setStartHealth(600);

        dbHelper = MainActivity.getDbHelper();

        username = GameActivity.getUsername();
        password = GameActivity.getPassword();

        id = dbHelper.getUserId(username, password);

        dbHelper.Log("Coins for player:" + username + " " + password + " " + id + " ", dbHelper.getColumnValueById(id, DatabaseColumns.COINS));
//        dbHelper.updateColumn(id, DatabaseColumns.COINS, 5);
//        dbHelper.Log("Coins for player:" + username + " " + password + " " + id + " ", dbHelper.getColumnValueById(id, DatabaseColumns.COLUMN_COINS));


    }

    public void update(double delta, boolean movePlayer) {
        if (movePlayer) updateAnimation();
        updateWepHitbox();
    }

    public Bitmap getIcon() {
        return icon;
    }

    public CopyOnWriteArrayList<Items> getInventory() {
        return inventory;
    }

    public void updateSQL(Item item) {
        if (Objects.requireNonNull(item.getItemType()) == Items.COIN) {
            dbHelper.updateColumn(id, DatabaseColumns.COINS, Integer.parseInt(dbHelper.getColumnValueById(id, DatabaseColumns.COINS)) + 1);
            dbHelper.Log("Coins for player:" + username + " " + password + " " + id + " ", dbHelper.getColumnValueById(id, DatabaseColumns.COINS));
        }
    }

}