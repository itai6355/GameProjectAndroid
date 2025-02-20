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

public class Setting extends BaseState implements GameStateInterface {

    private int menuX = MainActivity.GAME_WIDTH / 2 - GameImages.SETTING_MENU.getImage().getWidth() / 2;
    private int menuY = MainActivity.GAME_HEIGHT / 2 - GameImages.SETTING_MENU.getImage().getHeight() / 2;

    private CustomButton btnBack;
    private int btnXBack = menuX + 2 * GameConstants.Sprite.X_DRAW_OFFSET;
    private int btnYBack = menuY + 2 * GameConstants.Sprite.Y_DRAW_OFFSET;

    private CustomButton[] btnVolumeButtons;
    int space = 5;
    private int btnXVolume = menuX + 6 * GameConstants.Sprite.X_DRAW_OFFSET;
    private int btnYVolume = menuY + 16 * GameConstants.Sprite.Y_DRAW_OFFSET;


    public Setting(Game game) {
        super(game);
        btnBack = new CustomButton(btnXBack, btnYBack, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
        btnVolumeButtons = new CustomButton[10];
        for (int i = 0; i < 10; i++) {
            btnVolumeButtons[i] = new CustomButton(btnXVolume + i * ButtonImages.SETTINGS_VOLUMES.getWidth() + space * GameConstants.Sprite.SCALE_MULTIPLIER
                    , btnYVolume, ButtonImages.SETTINGS_VOLUMES.getWidth(), ButtonImages.SETTINGS_VOLUMES.getHeight());
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        drawBackground(canvas);
        drawButtons(canvas);

    }

    private void drawButtons(Canvas canvas) {
        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);
        //TODO: draw volume buttons!!!
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(GameImages.SETTING_MENU.getImage(), menuX, menuY, null);
    }

    @Override
    public void touchEvents(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack))
                btnBack.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, btnBack))
                if (btnBack.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);

            btnBack.setPushed(false);
        }

    }

}
