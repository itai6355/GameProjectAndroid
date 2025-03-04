package com.example.gameproject.gamestates.setting;

import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.ui.ButtonImages;
import com.example.gameproject.ui.CustomButton;
import com.example.gameproject.ui.GameImages;

public class Setting extends BaseState implements GameStateInterface {

    final int space = 20;
    private final int menuX = GAME_WIDTH / 2 - GameImages.SETTING_MENU.getImage().getWidth() / 2;
    private final int menuY = GAME_HEIGHT / 2 - GameImages.SETTING_MENU.getImage().getHeight() / 2;
    private final int btnXBack = menuX + 2 * GameConstants.Sprite.X_DRAW_OFFSET;
    private final int btnYBack = menuY + 2 * GameConstants.Sprite.Y_DRAW_OFFSET;


    private final CustomButton[] btnVolumeButtons;
    private final int btnXVolume = menuX + GameConstants.Sprite.X_DRAW_OFFSET;
    private final int SoundIconX = btnXVolume + space * GameConstants.Sprite.SCALE_MULTIPLIER;
    private final int btnYVolume = menuY + 16 * GameConstants.Sprite.Y_DRAW_OFFSET;
    private final int SoundIconY = btnYVolume - 4 * GameConstants.Sprite.Y_DRAW_OFFSET;
    private final CustomButton btnSound;
    private final CustomButton btnBack;


    public Setting(Game game) {
        super(game);
        btnBack = new CustomButton(btnXBack, btnYBack, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
        btnVolumeButtons = new CustomButton[20];
        for (int i = 0; i < btnVolumeButtons.length; i++)
            btnVolumeButtons[i] = new CustomButton(btnXVolume + i * ButtonImages.SETTINGS_VOLUMES.getWidth() + space * GameConstants.Sprite.SCALE_MULTIPLIER
                    , btnYVolume, ButtonImages.SETTINGS_VOLUMES.getWidth(), ButtonImages.SETTINGS_VOLUMES.getHeight());
        btnSound = new CustomButton(SoundIconX, SoundIconY, GameImages.SOUND_ICON.getImage().getWidth(), GameImages.SOUND_ICON.getImage().getHeight());
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
        for (CustomButton btn : btnVolumeButtons) {
            canvas.drawBitmap(ButtonImages.SETTINGS_VOLUMES.getBtnImg(btn.isPushed()), btn.getHitbox().left, btn.getHitbox().top, null);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(GameImages.SETTING_MENU.getImage(), menuX, menuY, null);
        if (btnVolumeButtons[0].isPushed())
            canvas.drawBitmap(GameImages.SOUND_ICON.getImage(), SoundIconX, SoundIconY, null);
        else
            canvas.drawBitmap(GameImages.SILENT_ICON.getImage(), SoundIconX, SoundIconY, null);

    }

    @Override
    public void touchEvents(MotionEvent event) {
        boolean isActive = true;


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnBack))
                btnBack.setPushed(true);
            else if (isIn(event, btnSound))
                btnSound.setPushed(true);
            else
                for (CustomButton btn : btnVolumeButtons)
                    if (isIn(event, btn))
                        for (CustomButton btnVolumeButton : btnVolumeButtons) {
                            btnVolumeButton.setPushed(isActive);
                            if (btnVolumeButton == btn) isActive = false;
                        }

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            for (CustomButton btn : btnVolumeButtons)
                if (isIn(event, btn))
                    for (CustomButton btnVolumeButton : btnVolumeButtons) {
                        btnVolumeButton.setPushed(isActive);
                        if (btnVolumeButton == btn) isActive = false;
                    }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isActive = true;
            if (isIn(event, btnBack)) {
                if (btnBack.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isIn(event, btnSound)) {
                if (btnSound.isPushed())
                    for (CustomButton btn : btnVolumeButtons)
                        btn.setPushed(false);

            }

            btnBack.setPushed(false);
        }

    }

}
