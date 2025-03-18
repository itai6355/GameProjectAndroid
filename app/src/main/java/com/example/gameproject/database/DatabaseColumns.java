package com.example.gameproject.database;

import com.example.gameproject.entities.items.Items;

import java.util.ArrayList;
import java.util.List;

public class DatabaseColumns {

    public static final List<Column> ALL_COLUMNS = new ArrayList<>();

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COINS = "coins";
    public static final String COLUMN_ITEM_PREFIX = "item_"; // Prefix to generate item columns


    public static final Column ID = new Column(COLUMN_ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
    public static final Column USERNAME = new Column(COLUMN_USERNAME, "TEXT");
    public static final Column PASSWORD = new Column(COLUMN_PASSWORD, "TEXT");
    public static final Column SKIN = new Column("skin", "TEXT");
    public static final Column COINS = new Column(COLUMN_COINS, "INTEGER");

    static {
        ALL_COLUMNS.add(ID);
        ALL_COLUMNS.add(USERNAME);
        ALL_COLUMNS.add(PASSWORD);
        ALL_COLUMNS.add(SKIN);
        ALL_COLUMNS.add(COINS);

        for (Items item : Items.values()) {
            if (item.name().equals("COIN")) continue;
            String columnName = COLUMN_ITEM_PREFIX + item.name();
            ALL_COLUMNS.add(new Column(columnName, "INTEGER DEFAULT 0"));
        }
    }

    public static Column getItemColumnByName(Items item) {
        String columnName = COLUMN_ITEM_PREFIX + item.name();
        for (Column column : ALL_COLUMNS) {
            if (column.name().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    public static List<Column> getAllColumns() {
        return ALL_COLUMNS;
    }

    public record Column(String name, String type) {
    }
}
