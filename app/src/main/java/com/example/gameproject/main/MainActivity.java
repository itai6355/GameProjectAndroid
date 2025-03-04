package com.example.gameproject.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameproject.database.DatabaseHelper;
import com.example.gameproject.R;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.GameImages;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int GAME_WIDTH, GAME_HEIGHT;
    private static Context gameContext;
    private static DatabaseHelper dbHelper;
    private final boolean dev = false;
    private EditText userName;
    private EditText password;
    private ImageView menu;
    private final boolean isBtnPushed = false;
    private ImageView btnStart;

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
        if (dev) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("username", "admin");
            intent.putExtra("password", "admin");
            startActivity(intent);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

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

    }

    @Override
    public void onClick(View v) {

        String usernameSt = userName.getText().toString().trim();
        String passwordSt = password.getText().toString().trim();

        if (usernameSt.isEmpty() || passwordSt.isEmpty())
            return;


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
}