package com.example.gameproject.database;

import static com.example.gameproject.database.DatabaseColumns.COLUMN_COINS;
import static com.example.gameproject.database.DatabaseColumns.COLUMN_ID;
import static com.example.gameproject.database.DatabaseColumns.COLUMN_PASSWORD;
import static com.example.gameproject.database.DatabaseColumns.COLUMN_USERNAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GAME_DataBase.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_NAME = "ANDROID_GAME_DB";
    private final Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        deleteAllDatabasesExeptThis();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        StringBuilder SQL_Builder = new StringBuilder();
        SQL_Builder.append("CREATE TABLE " + TABLE_NAME + " (");

        for (int i = 0; i < DatabaseColumns.ALL_COLUMNS.size(); i++) {
            DatabaseColumns.Column column = DatabaseColumns.ALL_COLUMNS.get(i);
            SQL_Builder.append(column.name()).append(" ").append(column.type());

            if (i < DatabaseColumns.ALL_COLUMNS.size() - 1) SQL_Builder.append(", ");
        }
        SQL_Builder.append(");");

        db.execSQL(SQL_Builder.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) return -1;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});

        int userId = -1;

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }

        cursor.close();
        return userId;
    }

    @SuppressLint("Range")
    public String getColumnValueById(int playerId, DatabaseColumns.Column column) {
        if (playerId == -1) return "999999";
        db = this.getReadableDatabase();

        if (!isValidColumn(column.name())) {
            return "Invalid column name";
        }

        Cursor cursor = db.rawQuery("SELECT " + column.name() + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?", new String[]{String.valueOf(playerId)});

        String columnValue = null;


        if (cursor.moveToFirst()) {
            columnValue = cursor.getString(cursor.getColumnIndex(column.name()));
        }

        cursor.close();

        return columnValue;
    }


    private boolean isValidColumn(String columnName) {
        for (DatabaseColumns.Column column : DatabaseColumns.getAllColumns()) {
            if (column.name().equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateIntColumn(int playerId, DatabaseColumns.Column column, int newValue) {
        if (playerId == -1) return true;
        db = this.getWritableDatabase();

        if (!isIntegerColumn(column)) {
            showToast("The specified column is not an integer column.");
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(column.name(), newValue);

        try {
            long result = db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(playerId)});

            if (result == -1) {
                showToast("Failed to update.");
            }
            return result != -1;
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            return false;
        }
    }


    public boolean updateStringColumn(int playerId, DatabaseColumns.Column column, String newValue) {
        if (playerId == -1) return true;
        db = this.getWritableDatabase();

        if (!isStringrColumn(column)) {
            showToast("The specified column is not an String column.");
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(column.name(), newValue);

        try {
            long result = db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(playerId)});
            return result != -1;
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            return false;
        }
    }

    private boolean isStringrColumn(DatabaseColumns.Column column) {
        return column.type().equals("TEXT");
    }

    private boolean isIntegerColumn(DatabaseColumns.Column column) {
        return column.type().equals("INTEGER") || column.type().equals("INTEGER DEFAULT 0");
    }


    Cursor readAllData() {
        db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    void deletePlayer(String Player_id) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            long result = db.delete(TABLE_NAME, "_id=?", new String[]{Player_id});
            if (result == -1) {
                showToast("Failed to Delete.");
            } else {
                showToast("Successfully Deleted.");
            }
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
        }
    }

    void deleteAllData() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_NAME);
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
        }
    }

    public void assignRandomValuesToPlayers() {
        db = this.getWritableDatabase();
        Random random = new Random();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int playerId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                String[] items = {"item_APPLE", "item_APPLE_PIE_DISH", "item_APRICOT", "item_BACON", "item_BACON_DISH", "item_BAGEL", "item_BAGEL_DISH", "item_BAGUETTE"};
                for (String item : items) {
                    int randomAmount = random.nextInt(21);
                    updateIntColumn(playerId, new DatabaseColumns.Column(item, "INTEGER"), randomAmount);
                }

                int randomCoins = 100 + random.nextInt(901);
                updateIntColumn(playerId, DatabaseColumns.COINS, randomCoins);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void deleteTable() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            Log("deleteTable", "Table deleted successfully.");
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            Log("deleteTable", "Error deleting table: " + e.getMessage());
        }
    }


    public boolean registerUser(String username, String password) {
        if ((username.length() < 2 || password.length() < 2) || (username.equals("admin") && password.equals("admin"))) {
            return false;
        }

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});

        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        }

        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_COINS, 0);

        long result;
        try {
            result = db.insert(TABLE_NAME, null, values);
            Log("registerUser", "Result: " + result + ", Username: " + username + ", Password: " + password);

        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            return false;
        }

        int id = getUserId(username, password);
        Log("registerUser", "User ID: " + id);
        updateStringColumn(id, DatabaseColumns.SKIN, "Boy");
        return result != -1;


    }

    public boolean loginUser(String username, String password) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        Log("loginUserByUsername", "Result: " + result + ", Username: " + username + ", Password: " + password);
        return result;
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void Log(String tag, String message) {
        Log.d(tag, message);
    }

    public void addIntColumn(int id, DatabaseColumns.Column itemColumnByName) {
        updateIntColumn(id, itemColumnByName, Integer.parseInt(getColumnValueById(id, DatabaseColumns.COINS)) + 1);
    }

    public void reduceIntColumn(int id, DatabaseColumns.Column itemColumnByName) {
        updateIntColumn(id, itemColumnByName, Integer.parseInt(getColumnValueById(id, DatabaseColumns.COINS)) - 1);
    }

    public void setInventory(int id, Player player) {
        if (id == -1) return;
        List<Items> tempInventory = new ArrayList<>();
        for (DatabaseColumns.Column column : DatabaseColumns.getAllColumns()) {
            if (column.name().startsWith(DatabaseColumns.COLUMN_ITEM_PREFIX)) {
                int itemValue;
                try {
                    itemValue = Integer.parseInt(getColumnValueById(id, column));
                } catch (NumberFormatException e) {
                    Log.e("setInventory", "Invalid value for item quantity: " + getColumnValueById(id, column));
                    continue;
                }
                for (int i = 0; i < itemValue; i++) {
                    Log("setInventory", "Adding item: " + Items.valueOf(column.name().substring(DatabaseColumns.COLUMN_ITEM_PREFIX.length())));
                    tempInventory.add(Items.valueOf(column.name().substring(DatabaseColumns.COLUMN_ITEM_PREFIX.length())));
                }
            }
        }

        for (Items item : tempInventory)
            player.addToInventoryWithoutSQL(item);


        Log("setInventory", tempInventory.toString());
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (db == null || !db.isOpen()) db = super.getReadableDatabase();
        return db;
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (db == null || !db.isOpen()) db = super.getWritableDatabase();
        return db;
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) db.close();
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                usernames.add(username);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return usernames;
    }

    public void deleteAllDatabasesExeptThis() {
        File databaseDir = context.getDatabasePath(DATABASE_NAME).getParentFile();
        if (databaseDir != null && databaseDir.isDirectory()) {
            for (File file : Objects.requireNonNull(databaseDir.listFiles())) {
                if (file.isFile() && (file.getName().endsWith(".db") || file.getName().endsWith(".db-journal")) && !file.getName().equals(DATABASE_NAME)) {
                    boolean deleted = file.delete();
                    Log("deleteAllDatabases", "Deleted: " + file.getName() + " - " + deleted);
                }
            }
        }
    }
}
