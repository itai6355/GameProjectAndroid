package com.example.gameproject.entities.entities;


import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

import com.example.gameproject.database.DatabaseColumns;
import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.gamestates.invenory.InventorySloth;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;

import java.util.Objects;
import java.util.Random;

public class Player extends Character {

    private final InventorySloth[][] inventory;
    private final DatabaseHelper dbHelper;
    private final int id;
    private final String username, password;
    public Icons icon;
    public GameCharacters skin;
    private final float maxHunger = 10.0f;
    private float currHunger = maxHunger;
    private int hungerTick = 0;
    private final int hungerTickMax = 250;
    private boolean isSpeeding = false, isStreangth = false, isSatoration = false, isInvisible = false;
    private long SpeedingStart, StreangthStart, SatorationStart, InvisibleStart;
    private float SPEED = 1, STRENGTH = 50;


    public Player(Game game) {
        super(new PointF((float) GAME_WIDTH / 2, (float) GAME_HEIGHT / 2), GameCharacters.BOY);
        setStartHealth(600);
        inventory = game.getInventoryState().getInventory();

        dbHelper = MainActivity.getDbHelper();

        username = GameActivity.getUsername();
        password = GameActivity.getPassword();

        id = dbHelper.getUserId(username, password);


        if (id == -1) setSkinAndIcon("Noble");
        else setSkinAndIcon(dbHelper.getColumnValueById(id, DatabaseColumns.SKIN));
        dbHelper.setInventory(id, this);

    }

    public void update(boolean movePlayer) {
        if (movePlayer) {
            updateAnimation();
            updateHunger();
        }
        updateWepHitbox();
        updatePotions();
    }

    private void updatePotions() {
        if (isSpeeding) {
            SPEED = 1.5f;
            if (System.currentTimeMillis() - SpeedingStart >= 30000) {
                isSpeeding = false;
                SPEED = 1;
            }
        }
        if (isStreangth) {
            STRENGTH = 100;
            if (System.currentTimeMillis() - StreangthStart >= 30000) {
                isStreangth = false;
                STRENGTH = 50;
            }
        }
        if (isSatoration) {
            if (System.currentTimeMillis() - SatorationStart >= 30000) {
                isSatoration = false;
            }
        }
        if (isInvisible) {
            if (System.currentTimeMillis() - InvisibleStart >= 30000) {
                isInvisible = false;
            }
        }

        attackDamage = setAttackDamage();
    }


    private void updateHunger() {
        if (isSatoration) if (new Random().nextBoolean()) hungerTick++;
        else hungerTick++;
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

    private void addToSQL(Items item) {
        if (Objects.requireNonNull(item) == Items.COIN)
            dbHelper.addIntColumn(id, DatabaseColumns.COINS);
        else {
            dbHelper.addIntColumn(id, DatabaseColumns.getItemColumnByName(item));
        }
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
            case "Knight" -> {
                skin = GameCharacters.KNIGHT;
                icon = Icons.KNIGHT_ICON;
            }
            case "Master" -> {
                skin = GameCharacters.MASTER;
                icon = Icons.MASTER_ICON;
            }
            case "Monk" -> {
                skin = GameCharacters.MONK;
                icon = Icons.MONK_ICON;
            }
            case "Ninja Blue 2" -> {
                skin = GameCharacters.NINJABLUE2;
                icon = Icons.NINJABLUE2_ICON;
            }
            case "Ninja Blue" -> {
                skin = GameCharacters.NINJABLUE;
                icon = Icons.NINJABLUE_ICON;
            }
            case "Ninja Bomb" -> {
                skin = GameCharacters.NINJABOMB;
                icon = Icons.NINJABOMB_ICON;
            }
            case "Ninja Dark" -> {
                skin = GameCharacters.NINJADARK;
                icon = Icons.NINJADARK_ICON;
            }
            case "Ninja Eskimo" -> {
                skin = GameCharacters.NINJAESKIMO;
                icon = Icons.NINJAESKIMO_ICON;
            }
            case "Ninja Gray" -> {
                skin = GameCharacters.NINJAGRAY;
                icon = Icons.NINJAGRAY_ICON;
            }
            case "Ninja Green" -> {
                skin = GameCharacters.NINJAGREEN;
                icon = Icons.NINJAGREEN_ICON;
            }
            case "Ninja Masked" -> {
                skin = GameCharacters.NINJAMASKED;
                icon = Icons.NINJAMASKED_ICON;
            }
            case "Ninja Red" -> {
                skin = GameCharacters.NINJARED;
                icon = Icons.NINJARED_ICON;
            }
            case "Ninja Yellow" -> {
                skin = GameCharacters.NINJAYELLOW;
                icon = Icons.NINJAYELLOW_ICON;
            }
            case "Noble" -> {
                skin = GameCharacters.NOBLE;
                icon = Icons.NOBLE_ICON;
            }
            case "Old Man 2" -> {
                skin = GameCharacters.OLDMAN2;
                icon = Icons.OLDMAN2_ICON;
            }
            case "Old Man 3" -> {
                skin = GameCharacters.OLDMAN3;
                icon = Icons.OLDMAN3_ICON;
            }
            case "Old Man" -> {
                skin = GameCharacters.OLDMAN;
                icon = Icons.OLDMAN_ICON;
            }
            case "Princess" -> {
                skin = GameCharacters.PRINCESS;
                icon = Icons.PRINCESS_ICON;
            }
            case "Red Ninja 3" -> {
                skin = GameCharacters.REDNINJA3;
                icon = Icons.REDNINJA3_ICON;
            }
            case "Robot Green" -> {
                skin = GameCharacters.ROBOTGREEN;
                icon = Icons.ROBOTGREEN_ICON;
            }

            case "Robot Grey" -> {
                skin = GameCharacters.ROBOTGREY;
                icon = Icons.ROBOTGREY_ICON;
            }

            case "Samurai Blue" -> {
                skin = GameCharacters.SAMURAIBLUE;
                icon = Icons.SAMURAIBLUE_ICON;
            }

            case "Samurai" -> {
                skin = GameCharacters.SAMURAI;
                icon = Icons.SAMURAI_ICON;
            }
            case "Sorcerer Black" -> {
                skin = GameCharacters.SORCERERBLACK;
                icon = Icons.SORCERERBLACK_ICON;
            }
            case "Sorcerer Orange" -> {
                skin = GameCharacters.SORCERERORANGE;
                icon = Icons.SORCERERORANGE_ICON;
            }
            case "Statue" -> {
                skin = GameCharacters.STATUE;
                icon = Icons.STATUE_ICON;
            }
            case "Sultan 2" -> {
                skin = GameCharacters.SULTAN2;
                icon = Icons.SULTAN2_ICON;
            }
            case "Sultan" -> {
                skin = GameCharacters.SULTAN;
                icon = Icons.SULTAN_ICON;
            }
            case "Vampire" -> {
                skin = GameCharacters.VAMPIRE;
                icon = Icons.VAMPIRE_ICON;
            }
            default -> {
                skin = GameCharacters.BOY;
                icon = Icons.BOY_ICON;
            }
        }
        dbHelper.updateStringColumn(id,DatabaseColumns.SKIN,skinName);
    }

