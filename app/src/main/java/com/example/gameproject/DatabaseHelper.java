package com.example.gameproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gameproject.entities.items.Items;

import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    // Columns for the users and inventory table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_COINS = "coins";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity"; // Quantity of the item

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a single table to store user data and inventory
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_COINS + " INTEGER DEFAULT 0, "
                + COLUMN_ITEM_NAME + " TEXT, "
                + COLUMN_ITEM_QUANTITY + " INTEGER, "
                + "UNIQUE(" + COLUMN_USERNAME + ", " + COLUMN_ITEM_NAME + "))"; // Unique constraint to ensure a single item per user
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Drop the existing table if it exists
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ITEM_NAME + " TEXT;");

            // Add the item_quantity column (if it doesn't exist)
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ITEM_QUANTITY + " INTEGER DEFAULT 0;");

        }
    }


    // Registration: Insert a new user
    public boolean registerUser(String username, String password) {
        if (username.length() < 4 || password.length() < 4) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the username already exists
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?",
                new String[]{username});

        if (cursor.moveToFirst()) {
            // User with the given username already exists
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        // If not, insert new record with 0 coins and no items
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


    // Update inventory: Add new items or increment existing items
    public void updateInventory(String username, CopyOnWriteArrayList<Items> inventory) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Items item : inventory) {
            // Check if the item already exists for this user
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_ITEM_NAME + "=?",
                    new String[]{username, item.getName()});

            if (cursor.moveToFirst()) {
                // Item exists, so update the quantity
                @SuppressLint("Range") int existingQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));

                ContentValues values = new ContentValues();
                values.put(COLUMN_ITEM_QUANTITY, existingQuantity + 1); // Increment by 1
                db.update(TABLE_USERS, values, COLUMN_USERNAME + "=? AND " + COLUMN_ITEM_NAME + "=?",
                        new String[]{username, item.getName()});
            } else {
                // Item doesn't exist, so insert a new record
                ContentValues values = new ContentValues();
                values.put(COLUMN_ITEM_NAME, item.getName());
                values.put(COLUMN_ITEM_QUANTITY, 1);  // Initialize the quantity to 1
                values.put(COLUMN_USERNAME, username); // Link to user

                db.insert(TABLE_USERS, null, values);
            }
            cursor.close();
        }
        db.close();
    }

    // Get the quantity of a specific item for a user
    @SuppressLint("Range")
    public int getItemQuantity(String username, String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_ITEM_QUANTITY + " FROM " + TABLE_USERS +
                        " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_ITEM_NAME + "=?",
                new String[]{username, itemName});

        int quantity = 0; // Default to 0 if not found

        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY));
        }

        cursor.close();
        db.close();

        return quantity;
    }
}
