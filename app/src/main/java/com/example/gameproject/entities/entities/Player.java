package com.example.gameproject.entities.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;
import android.util.Log;

import com.example.gameproject.database.DatabaseColumns;
import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.debug.DebugState;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Character {

    private final CopyOnWriteArrayList<Items> inventory = new CopyOnWriteArrayList<>();
    public Icons icon;
    public GameCharacters skin;
    private final InventorySloth[] itemBar = new InventorySloth[8];

    private float maxHunger = 10.0f;
    private float currHunger = maxHunger;
    private int hungerTick = 0;
    private int hungerTickMax = 100;

    private final DatabaseHelper dbHelper;
    private final int id;
    private final String username, password;


    public Player(Game game) {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.BOY);
        setStartHealth(600);


        dbHelper = MainActivity.getDbHelper();

        username = GameActivity.getUsername();
        password = GameActivity.getPassword();

        id = dbHelper.getUserId(username, password);


        setSkinAndIcon(dbHelper.getColumnValueById(id, DatabaseColumns.SKIN));
        dbHelper.setInventory(id, inventory);
        game.getInventoryState().SyncInventories(this);
        Log.d("setInventory", inventory.toString());
        initItemBar();
    }

    private void initItemBar() {
        for (int i = 0; i < itemBar.length; i++)
            itemBar[i] = new InventorySloth(i, 1, 750 + (i * (InventorySloth.SLOT_SIZE + GameConstants.Sprite.X_DRAW_OFFSET)), GAME_HEIGHT - InventorySloth.SLOT_SIZE - GameConstants.Sprite.Y_DRAW_OFFSET);
    }

    public void update(double delta, boolean movePlayer) {
        if (movePlayer) {
            updateAnimation();
            updateHunger();
        }
        updateWepHitbox();


    }

    private void updateHunger() {
        hungerTick++;
        if (hungerTick >= hungerTickMax) {
            reduceHunger(1f);
            hungerTick = 0;
        }
    }

    public Icons getIcon() {
        return icon;
    }

    public GameCharacters getSkin() {
        return skin;
    }

    public CopyOnWriteArrayList<Items> getInventory() {
        return inventory;
    }

    public int getCurrHunger() {
        return (int) currHunger;
    }

    public int getMaxHunger() {
        return (int) maxHunger;
    }

    public void reduceHunger(float amount) {
        currHunger -= amount;
        if (currHunger < 0) currHunger = 0;
    }

    public void addHunger(float amount) {
        currHunger += amount;
        if (currHunger > maxHunger) currHunger = maxHunger;
    }

    public void resetHungerBar() {
        currHunger = maxHunger;
    }

    public void addToSQL(Items item) {
        if (Objects.requireNonNull(item) == Items.COIN)
            dbHelper.addIntColumn(id, DatabaseColumns.COINS);
        else
            dbHelper.addIntColumn(id, DatabaseColumns.getItemColumnByName(item));

    }

    public void setSkinAndIcon(String skinName) {
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

    public int getCoins() {
        try {
            return Integer.parseInt(dbHelper.getColumnValueById(id, DatabaseColumns.COINS));
        } catch (Exception e) {
            DebugState.getBug(e);
        }
        return 0;
    }

    public void setCoins(int coins) {
        dbHelper.updateIntColumn(id, DatabaseColumns.COINS, coins);
    }


    public void addToInventory(Items item) {
        inventory.add(item);
        addToSQL(item);
    }

    public InventorySloth[] getItemBar() {
        return itemBar;
    }

    public void eat(InventorySloth item) {
        if (item.getItem().isAdible()) {
            if (currHunger != maxHunger) {
                dbHelper.reduceIntColumn(id, DatabaseColumns.getItemColumnByName(item.getItem()));
                item.reduceAmount();
                addHunger(1);
            }
        }else {
            switch (item.getItem()){

            }
        }
    }

}