package com.example.gameproject.database;

import java.util.ArrayList;
import java.util.List;

public class DatabaseColumns {

    public static final List<Column> ALL_COLUMNS = new ArrayList<>();

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COINS = "coins";

    public static final Column ID = new Column(COLUMN_ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
    public static final Column USERNAME = new Column(COLUMN_USERNAME, "TEXT");
    public static final Column PASSWORD = new Column(COLUMN_PASSWORD, "TEXT");
    public static final Column COINS = new Column(COLUMN_COINS, "INTEGER");
    public static final Column SKIN = new Column("skin", "TEXT");

    static {
        ALL_COLUMNS.add(ID);
        ALL_COLUMNS.add(USERNAME);
        ALL_COLUMNS.add(PASSWORD);
        ALL_COLUMNS.add(COINS);
        ALL_COLUMNS.add(SKIN);
    }

    public static List<Column> getAllColumns() {
        return ALL_COLUMNS;
    }
    public record Column(String name, String type) {}
}
