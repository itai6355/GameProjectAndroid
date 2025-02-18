package com.example.gameproject.gamestates;

import android.graphics.Canvas;

import android.view.MotionEvent;

import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;


public class Menu extends BaseState implements GameStateInterface {

    private CustomButton btnStart;

    private int menuX = MainActivity.GAME_WIDTH / 6;
    private int menuY = 200;

    private int btnStartX = menuX + GameImages.MAINMENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
    private int btnStartY = menuY + 100;

    public Menu(Game game) {
        super(game);
        btnStart = new CustomButton(btnStartX, btnStartY, ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_START.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(
                GameImages.MAINMENU_MENUBG.getImage(),
                menuX,
                menuY,
                null);


        canvas.drawBitmap(
                ButtonImages.MENU_START.getBtnImg(btnStart.isPushed()),
                btnStart.getHitbox().left,
                btnStart.getHitbox().top,
                null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnStart))
                btnStart.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnStart))
                if (btnStart.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);

            btnStart.setPushed(false);
        }


    }


}
