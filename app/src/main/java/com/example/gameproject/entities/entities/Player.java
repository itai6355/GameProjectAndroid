package com.example.gameproject.entities.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

import com.example.gameproject.database.DatabaseColumns;
import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.items.Item;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Character {

    private final CopyOnWriteArrayList<Items> inventory = new CopyOnWriteArrayList<>();
    public Icons icon;
    public GameCharacters skin;

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


        setSkinAndIcon(dbHelper.getColumnValueById(id, DatabaseColumns.SKIN));



        dbHelper.Log("Coins for player:" + username + " " + password + " " + id + " ", dbHelper.getColumnValueById(id, DatabaseColumns.COINS));
//        dbHelper.updateColumn(id, DatabaseColumns.COINS, 5);
//        dbHelper.Log("Coins for player:" + username + " " + password + " " + id + " ", dbHelper.getColumnValueById(id, DatabaseColumns.COLUMN_COINS));


    }


    public void update(double delta, boolean movePlayer) {
        if (movePlayer) updateAnimation();
        updateWepHitbox();
    }

    public Icons getIcon() {
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

    private void setSkinAndIcon(String skinName) {
        switch (skinName) {
            case "Egg Boy" -> {
                skin = GameCharacters.EGG_BOY;
                icon = Icons.EGG_BOY_ICON;
            }
            case "Egg Girl" -> {
                skin = GameCharacters.EGG_GIRL;
                icon = Icons.EGG_GIRL_ICON;
            }
            case "Eskimo" -> {
                skin = GameCharacters.ESKIMOS;
                icon = Icons.ESKIMOS_ICON;
            }
            case "Inspector" -> {
                skin = GameCharacters.INSPECTOR;
                icon = Icons.INSPECTOR_ICON;
            }
            case "Fighter" -> {
                skin = GameCharacters.FIGHTER;
                icon = Icons.FIGHTER_ICON;
            }
            case "Hunter" -> {
                skin = GameCharacters.HUNTER;
                icon = Icons.HUNTER_ICON;
            }
            case "Red Ninja" -> {
                skin = GameCharacters.RED_NINJA;
                icon = Icons.RED_NINJA_ICON;
            }
            default -> {
                skin = GameCharacters.BOY;
                icon = Icons.BOY_ICON;
            }
        }
    }

}