package com.example.gameproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 3; // Incremented the version number
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_COINS = "coins"; // New column for coins

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_COINS + " INTEGER DEFAULT 0" + ")"; // Set a default value for coins
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrade the database if needed (add the coins column)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // Add the coins column in case of schema upgrade
            String ADD_COINS_COLUMN = "ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_COINS + " INTEGER DEFAULT 0";
            db.execSQL(ADD_COINS_COLUMN);
        }
    }

    // Registration: Insert a new user
    public boolean registerUser(String username, String password) {
        if (username.length() < 4 || password.length() < 4)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the username already exists
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?",
                new String[]{username});

        if (cursor.moveToFirst()) {
            // User with the given username already exists.
            cursor.close();
            db.close();
            return false; // Handle the error as needed
        }

        cursor.close();

        // If not, insert new record
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_COINS, 0); // Initialize coins to 0

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Login: Check if user exists with the given username and password
    public boolean loginUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    // Set the coin amount for a user
    public boolean setCoins(String username, int coins) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COINS, coins);

        int rowsUpdated = db.update(TABLE_USERS, values, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
        return rowsUpdated > 0; // Return true if rows were updated
    }

    int cllomIndex;

    // Get the coin amount for a user
    public int getCoins(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_COINS}, COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null);

        if (cursor.moveToFirst()) {
            if (cursor.getColumnIndex(COLUMN_COINS) >= 0) {
                cllomIndex = cursor.getColumnIndex(COLUMN_COINS);
                int coins = cursor.getInt(cllomIndex);
                cursor.close();
                db.close();
                return coins;
            }
            cursor.close();
        }

        db.close();
        return 0; // Return 0 if the user is not found
    }
}
