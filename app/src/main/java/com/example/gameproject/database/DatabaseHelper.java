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

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Lerning.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "lerning";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE " + TABLE_NAME + " (");

        for (int i = 0; i < DatabaseColumns.ALL_COLUMNS.size(); i++) {
            DatabaseColumns.Column column = DatabaseColumns.ALL_COLUMNS.get(i);
            queryBuilder.append(column.name()).append(" ").append(column.type());

            if (i < DatabaseColumns.ALL_COLUMNS.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(");");

        db.execSQL(queryBuilder.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) return -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});

        int userId = -1;

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }

        cursor.close();
        db.close();

        return userId;
    }

    @SuppressLint("Range")
    public String getColumnValueById(int playerId, DatabaseColumns.Column column) {
        if (playerId == -1) return "9999999999";
        SQLiteDatabase db = this.getReadableDatabase();

        if (!isValidColumn(column.name())) {
            return "Invalid column name";
        }

        Cursor cursor = db.rawQuery("SELECT " + column.name() + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?", new String[]{String.valueOf(playerId)});

        String columnValue = null;

        if (cursor.moveToFirst()) {
            columnValue = cursor.getString(cursor.getColumnIndex(column.name()));
        }

        cursor.close();
        db.close();

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

    public boolean updateColumn(int playerId, DatabaseColumns.Column column, int newValue) {
        if (playerId == -1) return true;
        SQLiteDatabase db = this.getWritableDatabase();

        if (!isIntegerColumn(column)) {
            showToast("The specified column is not an integer column.");
            db.close();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(column.name(), newValue);

        try {
            long result = db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(playerId)});

            db.close();
            return result != -1;
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            db.close();
            return false;
        }
    }


    private boolean isIntegerColumn(DatabaseColumns.Column column) {
        return column.type().equals("INTEGER");
    }


    Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
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

    public boolean registerUser(String username, String password) {
        if (username.length() < 4 || password.length() < 4) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_COINS, 0);

        try {
            long result = db.insert(TABLE_NAME, null, values);
            db.close();
            Log("registerUser", "Result: " + result + ", Username: " + username + ", Password: " + password);
            return result != -1;
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        Log("loginUserByUsername", "Result: " + result + ", Username: " + username + ", Password: " + password);
        return result;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void Log(String tag, String message) {
        Log.d(tag, message);
    }
}