    public int getCoins() {
        try {
            return Integer.parseInt(dbHelper.getColumnValueById(id, DatabaseColumns.COINS));
        } catch (Exception ignored) {
        }
        return 0;
    }

    public void setCoins(int coins) {
        dbHelper.updateIntColumn(id, DatabaseColumns.COINS, coins);
    }


    public void addToInventory(Items item) {
        if (item == Items.COIN) {
            dbHelper.addIntColumn(id, DatabaseColumns.COINS);
            return;
        }
        int iEmpty = -1, jEmpty = -1;
        boolean found = false;
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                if (inventory[i][j].getItem() == item) {
                    inventory[i][j].addAmount();
                    found = true;
                    break;
                }
                if (inventory[i][j].getItem() == null && iEmpty == -1 && jEmpty == -1) {
                    iEmpty = i;
                    jEmpty = j;
                }
            }
            if (found) break;
        }

        if (!found && iEmpty != -1 && jEmpty != -1) {
            inventory[iEmpty][jEmpty].setItem(item);
        }
        addToSQL(item);
    }

    public void addToInventoryWithoutSQL(Items item) {
        if (item == Items.COIN) return;
        int iEmpty = -1, jEmpty = -1;
        boolean found = false;
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length - 1; j++) {
                if (inventory[i][j].getItem() == item) {
                    inventory[i][j].addAmount();
                    found = true;
                    break;
                }
                if (inventory[i][j].getItem() == null && iEmpty == -1 && jEmpty == -1) {
                    iEmpty = i;
                    jEmpty = j;
                }
            }
            if (found) break;
        }

        if (!found && iEmpty != -1 && jEmpty != -1) {
            inventory[iEmpty][jEmpty].setItem(item);
        }
    }


    public InventorySloth[][] getInventory() {
        return inventory;
    }

    public void UseItem(InventorySloth item) {
        if (item.getItem() == null) return;
        if (item.getItem().isAdible()) if (currHunger != maxHunger) addHunger(1);
        else return;
        else switch (item.getItem()) {
            case MEDIPCK -> {
                if (this.getCurrentHealth() == this.getMaxHealth()) return;
                heal(50);
            }
            case POTION_BLUE -> {
                this.SpeedingStart = System.currentTimeMillis();
                this.isSpeeding = true;
            }
            case POTION_RED -> {
                this.SatorationStart = System.currentTimeMillis();
                this.isSatoration = true;
            }

            case POTION_PURPLE -> {
                this.StreangthStart = System.currentTimeMillis();
                this.isStreangth = true;
            }
            case POTION_WHITE -> {
                this.InvisibleStart = System.currentTimeMillis();
                this.isInvisible = true;
            }
        }

        useItem(item);

    }

    private void useItem(InventorySloth item) {
        try {
            dbHelper.reduceIntColumn(id, DatabaseColumns.getItemColumnByName(item.getItem()));
            item.reduceAmount();
        } catch (Exception ignored) {
        }

    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public float getSPEED() {
        return SPEED;
    }

    public int getStreangth() {
        return (int) STRENGTH;
    }

    public boolean isEffect() {
        return isSpeeding || isStreangth || isSatoration || isInvisible;
    }

    public Items[] getEffects() {
        int count = 0;
        if (isSpeeding) count++;
        if (isStreangth) count++;
        if (isSatoration) count++;
        if (isInvisible) count++;

        Items[] effects = new Items[count];
        int i = 0;
        if (isSpeeding) effects[i++] = Items.POTION_BLUE;
        if (isStreangth) effects[i++] = Items.POTION_PURPLE;
        if (isSatoration) effects[i++] = Items.POTION_RED;
        if (isInvisible) effects[i] = Items.POTION_WHITE;

        return effects;
    }

    public int getTimeForEffect(Items effect) {
        return switch (effect) {
            case POTION_BLUE -> 30000 - (int) (System.currentTimeMillis() - SpeedingStart);
            case POTION_PURPLE -> 30000 - (int) (System.currentTimeMillis() - StreangthStart);
            case POTION_RED -> 30000 - (int) (System.currentTimeMillis() - SatorationStart);
            case POTION_WHITE -> 30000 - (int) (System.currentTimeMillis() - InvisibleStart);
            default -> 0;
        };
    }
}