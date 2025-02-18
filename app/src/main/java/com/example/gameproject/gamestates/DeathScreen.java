package com.example.gameproject.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;


public class DeathScreen extends BaseState implements GameStateInterface {

    private CustomButton buttonReplay, buttonMainMenu;

    private int menuX = MainActivity.GAME_WIDTH / 6;
    private int menuY = 200;

    private int buttonsX = menuX + GameImages.DEATH_MENU_MENUBG.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2;
    private int buttonReplayY = menuY + 200, buttonMainMenuY = buttonReplayY + 150;


    public DeathScreen(Game game) {
        super(game);
        buttonReplay = new CustomButton(buttonsX, buttonReplayY, ButtonImages.MENU_REPLAY.getWidth(), ButtonImages.MENU_REPLAY.getHeight());
        buttonMainMenu = new CustomButton(buttonsX, buttonMainMenuY, ButtonImages.MENU_MENU.getWidth(), ButtonImages.MENU_MENU.getHeight());
    }


    @Override
    public void render(Canvas c) {
        drawBackground(c);
        drawButtons(c);
    }

    private void drawButtons(Canvas c) {
        c.drawBitmap(ButtonImages.MENU_REPLAY.getBtnImg(buttonReplay.isPushed()),
                buttonReplay.getHitbox().left,
                buttonReplay.getHitbox().top,
                null);

        c.drawBitmap(ButtonImages.MENU_MENU.getBtnImg(buttonMainMenu.isPushed()),
                buttonMainMenu.getHitbox().left,
                buttonMainMenu.getHitbox().top,
                null);
    }

    private void drawBackground(Canvas c) {
        c.drawBitmap(GameImages.DEATH_MENU_MENUBG.getImage(),
                menuX, menuY, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, buttonReplay))
                buttonReplay.setPushed(true);
            else if (isIn(event, buttonMainMenu))
                buttonMainMenu.setPushed(true);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, buttonReplay)) {
                if (buttonReplay.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isIn(event, buttonMainMenu)) {
                if (buttonMainMenu.isPushed())
                    game.setCurrentGameState(Game.GameState.MENU);
            }

            buttonReplay.setPushed(false);
            buttonMainMenu.setPushed(false);
        }

    }


    @Override
    public void update(double delta) {
    }

}
