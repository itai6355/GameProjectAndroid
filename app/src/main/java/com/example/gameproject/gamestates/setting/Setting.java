package com.example.gameproject.gamestates.setting;

import static com.example.gameproject.main.MainActivity.GAME_HEIGHT;
import static com.example.gameproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.gameproject.GeminiAPI;
import com.example.gameproject.gamestates.BaseState;
import com.example.gameproject.gamestates.shop.ShopImages;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.Paints;
import com.example.gameproject.helpers.interfaces.GameStateInterface;
import com.example.gameproject.main.Game;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;
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

    private final CustomButton btnGeminiActive;
    private final int GeminiX = (int) (btnXVolume + space * GameConstants.Sprite.SCALE_MULTIPLIER * 2.5 - GameConstants.Sprite.X_DRAW_OFFSET);
    private final int GeminiY = btnYVolume + 5 * GameConstants.Sprite.Y_DRAW_OFFSET;


    private final CustomButton btnNext;
    private final CustomButton btnPrev;

    private final Paint BlackPaint;

    private float volume = 0.5f;


    public Setting(Game game) {
        super(game);
        btnBack = new CustomButton(btnXBack, btnYBack, ButtonImages.SETTINGS_BACK.getWidth(), ButtonImages.SETTINGS_BACK.getHeight());
        btnVolumeButtons = new CustomButton[20];
        for (int i = 0; i < btnVolumeButtons.length; i++) {
            btnVolumeButtons[i] = new CustomButton(btnXVolume + i * ButtonImages.SETTINGS_VOLUMES.getWidth() + space * GameConstants.Sprite.SCALE_MULTIPLIER
                    , btnYVolume, ButtonImages.SETTINGS_VOLUMES.getWidth(), ButtonImages.SETTINGS_VOLUMES.getHeight());
            btnVolumeButtons[i].setPushed(true);
        }
        btnNext = new CustomButton(btnVolumeButtons[19].getHitbox().right - ButtonImages.EMPTY_SMALL.getWidth(), btnYVolume + ButtonImages.EMPTY_SMALL.getHeight(), ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());
        btnPrev = new CustomButton(btnXVolume + space * GameConstants.Sprite.SCALE_MULTIPLIER, btnYVolume + ButtonImages.EMPTY_SMALL.getHeight(), ButtonImages.EMPTY_SMALL.getWidth(), ButtonImages.EMPTY_SMALL.getHeight());

        btnSound = new CustomButton(SoundIconX, SoundIconY, GameImages.SOUND_ICON.getImage().getWidth(), GameImages.SOUND_ICON.getImage().getHeight());
        btnGeminiActive = new CustomButton(GeminiX, GeminiY, ButtonImages.SETTINGS_IS_GEMINI.getWidth(), ButtonImages.SETTINGS_IS_GEMINI.getWidth());

        BlackPaint = Paints.BIG_TEXT_PAINT;
    }

    @Override
    public void update(double delta) {
        volume = 0.0f;
        for (CustomButton btn : btnVolumeButtons)
            if (btn.isPushed()) volume += 0.05f;
        GameActivity.getMpHelper().setVolume(volume, volume);
    }

    @Override
    public void render(Canvas canvas) {
        drawBackground(canvas);
        drawButtons(canvas);
        drawSong(canvas);
        drawArrow(canvas, btnNext, btnNext.getHitbox().left, btnNext.getHitbox().top);
        drawArrow(canvas, btnPrev, btnPrev.getHitbox().left, btnPrev.getHitbox().top);
        canvas.drawText("Is Villagers talking", GeminiX - 170, GeminiY + 200, BlackPaint);

    }

    private void drawArrow(Canvas canvas, CustomButton arrow, float arrowX, float arrowY) {
        canvas.drawBitmap(ButtonImages.EMPTY_SMALL.getBtnImg(arrow.isPushed()), arrowX, arrowY, null);
        if (arrow == btnPrev)
            canvas.drawBitmap(ShopImages.SHOP_ARROW_LEFT.getImage(), arrowX + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowY + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
        else
            canvas.drawBitmap(ShopImages.SHOP_ARROW_RIGHT.getImage(), arrowX + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, arrowY + 5 * GameConstants.Sprite.SCALE_MULTIPLIER, null);
    }

    private void drawSong(Canvas canvas) {
        var mp = GameActivity.getMpHelper();
        String name = mp.getCurrSong().name();
        float widthM = ((float) GAME_WIDTH / 2 - GameConstants.Sprite.X_DRAW_OFFSET * 12);
        float heightM = (float) GAME_HEIGHT / 2 - GameConstants.Sprite.Y_DRAW_OFFSET * 4;
        canvas.drawText(name, widthM, heightM, BlackPaint);
    }

    private void drawButtons(Canvas canvas) {
        canvas.drawBitmap(ButtonImages.SETTINGS_BACK.getBtnImg(btnBack.isPushed()), btnBack.getHitbox().left, btnBack.getHitbox().top, null);
        for (CustomButton btn : btnVolumeButtons) {
            canvas.drawBitmap(ButtonImages.SETTINGS_VOLUMES.getBtnImg(btn.isPushed()), btn.getHitbox().left, btn.getHitbox().top, null);
        }
        canvas.drawBitmap(ButtonImages.SETTINGS_IS_GEMINI.getBtnImg(btnGeminiActive.isPushed()), btnGeminiActive.getHitbox().left, btnGeminiActive.getHitbox().top, null);
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
            else if (isIn(event, btnNext))
                btnNext.setPushed(true);
            else if (isIn(event, btnPrev))
                btnPrev.setPushed(true);
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
            if (isIn(event, btnBack)) {
                if (btnBack.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);
            } else if (isIn(event, btnSound)) {
                if (btnSound.isPushed())
                    for (CustomButton btn : btnVolumeButtons)
                        btn.setPushed(false);
            } else if (isIn(event, btnNext)) {
                if (btnNext.isPushed())
                    GameActivity.getMpHelper().playNextSong();
            } else if (isIn(event, btnPrev)) {
                if (btnPrev.isPushed())
                    GameActivity.getMpHelper().playPreviousSong();
            } else if (isIn(event, btnGeminiActive)) {
                btnGeminiActive.setPushed(!btnGeminiActive.isPushed());
                MainActivity.getGeminiAPI().setIsShowText(!btnGeminiActive.isPushed());
            }

            btnBack.setPushed(false);
        }

    }

}
