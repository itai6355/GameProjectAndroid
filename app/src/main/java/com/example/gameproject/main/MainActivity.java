package com.example.gameproject.main;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameproject.GeminiAPI;
import com.example.gameproject.R;
import com.example.gameproject.database.DatabaseHelper;

import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.GameImages;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static int GAME_WIDTH, GAME_HEIGHT;
    private static Context gameContext;
    private static DatabaseHelper dbHelper;
    private final boolean dev = false;
    private final boolean isBtnPushed = false;
    private EditText userName;
    private EditText password;
    private ImageView menu;
    private ImageView btnStart;
    private static final GeminiAPI geminiAPI = new GeminiAPI();
    private androidx.appcompat.widget.Toolbar toolbar;

    private static Vibrator vibrator;

    public static Context getGameContext() {
        return gameContext;
    }

    public static DatabaseHelper getDbHelper() {
        return dbHelper;
    }


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        GAME_WIDTH = dm.widthPixels;
        GAME_HEIGHT = dm.heightPixels;

        gameContext = this;

        dbHelper = new DatabaseHelper(this);

        menu = findViewById(R.id.menu);
        btnStart = findViewById(R.id.startBtn);
        userName = findViewById(R.id.UserNameText);
        password = findViewById(R.id.PasswordText);

        btnStart.setImageBitmap(ButtonImages.MENU_START.getBtnImg(isBtnPushed));
        btnStart.setOnClickListener(this);

        menu.setMaxWidth(GameImages.MENU.getImage().getWidth());
        menu.setMaxHeight(GameImages.MENU.getImage().getHeight());
        menu.setImageBitmap(GameImages.MENU.getImage());


        if (dev) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("username", "admin");
            intent.putExtra("password", "admin");
            intent.putExtra("left", 1);
            intent.putExtra("right", 1);
            startActivity(intent);
        }

    }


    @Override
    public void onClick(View v) {

        String usernameSt = userName.getText().toString().trim();
        String passwordSt = password.getText().toString().trim();

        if (usernameSt.isEmpty() || passwordSt.isEmpty() || usernameSt.length() < 4 || passwordSt.length() < 4) return;


        if (dbHelper.loginUserByUsername(usernameSt, passwordSt)) {
            Toast.makeText(this, "Welcome back " + usernameSt, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("username", usernameSt);
            intent.putExtra("password", passwordSt);
            startActivity(intent);
        } else {
            if (dbHelper.registerUser(usernameSt, passwordSt)) {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("username", usernameSt);
                intent.putExtra("password", passwordSt);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_login_existing) {
            showUserSelectionDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUserSelectionDialog() {
        List<String> usernames = dbHelper.getAllUsernames();
        if (usernames.isEmpty()) {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usernames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = new Spinner(this);
        spinner.setAdapter(adapter);

        builder.setView(spinner);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String selectedUser = (String) spinner.getSelectedItem();
            userName.setText(selectedUser);
            password.requestFocus();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }


    @Override
    protected void onDestroy() {
        dbHelper.closeDatabase();
        super.onDestroy();

    }
    public static void updateSurfaceSize(int width, int height) {
        GAME_WIDTH = width;
        GAME_HEIGHT = height;
    }

    public static GeminiAPI getGeminiAPI() {
        return geminiAPI;
    }

    public static void Vibrate(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        }
    }
}