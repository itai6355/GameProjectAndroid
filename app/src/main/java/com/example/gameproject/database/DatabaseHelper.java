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
import android.graphics.PointF;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameproject.entities.enemies.DarkNinja;
import com.example.gameproject.entities.enemies.DarkWizard;
import com.example.gameproject.entities.enemies.Enemies;
import com.example.gameproject.entities.enemies.MaskedRaccoon;
import com.example.gameproject.entities.enemies.Skeleton;
import com.example.gameproject.entities.entities.Character;
import com.example.gameproject.entities.entities.Player;
import com.example.gameproject.entities.items.Items;
import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.entities.objects.Buildings;
import com.example.gameproject.entities.objects.GameObject;
import com.example.gameproject.entities.objects.GameObjects;
import com.example.gameproject.environments.GameMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GAME_DataBase.db";
    private static final int DATABASE_VERSION = 11;
    private static final String TABLE_NAME = "ANDROID_GAME_DB";
    private final Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        StringBuilder sql = new StringBuilder("CREATE TABLE " + TABLE_NAME + " (");
        List<DatabaseColumns.Column> cols = DatabaseColumns.getAllColumns();
        for (int i = 0; i < cols.size(); i++) {
            sql.append(cols.get(i).name()).append(" ").append(cols.get(i).type());
            if (i < cols.size() - 1) sql.append(", ");
        }
        sql.append(");");
        db.execSQL(sql.toString());
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
        Cursor cursor = db.rawQuery(
                "SELECT _id FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password});
        int userId = -1;
        if (cursor.moveToFirst())
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        cursor.close();
        return userId;
    }

    public boolean loginUser(String username, String password) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public boolean registerUser(String username, String password) {
        if (username.length() < 2 || password.length() < 2
                || (username.equals("admin") && password.equals("admin"))) return false;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?",
                new String[]{username});
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
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
            return false;
        }

        int id = getUserId(username, password);
        updateStringColumn(id, DatabaseColumns.SKIN, "Boy");
        return result != -1;
    }


    public void saveAllBuildings(int playerId, List<Building> buildings) {
        if (playerId == -1) return;
        try {
            JSONArray arr = new JSONArray();
            for (Building b : buildings) {
                JSONObject obj = new JSONObject();
                float rawX = b.getPos().x;
                float rawY = b.getPos().y - b.getBuildingType().getHitboxRoof();
                obj.put("x", rawX);
                obj.put("y", rawY);
                obj.put("type", b.getBuildingType().name());

                JSONArray insideObjects = new JSONArray();
                for (GameObject go : b.getInsideMap().getGameObjectArrayList()) {
                    JSONObject goObj = new JSONObject();
                    goObj.put("x", go.getHitbox().left);
                    goObj.put("y", go.getHitbox().top);
                    goObj.put("type", go.getObjectType().name());
                    insideObjects.put(goObj);
                }
                obj.put("insideObjects", insideObjects);

                arr.put(obj);
            }
            updateStringColumn(playerId, DatabaseColumns.BUILT_BUILDINGS, arr.toString());
            Log.d("DB", "Saved " + buildings.size() + " buildings for player " + playerId);
        } catch (JSONException e) {
            Log.e("saveAllBuildings", "Error: " + e.getMessage());
        }
    }

    public List<Building> getAllBuildings(int playerId, GameMap outsideMap) {
        List<Building> list = new ArrayList<>();
        if (playerId == -1) return list;

        String json = getColumnValueById(playerId, DatabaseColumns.BUILT_BUILDINGS);
        if (json == null || json.isEmpty()) return list;

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                float rawX = (float) obj.getDouble("x");
                float rawY = (float) obj.getDouble("y");
                Buildings type = Buildings.valueOf(obj.getString("type"));
                Building building = new Building(new PointF(rawX, rawY), type, 0, outsideMap);

                if (obj.has("insideObjects")) {
                    JSONArray insideObjects = obj.getJSONArray("insideObjects");
                    if (insideObjects.length() > 0) {
                        building.getInsideMap().getGameObjectArrayList().clear();
                        for (int j = 0; j < insideObjects.length(); j++) {
                            JSONObject goObj = insideObjects.getJSONObject(j);
                            float goX = (float) goObj.getDouble("x");
                            float goY = (float) goObj.getDouble("y");
                            GameObjects goType = GameObjects.valueOf(goObj.getString("type"));
                            float constructorY = goY - goType.hitboxRoof;
                            building.getInsideMap().getGameObjectArrayList()
                                    .add(new GameObject(new PointF(goX, constructorY), goType));
                        }
                    }
                }

                list.add(building);
            }
            Log.d("DB", "Loaded " + list.size() + " buildings for player " + playerId);
        } catch (Exception e) {
            Log.e("getAllBuildings", "Error: " + e.getMessage());
        }
        return list;
    }


    public void saveAllObjects(int playerId, List<GameObject> objects) {
        if (playerId == -1) return;
        try {
            JSONArray arr = new JSONArray();
            for (GameObject go : objects) {
                JSONObject obj = new JSONObject();
                float rawX = go.getHitbox().left;
                float rawY = go.getHitbox().top;
                obj.put("x", rawX);
                obj.put("y", rawY);
                obj.put("type", go.getObjectType().name());
                arr.put(obj);
            }
            updateStringColumn(playerId, DatabaseColumns.BUILT_OBJECTS, arr.toString());
            Log.d("DB", "Saved " + objects.size() + " objects for player " + playerId);
        } catch (JSONException e) {
            Log.e("saveAllObjects", "Error: " + e.getMessage());
        }
    }


    public List<GameObject> getAllObjects(int playerId) {
        List<GameObject> list = new ArrayList<>();
        if (playerId == -1) return list;

        String json = getColumnValueById(playerId, DatabaseColumns.BUILT_OBJECTS);
        if (json == null || json.isEmpty()) return list;

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                float rawX = (float) obj.getDouble("x");
                float rawY = (float) obj.getDouble("y");
                GameObjects type = GameObjects.valueOf(obj.getString("type"));
                float constructorY = rawY - type.hitboxRoof;
                list.add(new GameObject(new PointF(rawX, constructorY), type));
            }
            Log.d("DB", "Loaded " + list.size() + " objects for player " + playerId);
        } catch (Exception e) {
            Log.e("getAllObjects", "Error: " + e.getMessage());
        }
        return list;
    }

    public void saveAllEnemies(int playerId, CopyOnWriteArrayList<Character> enemies) {
        if (playerId == -1) return;
        try {
            JSONArray arr = getJsonArray(enemies);
            updateStringColumn(playerId, DatabaseColumns.ENEMIES, arr.toString());
            Log.d("DB", "Saved " + arr.length() + " enemies for player " + playerId);
        } catch (JSONException e) {
            Log.e("saveAllEnemies", "Error: " + e.getMessage());
        }
    }

    @NonNull
    private JSONArray getJsonArray(CopyOnWriteArrayList<Character> enemies) throws JSONException {
        JSONArray arr = new JSONArray();
        for (Character c : enemies) {
            Enemies type = getEnemyEnumType(c);
            if (type == null) continue;
            JSONObject obj = new JSONObject();
            obj.put("type", type.name());
            obj.put("x", c.getHitbox().left);
            obj.put("y", c.getHitbox().top);
            obj.put("hp", c.getCurrentHealth());
            arr.put(obj);
        }
        return arr;
    }


    public CopyOnWriteArrayList<Character> getAllEnemies(int playerId) {
        CopyOnWriteArrayList<Character> list = new CopyOnWriteArrayList<>();
        if (playerId == -1) return list;

        String json = getColumnValueById(playerId, DatabaseColumns.ENEMIES);
        if (json == null || json.isEmpty()) return list;

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Enemies type = Enemies.valueOf(obj.getString("type"));
                float x = (float) obj.getDouble("x");
                float y = (float) obj.getDouble("y");
                int hp = obj.optInt("hp", -1);

                Character c = buildEnemy(type, new PointF(x, y));
                if (hp > 0) c.setHealth(hp);
                list.add(c);
            }
            Log.d("DB", "Loaded " + list.size() + " enemies for player " + playerId);
        } catch (Exception e) {
            Log.e("getAllEnemies", "Error: " + e.getMessage());
        }
        return list;
    }


    public void clearSavedEnemies(int playerId) {
        if (playerId == -1) return;
        updateStringColumn(playerId, DatabaseColumns.ENEMIES, "");
    }

    public void saveTileMap(int playerId, int[][] spriteIds, int[][] originalIds) {
        if (playerId == -1) return;
        try {
            JSONArray arr = new JSONArray();
            for (int row = 0; row < spriteIds.length; row++) {
                for (int col = 0; col < spriteIds[row].length; col++) {
                    int currentId = spriteIds[row][col];
                    if (originalIds != null
                            && row < originalIds.length
                            && col < originalIds[row].length
                            && originalIds[row][col] == currentId) {
                        continue;
                    }
                    JSONObject cell = new JSONObject();
                    cell.put("row", row);
                    cell.put("col", col);
                    cell.put("id", currentId);
                    arr.put(cell);
                }
            }
            updateStringColumn(playerId, DatabaseColumns.TILE_MAP, arr.toString());
            Log.d("DB", "Saved " + arr.length() + " tile overrides for player " + playerId);
        } catch (JSONException e) {
            Log.e("saveTileMap", "Error: " + e.getMessage());
        }
    }

    public void loadTileMap(int playerId, int[][] spriteIds) {
        if (playerId == -1) return;
        String json = getColumnValueById(playerId, DatabaseColumns.TILE_MAP);
        if (json == null || json.isEmpty()) return;
        try {
            JSONArray arr = new JSONArray(json);
            int applied = 0;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject cell = arr.getJSONObject(i);
                int row = cell.getInt("row");
                int col = cell.getInt("col");
                int id  = cell.getInt("id");
                if (row >= 0 && row < spriteIds.length
                        && col >= 0 && col < spriteIds[row].length) {
                    spriteIds[row][col] = id;
                    applied++;
                }
            }
            Log.d("DB", "Applied " + applied + " tile overrides for player " + playerId);
        } catch (Exception e) {
            Log.e("loadTileMap", "Error: " + e.getMessage());
        }
    }


    private Enemies getEnemyEnumType(Character c) {
        if (c instanceof Skeleton) return Enemies.SKELETON;
        if (c instanceof MaskedRaccoon) return Enemies.MASKED_RAKKON;
        if (c instanceof DarkNinja) return Enemies.DARK_NINJA;
        if (c instanceof DarkWizard) return Enemies.DARK_WIZARD;
        return null;
    }

    private Character buildEnemy(Enemies type, PointF pos) {
        return switch (type) {
            case SKELETON -> new Skeleton(pos);
            case MASKED_RAKKON, GOLDEN_MASKED_RAKKON -> new MaskedRaccoon(pos);
            case DARK_NINJA -> new DarkNinja(pos);
            case DARK_WIZARD -> new DarkWizard(pos);
        };
    }


    public void setInventory(int id, Player player) {
        if (id == -1) return;
        List<Items> tempInventory = new ArrayList<>();
        for (DatabaseColumns.Column column : DatabaseColumns.getAllColumns()) {
            if (!column.name().startsWith(DatabaseColumns.COLUMN_ITEM_PREFIX)) continue;
            int itemValue;
            try {
                itemValue = Integer.parseInt(getColumnValueById(id, column));
            } catch (NumberFormatException e) {
                continue;
            }
            Items item = Items.valueOf(column.name().substring(DatabaseColumns.COLUMN_ITEM_PREFIX.length()));
            for (int i = 0; i < itemValue; i++) tempInventory.add(item);
        }
        for (Items item : tempInventory) player.addToInventoryWithoutSQL(item);
    }

    @SuppressLint("Range")
    public String getColumnValueById(int playerId, DatabaseColumns.Column column) {
        if (playerId == -1) return "999999";
        db = this.getReadableDatabase();
        if (!isValidColumn(column.name())) return "Invalid column name";
        Cursor cursor = db.rawQuery(
                "SELECT " + column.name() + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?",
                new String[]{String.valueOf(playerId)});
        String value = null;
        if (cursor.moveToFirst()) value = cursor.getString(cursor.getColumnIndex(column.name()));
        cursor.close();
        return value;
    }

    public boolean updateIntColumn(int playerId, DatabaseColumns.Column column, int newValue) {
        if (playerId == -1) return true;
        db = this.getWritableDatabase();
        if (!isIntegerColumn(column)) {
            showToast("Not an integer column.");
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

    public boolean updateStringColumn(int playerId, DatabaseColumns.Column column, String newValue) {
        if (playerId == -1) return true;
        db = this.getWritableDatabase();
        if (!isStringrColumn(column)) {
            showToast("Not a string column.");
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

    private boolean isValidColumn(String name) {
        for (DatabaseColumns.Column c : DatabaseColumns.getAllColumns())
            if (c.name().equals(name)) return true;
        return false;
    }

    private boolean isStringrColumn(DatabaseColumns.Column c) {
        return c.type().equals("TEXT");
    }

    private boolean isIntegerColumn(DatabaseColumns.Column c) {
        return c.type().equals("INTEGER") || c.type().equals("INTEGER DEFAULT 0");
    }

    Cursor readAllData() {
        db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    void deletePlayer(String playerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            long result = db.delete(TABLE_NAME, "_id=?", new String[]{playerId});
            showToast(result == -1 ? "Failed to Delete." : "Successfully Deleted.");
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

    public void deleteTable() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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
                @SuppressLint("Range") int pid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String[] items = {"item_APPLE", "item_APPLE_PIE_DISH", "item_APRICOT", "item_BACON",
                        "item_BACON_DISH", "item_BAGEL", "item_BAGEL_DISH", "item_BAGUETTE"};
                for (String item : items)
                    updateIntColumn(pid, new DatabaseColumns.Column(item, "INTEGER"), random.nextInt(21));
                updateIntColumn(pid, DatabaseColumns.COINS, 100 + random.nextInt(901));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void addIntColumn(int id, DatabaseColumns.Column col) {
        updateIntColumn(id, col, Integer.parseInt(getColumnValueById(id, col)) + 1);
    }

    public void reduceIntColumn(int id, DatabaseColumns.Column col) {
        updateIntColumn(id, col, Integer.parseInt(getColumnValueById(id, col)) - 1);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
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
                @SuppressLint("Range") String u = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                usernames.add(u);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usernames;
    }

    public void deleteAllDatabasesExeptThis() {
        File databaseDir = context.getDatabasePath(DATABASE_NAME).getParentFile();
        if (databaseDir != null && databaseDir.isDirectory()) {
            for (File file : Objects.requireNonNull(databaseDir.listFiles())) {
                if (file.isFile()
                        && (file.getName().endsWith(".db") || file.getName().endsWith(".db-journal"))
                        && !file.getName().equals(DATABASE_NAME)) {
                    file.delete();
                }
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void Log(String tag, String message) {
        android.util.Log.d(tag, message);
    }
}