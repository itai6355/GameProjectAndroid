package com.example.gameproject.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;


public class DeathScreen extends BaseState implements GameStateInterface {

    private CustomButton buttonReplay, buttonMainMenu;

    private int menuX = MainActivity.GAME_WIDTH / 2 - GameImages.MENU.getImage().getWidth() / 2;
    private int menuY = MainActivity.GAME_HEIGHT / 2 - GameImages.MENU.getImage().getHeight() / 2;

    private int buttonsX = menuX + GameImages.MENU.getImage().getWidth() / 2 - ButtonImages.MENU_START.getWidth() / 2 - 2 * GameConstants.Sprite.X_DRAW_OFFSET;
    private int buttonReplayY = menuY + ButtonImages.MENU_START.getHeight(),
            buttonMainMenuY = buttonReplayY + GameConstants.Sprite.SCALE_MULTIPLIER * GameConstants.Sprite.Y_DRAW_OFFSET;


    public DeathScreen(Game game) {
        super(game);
        buttonReplay = new CustomButton(buttonsX, buttonReplayY, ButtonImages.EMPTY.getWidth(), ButtonImages.EMPTY.getHeight());
        buttonMainMenu = new CustomButton(buttonsX, buttonMainMenuY, ButtonImages.EMPTY.getWidth(), ButtonImages.EMPTY.getHeight());
    }


    @Override
    public void render(Canvas canvas) {
        drawBackground(canvas);
        drawButtons(canvas);
    }

    private void drawButtons(Canvas canvas) {
        canvas.drawBitmap(ButtonImages.EMPTY.getBtnImg(buttonReplay.isPushed()),
                buttonReplay.getHitbox().left,
                buttonReplay.getHitbox().top,
                null);

        canvas.drawBitmap(ButtonImages.EMPTY.getBtnImg(buttonMainMenu.isPushed()),
                buttonMainMenu.getHitbox().left,
                buttonMainMenu.getHitbox().top,
                null);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(GameImages.MENU.getImage(),
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
